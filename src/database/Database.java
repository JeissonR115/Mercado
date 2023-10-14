package database;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

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
        return getClassParameters(clazz);
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
    private static <T> T createObject(Class<T> clazz, List<Object> attributeValues) {
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
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Error creating object of class " + clazz.getSimpleName(), e);
        }
    }
    private Object parseAttributeValue(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            // Si el valor no es un entero, devolverlo como una cadena
            return value;
        }
    }
    public void readObjectFromFile() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(filename));
            Set<List<Object>> attributeSets = new HashSet<>();
            objects.clear();
            for (String line : lines) {
                String[] fields = line.split(",");
                List<Object> attributeValues = new ArrayList<>();

                for (String field : fields) {
                    attributeValues.add(parseAttributeValue(field));

                }
                if (attributeSets.add(attributeValues)) {

                    T newObj = createObject(clazz, attributeValues);

                    objects.add(newObj);
                }
            }

            System.out.println("/* Se ha leído el archivo\" "+filename+" \" de forma exitosa */"  );
        } catch (IOException e) {
            throw new RuntimeException("Error al acceder al archivo: " + filename, e);
        }
    }
    public void writeObjectsToFile() {
        try (FileWriter writer = new FileWriter(filename)) {
            for (T object : objects) {
                List<Object> attributeValues = getObjectAttributes(object);
                List<String> attributeStrings = new ArrayList<>();

                for (Object value : attributeValues) {
                    attributeStrings.add(value.toString());
                }

                String line = String.join(",", attributeStrings) + "\n";
                writer.write(line);

            }
            System.out.println("/* Se ha escrito el archivo\" "+filename+" \" de forma exitosa */"  );
        } catch (IOException e) {
            throw new RuntimeException("Error al escribir en el archivo: " + filename, e);
        }
    }
    public boolean existsObject(List<T> list, T object) {
        readObjectFromFile();
        Object id = getObjectAttributes(object).get(0);
        Object name = getObjectAttributes(object).get(1);
        for (T item:list) {
            Object itemId=getObjectAttributes(item).get(0);
            Object itemName = getObjectAttributes(item).get(1);

            if(itemId == id || itemName.equals(name) ){
                return true;
            }
        }
        return false;
    }
    public boolean existsObject(T object){
        return existsObject(objects,object);
    }
    public void updateFile() {
        readObjectFromFile();
        writeObjectsToFile();
    }
    public void addObjet(T object) {
        readObjectFromFile();
        if (existsObject(object)) {
            System.out.println("ERROR!!!");
            System.out.println(
                    "El " +getClassParameters().get(0).getName()
                            + " Ya existe en el archivo " + filename + ". No se guardarán los datos."
            );
        } else {
            System.out.println(objects);
            objects.add(object);
            writeObjectsToFile();
            System.out.println("El " + object.getClass() + " fue annadido correctamente");
        }
    }
}