import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class profileViewController implements Initializable {

    @FXML
    private Label gameaccountlabel;

    @FXML
    private Label playeridlabel;

    @FXML
    private Label profilenamelabel;

    @FXML
    private Button returnprofileviewbutton;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Set the labels based on the Session values
        playeridlabel.setText(String.valueOf(Session.playerId));
        profilenamelabel.setText(Session.playerName);
        gameaccountlabel.setText(Session.playerGameacc);
    }

    @FXML
    public void returnprofileviewButton(ActionEvent event) throws IOException {
        stage = (Stage) returnprofileviewbutton.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("playerHome.fxml"));
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}