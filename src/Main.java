import Users.*;
import java.util.*;
import java.io.*;
import ui.UIMenu;

import static other.Login.verifyCredentials;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        UserDatabase userDb = new UserDatabase("user1.txt");
        List<Worker> listUsers = userDb.getUsers();

        try {
            userDb.readUsersFromFile();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println(listUsers.size());
        try {
            userDb.writeUsersToFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        verifyCredentials(listUsers);
        System.out.println(listUsers.size());





        //Worker ana = new Worker(111,"ana","cajero","asd123");
    }
}