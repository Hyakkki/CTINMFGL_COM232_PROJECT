import java.io.IOException;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class playerCreateController {

    @FXML
    private Button cancelcreatebutton;

    @FXML
    private Button createplayerbutton;

    @FXML
    private TextField emailcreatetextfield;

    @FXML
    private TextField passwordcreatetextfield;

    @FXML
    private TextField playercreatetextfield;

    private Stage stage;
    private Scene scene;
    private Parent root;

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.com$"
    );

    @FXML
    public void cancelcreateButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("playerLogin.fxml"));
        root = loader.load();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void createplayerButton(ActionEvent event) throws IOException {

        String playername = playercreatetextfield.getText();
        String playergameacc = emailcreatetextfield.getText();
        String playerpassword = passwordcreatetextfield.getText();
        

        playername = playername.trim();
        playergameacc = playergameacc.trim();
        playerpassword = playerpassword.trim();
        
        if (playername.length() == 0) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Username Entry");
            alert.setContentText("Please fill in username");
            alert.showAndWait();
            return;
        }

        if (playergameacc.length() == 0) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Password Entry");
            alert.setContentText("Please fill in email");
            alert.showAndWait();
            return;
        }

        if (!EMAIL_PATTERN.matcher(playergameacc).matches()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Email Format");
            alert.setContentText("Please enter a valid email format (name@email.com)");
            alert.showAndWait();
            return;
        }

        if (playerpassword.length() == 0) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Password Entry");
            alert.setContentText("Please fill in password");
            alert.showAndWait();
            return;
        }

        

        Player player = new Player(0, playername, playergameacc, playerpassword);
    
        if (DatabaseHandler.addPlayer(player)) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Success");
            alert.setContentText("Account created successfully! Please login.");
            alert.showAndWait();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("playerLogin.fxml"));
            root = loader.load();

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        
        else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Player Entry");
            alert.setContentText("Account not created!");
            alert.showAndWait();
        }
    }
}
