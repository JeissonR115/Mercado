package Users;

public class User {
    private String idCard;
    private String name;
    private String userType;

    public User(String idCard, String name, String userType) {
        this.idCard = idCard;
        this.name = name;
        this.userType = userType;
    }

    // getters
    public String getIdCard() {
        return idCard;
    }

    public String getName() {
        return name;
    }

    public String getUserType() {
        return userType;
    }
}