package Users;

public class Admin extends User {
    private String password ;
    //private double sueldo;
    public Admin(int idCard, String name, String userType, String password){
        super(idCard,name,userType);
        this.password = password;
    }

    public Admin(){
    }
    public String getPassword() {
        return password;
    }
}
