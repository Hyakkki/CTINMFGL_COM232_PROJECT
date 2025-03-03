import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class adminLoginController {

    @FXML
    PasswordField adminpasswordtextfield;

    @FXML
    TextField admintextfield;

    @FXML
    Button adminloginbutton;

    @FXML
    Button notanadminbutton;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void adminloginbuttonHandler(ActionEvent event) throws IOException {

        String adminname = admintextfield.getText();
        String adminpassword = adminpasswordtextfield.getText();


        if (DatabaseHandler.validateLogin(adminname, adminpassword)) {
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("adminHome.fxml"));
            root = loader.load();

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else
        {
            System.out.println("Login Unsuccessful");
        }
    }

    @FXML
    public void notanadminButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("playerLogin.fxml"));
        root = loader.load();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root, 1000, 600);
        stage.setScene(scene);
        stage.show();
    }

}
