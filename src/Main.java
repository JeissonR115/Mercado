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
        Worker Juan = new Worker(0,"juan","admin","123");
        userDb.addUser(Juan);
        userDb.updateFile();
        verifyCredentials(listUsers);
        System.out.println(listUsers.size());
    }
}