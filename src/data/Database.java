package data;

import java.io.*;
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

    public List<T> getObjects() {
        return objects;
    }

    public void readObjectsFromFile() throws FileNotFoundException {
        File file = new File(filename);
        if (!file.exists()) {
            try {
                FileWriter writer = new FileWriter(filename);
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            String[] fields = scanner.nextLine().split(",");
            try {
                T newObject = clazz.getDeclaredConstructor(String[].class).newInstance((Object) fields);
                if (!existsObject(objects, newObject)) {
                    objects.add(newObject);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        scanner.close();
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