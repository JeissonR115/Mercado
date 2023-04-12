import Users.*;
import java.util.*;
import ui.*;
import static other.Login.*;
import static ui.UIMenu.*;

public class Main {
    public static void main(String[] args) {
        UserDatabase userDb = new UserDatabase("user1.txt");
        List<Worker> listUsers = userDb.getUsers();
        userDb.updateFile();
        showMenu();
    }
}