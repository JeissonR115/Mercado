package data;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
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
    public List<Field> getClassParameters() {
        List<Field> parameters = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();
        Field[] fieldsSuper = clazz.getSuperclass().getDeclaredFields();

        parameters.addAll(Arrays.asList(fieldsSuper));
        parameters.addAll(Arrays.asList(fields));

        return parameters;
    }
    public List<T> getObjects() {
        return objects;
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
    public static <T> T createObject(Class<T> clazz, List<Object> attributeValues) {
        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            T object = constructor.newInstance();

            Field[] fields = clazz.getDeclaredFields();
            Field[] fieldsSuper = clazz.getSuperclass().getDeclaredFields();

            if (fields.length + fieldsSuper.length != attributeValues.size()) {
                throw new IllegalArgumentException("Number of attributes does not match the number of values.");
            }
            for (int i = 0; i < fieldsSuper.length; i++) {
                Field field = fieldsSuper[i];
                field.setAccessible(true);
                field.set(object, attributeValues.get(i));
            }
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                field.setAccessible(true);
                field.set(object, attributeValues.get(fieldsSuper.length + i ));
            }

            return object;
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | java.lang.reflect.InvocationTargetException e) {
            throw new RuntimeException("Error creating object of class " + clazz.getSimpleName(), e);
        }
    }
    public Object parseAttributeValue(String value) {
        List<Field> parameters = getClassParameters();

        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            // Si el valor no es un entero, devolverlo como una cadena
            return value;
        }
    }
    public void readObjectFromFile( ) {
        File file = new File(filename);
        try {
            if (!file.exists()) {
                file.createNewFile();
                System.out.println("Se ha creado un nuevo archivo: " + file.getName());
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al acceder al archivo: " + file.getName(), e);
        }

        try (Scanner scanner = new Scanner(file)) {

            while (scanner.hasNextLine()) {
                String[] fields = scanner.nextLine().split(",");
                List<Object> attributeValues = new ArrayList<>();

                for (int i = 0; i < fields.length; i++) {
                    attributeValues.add(parseAttributeValue(fields[i]));
                    System.out.println(attributeValues.get(i));
                }

                T newObj = createObject(clazz, attributeValues);
                objects.add(newObj);
            }
        }catch (FileNotFoundException e) {
            throw new RuntimeException(e);
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