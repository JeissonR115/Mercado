package Users;

public class Worker extends User {
    private String password ;
    //private double sueldo;
    public Worker(int idCard, String name, String userType, String password){
        super(idCard,name,userType);
        this.password = password;
    }

    public Worker(){
    }
    public String getPassword() {
        return password;
    }
}
