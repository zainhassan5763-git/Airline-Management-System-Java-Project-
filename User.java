package airline;

public class User {
    private String name;
    private String userName;
    private String passwordHash;

    public User(String name, String userName, String password) {
        this.name = name;
        this.userName = userName;
        this.passwordHash = hashPassword(password);
    }

    private String hashPassword(String password) {
        int hash = 0;
        for (char c : password.toCharArray()) hash = 31 * hash + c;
        return Integer.toHexString(hash);
    }

    public boolean checkPassword(String password) {
        return this.passwordHash.equals(hashPassword(password));
    }

    public String getName()     { return name; }
    public String getUserName() { return userName; }
    public void setName(String name)         { this.name = name; }
    public void setUserName(String userName) { this.userName = userName; }
    public void setPassword(String password) { this.passwordHash = hashPassword(password); }
}
