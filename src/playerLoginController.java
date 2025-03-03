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
public class playerLoginController {

    @FXML
    private Button createanaccountbutton;

    @FXML
    private Button playerloginbutton;

    @FXML
    private PasswordField playerpasswordfield;

    @FXML
    private TextField playertextfield;

    @FXML
    private Button returnplayerbutton;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void playerloginbuttonHandler(ActionEvent event) throws IOException {

        String playername = playertextfield.getText();
        String playerpassword = playerpasswordfield.getText();


        if (DatabaseHandler.validatePlayerLogin(playername, playerpassword)) {
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("playerHome.fxml"));
            root = loader.load();

            // Load stage and scene
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
    public void returnplayerButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("adminLogin.fxml"));
        root = loader.load();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root, 986, 592);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void createanaccountButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("playerCreate.fxml"));
        root = loader.load();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root, 1000, 600);
        stage.setScene(scene);
        stage.show();
    }
    
}
