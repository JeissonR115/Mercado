package Users;

import java.io.*;
import java.util.*;

public class UserDatabase {
    private final List<Worker> users;
    private final String filename;

    public UserDatabase(String filename) {
        this.filename = filename;
        users = new ArrayList<>();
        readUsersFromFile();
    }

    public List<Worker> getUsers() {
        return users;
    }

    public void addUser(Worker user) {
        if (existsUser(user)) {
            System.out.println("ERROR!!!");
            System.out.println("El usuario con ID " + user.getIdCard() + " o nombre " + user.getName() + " ya existe en el archivo " + filename + ". No se guardarán los datos.");
        } else {
            users.add(user);
            updateFile();
        }
    }

    public void removeUser(Worker user) {
        if (!existsUser(user)) {
            System.out.println("ERROR!!!");
            System.out.println("El usuario con ID " + user.getIdCard() + " o nombre " + user.getName() + " no existe en el archivo " + filename + ".");
        } else {
            users.remove(user);
            updateFile();
        }
    }

    private void readUsersFromFile() {
        File file = new File(filename);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String[] fields = scanner.nextLine().split(",");
                Worker newWorker = new Worker(fields[0], fields[1], fields[2], fields[3]);
                if (!existsUser(newWorker)) {
                    users.add(newWorker);
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeUsersToFile() {
        try (FileWriter writer = new FileWriter(filename)) {
            for (Worker user : users) {
                String line = String.format("%s,%s,%s,%s\n", user.getIdCard(), user.getName(), user.getUserType(), user.getPassword());
                writer.write(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateFile() {
        writeUsersToFile();
        readUsersFromFile();
    }

    private boolean existsUser(Worker user) {
        for (Worker existingUser : users) {
            if (existingUser.getIdCard().equals(user.getIdCard()) || existingUser.getName().equals(user.getName())) {
                return true;
            }
        }
        return false;
    }
}