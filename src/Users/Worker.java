package Users;
import java.util.*;
import static other.Login.*;
import static ui.UIMenu.*;
import other.*;
//import ui.UIMenu;
public class Worker extends User {
    private final String password;
    private double sueldo;

    public Worker(int idCard, String name, String userType, String password){
        super(idCard,name,userType);
        this.password = password;
    }
    public String getPassword() {
        return password;
    }
}
