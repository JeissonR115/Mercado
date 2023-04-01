package Users;
import java.io.*;
import java.util.*;

public class UserDatabase {
    private List<Worker> users;
    private String filename;

    public UserDatabase(String filename) {
        this.filename = filename;
        this.users = new ArrayList<>();
    }

    public List<Worker> getUsers() {
        return users;
    }

    public void readUsersFromFile() throws FileNotFoundException {
        File file = new File(filename);
        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            String[] fields = scanner.nextLine().split(",");
            int idCard = Integer.parseInt(fields[0]);
            String name = fields[1];
            String userType = fields[2];
            String password = fields[3];
            if (userType.equals("cajero")) {
                users.add(new Worker(idCard, name, userType, password));
            } else {
                users.add(new Worker(idCard, name, userType, password));
            }
        }
        scanner.close();
    }

    public void writeUsersToFile() throws IOException {
        FileWriter writer = new FileWriter(filename);
        for (User user : users) {
            String line;
            if (user instanceof Worker) {
                Worker worker = (Worker) user;
                line = String.format("%d,%s,%s,%s\n", worker.getIdCard(), worker.getName(), worker.getUserType(), worker.getPassword());
            } else {
                line = String.format("%d,%s,%s\n", user.getIdCard(), user.getName(), user.getUserType());
            }
            writer.write(line);
        }
        writer.close();
    }

    public void addUser(Worker user) {
        if (exists(this.filename , user.getName(),user.getIdCard())){
            System.out.println("ERROR!!!");
            System.out.println("El user:"+ user.getName()+" o el id:"+ user.getIdCard() + " ya exixte.\nen el archivo: "+this.filename);
            System.out.println("No guardare los datos.");
        } else {
            users.add(user);
        }

    }

    public void removeUser(Worker user) {
        users.remove(user);
    }

    public Worker getUserByIdCard(int idCard) {
        for (Worker user : users) {
            if (user.getIdCard() == idCard) {
                return user;
            }
        }
        return null;
    }

    public static boolean exists(String fileName, String userName, int idCard) {
        boolean userExists = false;
        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNextLine()) {
                String[] fields = scanner.nextLine().split(",");
                int idCardFromFile = Integer.parseInt(fields[0]);
                String nameFromFile = fields[1];
                if (idCardFromFile == idCard || nameFromFile.equals(userName)) {
                    userExists = true;
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: el archivo no existe");
            e.printStackTrace();
        }
        return userExists;
    }
}