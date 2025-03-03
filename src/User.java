import javafx.beans.property.SimpleStringProperty;

public class User {
    
    private final SimpleStringProperty username;
    private final SimpleStringProperty password;
    private final SimpleStringProperty accountcreated;

    public User(String adminname, String adminpassword, String datecreated) {
        this.username = new SimpleStringProperty(adminname);
        this.password = new SimpleStringProperty(adminpassword);
        this.accountcreated = new SimpleStringProperty(datecreated);
    }

    public String getUsername() {
        return username.get();
    }

    public String getPassword() {
        return password.get();
    }

    public String getAccountCreated() {
        return accountcreated.get();
    }
}
