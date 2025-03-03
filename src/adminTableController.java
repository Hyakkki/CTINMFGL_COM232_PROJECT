import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class adminTableController implements Initializable {

    ObservableList<User> userlist = FXCollections.observableArrayList();

    @FXML
    private TableColumn<User, String> admindatecreatedcol;

    @FXML
    private TableColumn<User, String> adminpasswordcol;

    @FXML
    private TableView<User> admintable;

    @FXML
    private TextField adminpasswordtextfield;

    @FXML
    private TableColumn<User, String> adminusernamecol;

    @FXML
    private Button createadminbutton;

    @FXML
    private TextField adminusernametextfield;

    @FXML
    private Button deleteadminbutton;

    @FXML
    private Button updatedminbutton;

    @FXML
    private Button returnadminbutton;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeCol();
        displayUsers();
    }

    private void initializeCol() {
        adminusernamecol.setCellValueFactory(new PropertyValueFactory<>("username"));
        adminpasswordcol.setCellValueFactory(new PropertyValueFactory<>("password"));
        admindatecreatedcol.setCellValueFactory(new PropertyValueFactory<>("accountCreated"));
    }

    private void displayUsers() {

        userlist.clear();

        // Returns a set of Users from the database
        ResultSet result = DatabaseHandler.getUsers();

        try {
            // loop through set of users from the database
            while (result.next()) {
                String username = result.getString("Username");
                String password = result.getString("Password");
                String accountcreated = result.getString("AccountCreated");
   

                // Create a new User instance
                User newuser = new User(username, password, accountcreated);

                // Adds to arraylist
                userlist.add(newuser);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        admintable.setItems(userlist);
    }

    @FXML
    private void createUser(ActionEvent event) {

        String adminname = adminusernametextfield.getText();
        String adminpassword = adminpasswordtextfield.getText();

        adminname = adminname.trim();
        adminpassword = adminpassword.trim();
        if (adminname.length() == 0) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Admin User Entry");
            alert.setContentText("Please fill in username");
            alert.showAndWait();
            return;
        }

        if (adminpassword.length() == 0) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Password Entry");
            alert.setContentText("Please fill in password");
            alert.showAndWait();
            return;
        }

        User user = new User(adminname, adminpassword, "");
    
        if (DatabaseHandler.addUser(user)) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Success");
            alert.setContentText("Admin created successfully!");
            alert.showAndWait();
        }
        
        else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Admin Entry");
            alert.setContentText("Admin not created!");
            alert.showAndWait();
        }

        displayUsers();
    }

    @FXML
    private void deleteUser(ActionEvent event) throws IOException {

        User user = admintable.getSelectionModel().getSelectedItem();

        if (DatabaseHandler.deleteUser(user)) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Success");
            alert.setContentText("Admin deleted successfully!");
            alert.showAndWait();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("adminLogin.fxml"));
            root = loader.load();

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("Admin not deleted!");
            alert.showAndWait();
        }

        displayUsers();
    }

    @FXML
    private void updateUser(ActionEvent event) {
        

        String adminname = adminusernametextfield.getText();
        String adminpassword = adminpasswordtextfield.getText();

        adminname = adminname.trim();
        adminpassword = adminpassword.trim();

        if (adminname.length () == 0) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Username Entry");
            alert.setContentText("Please fill in Admin username");
            alert.showAndWait();
            return;
        }

        if (adminpassword.length () == 0) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Password Entry");
            alert.setContentText("Please fill in Password");
            alert.showAndWait();
            return;
        }

        User user = new User(adminname, adminpassword, "");

        if (DatabaseHandler.updateUser(user)) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Success");
            alert.setContentText("Admin updated successfully!");
            alert.showAndWait();
        }
        
        else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("User Entry");
            alert.setContentText("Admin not updated!");
            alert.showAndWait();
        }

        displayUsers();
    }

    @FXML
    public void returnadminButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("adminHome.fxml"));
        root = loader.load();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
