package Users;
import java.io.*;
import java.util.*;

public class UserDatabase {
    private final List<Worker> users;
    private final String filename;
    public UserDatabase(String filename) {
        this.filename = filename;
        this.users = new ArrayList<>();
    }
    public List<Worker> getUsers() {
        return users;
    }
    private void readUsersFromFile() throws FileNotFoundException {
        File file = new File(filename);
        if(!file.exists()){
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
            String idCard = fields[0];
            String name = fields[1];
            String userType = fields[2];
            String password = fields[3];
            Worker newWorker = new Worker(idCard, name, userType, password);
            if(!existsUser(users,newWorker)){users.add(newWorker);}
        }
        scanner.close();
    }
    private void writeUsersToFile() throws IOException {
        FileWriter writer = new FileWriter(filename);
        for (Worker user : users) {
            String line;
            line = String.format("%s,%s,%s,%s\n", user.getIdCard(), user.getName(), user.getUserType(), user.getPassword());
            writer.write(line);
        }
        writer.close();
    }
    public void updateFile(){
        try {
            this.readUsersFromFile();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            this.writeUsersToFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void addUser(Worker user) {
        if (existsUser(this.filename, user)){
            System.out.println("ERROR!!!");
            System.out.println("El user:"+ user.getName()+" o el id:"+ user.getIdCard() + " ya exixte.\nen el archivo: "+this.filename);
            System.out.println("No guardare los datos.");
        } else {
            users.add(user);
            updateFile();
        }

    }
    public void removeUser(Worker user) {
        if (!existsUser(users   , user)){
            System.out.println("ERROR!!!");
            System.out.println("El user:"+ user.getName()+" o el id:"+ user.getIdCard() + " no exixte.\nen el archivo: "+this.filename);
        } else {
            users.remove(user);
            updateFile();
        }

    }
    public boolean existsUser(String fileName, Worker user) {
        this.updateFile();
        boolean userExists = false;
        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNextLine()) {
                String[] fields = scanner.nextLine().split(",");
                String idCardFromFile =fields[0];
                String nameFromFile = fields[1];
                if (idCardFromFile.equals(user.getIdCard()) || nameFromFile.equals(user.getName())) {
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
    public boolean existsUser(List<Worker> userList, Worker user) {
        boolean userExists = false;
        for (Worker worker : userList) {
            if (worker.getIdCard().equals(user.getIdCard()) || worker.getName().equals(user.getName())) {
                userExists = true;
                break;
            }
        }
        return userExists;
    }
}