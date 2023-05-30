package Users;

public class User {
    private int idCard;
    private String name;
    private String userType;

    public User(int idCard, String name, String userType) {
        this.idCard = idCard;
        this.name = name;
        this.userType = userType;
    }
    public User(){}
    // getters
    public int getIdCard() {
        return idCard;
    }

    public String getName() {
        return name;
    }

    public String getUserType() {
        return userType;
    }
}