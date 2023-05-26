package data;

import java.io.*;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;

public class Database<T> {
    private final List<T> objects;
    private final String filename;
    private final Class<T> clazz;
    public Database(String filename, Class<T> clazz) {
        this.filename = filename;
        this.clazz = clazz;
        objects = new ArrayList<>();
    }
    public static List<Field> getClassParameters(Class<?> clazz) {
        List<Field> parameters = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();
        Field[] fieldsSuper = clazz.getSuperclass().getDeclaredFields();

        parameters.addAll(Arrays.asList(fieldsSuper));
        parameters.addAll(Arrays.asList(fields));

        return parameters;
    }
    public List<Object> getObjectAttributes(T object) {
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        Field[] fieldsSuper = clazz.getSuperclass().getDeclaredFields();
        List<Object> attributeValues = new ArrayList<>();

        try {
            for (Field fieldSuper : fieldsSuper) {
            fieldSuper.setAccessible(true); // Permitir acceso a campos privados
            Object value = fieldSuper.get(object); // Obtener el valor del atributo en el objeto
            attributeValues.add(value);
        }
            for (Field field : fields) {
                field.setAccessible(true); // Permitir acceso a campos privados
                Object value = field.get(object); // Obtener el valor del atributo en el objeto
                attributeValues.add(value);
            }


        } catch (IllegalAccessException e) {
            throw new RuntimeException("Error al acceder a los atributos del objeto", e);
        }

        return attributeValues;
    }

    public List<T> getObjects() {
        return objects;
    }
    public void addUser(T object) {

        if (existsObject(object)) {
            Class<?> objectClass = object.getClass();
            System.out.println("ERROR!!!");
            System.out.printf(objectClass.getName()+" ya existe en el archivo: \""+filename+"\"\n. No se guardarán los datos.");
        } else {
            objects.add(object);
            //updateFile();
        }
    }
    private void readObjectFromFile( Function<String[], Object> objectCreator) {
        File file = new File(filename);
        try {
            if (!file.exists()) {
                file.createNewFile();
                System.out.println("Se ha creado un nuevo archivo: " + file.getName());
            }

            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNextLine()) {
                    String[] fields = scanner.nextLine().split(",");
                    Object newObj = objectCreator.apply(fields);
                    objects.add((T) newObj);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al acceder al archivo: " + file.getName(), e);
        }
    }

    public void writeObjectsToFile() throws IOException {
        FileWriter writer = new FileWriter(filename);
        for (T object : objects) {
            String line = object.toString() + "\n";
            writer.write(line);
        }
        writer.close();
    }
    public boolean existsObject(T object) {
        return existsObject(objects, object);
    }
    public boolean existsObject(List<T> objectList, T object) {
        return objectList.stream().anyMatch(o -> o.equals(object));
    }
}