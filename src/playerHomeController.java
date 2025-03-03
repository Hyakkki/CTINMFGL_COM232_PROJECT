import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;

public class playerHomeController {

    @FXML
    private Hyperlink applehyperlink;

    @FXML
    private Hyperlink googleplayhyperlink;

    @FXML
    private Hyperlink vid1hyperlink;

    @FXML
    private Hyperlink vid2hyperlink;

    @FXML
    private Hyperlink vid3hyperlink;

    @FXML
    private Hyperlink vid4hyperlink;

    @FXML
    private Hyperlink vid5hyperlink;


    @FXML
    private Button logoutplayerbutton;

    @FXML
    private Button playerheroesbutton;

    @FXML
    private Button playeritemsbutton;

    @FXML
    private Button playerskinsbutton;

    @FXML
    private Button playerprofilebutton;

    

    private Stage stage;
    private Scene scene;
    private Parent root;
    
    @FXML
    public void applehyperLink(ActionEvent event) throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI("https://play.google.com/store/apps/details?id=com.mobile.legends&referrer=adjust_reftag%3Dc1RxaipnMfGKY%26utm_source%3DReLandingButton"));
    }

    @FXML
    public void googleplayhyperLink(ActionEvent event) throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI("https://play.google.com/store/apps/details?id=com.mobile.legends&referrer=adjust_reftag%3Dcgs2zfwIOBwpN%26utm_source%3DReLandingButton"));
    }

    @FXML
    public void vid1hyperLink(ActionEvent event) throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI("https://youtu.be/Mz_TTKKlY0U"));
    }

    @FXML
    public void vid2hyperLink(ActionEvent event) throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI("https://youtu.be/IR6cvhj368Y"));
    }

    @FXML
    public void vid3hyperLink(ActionEvent event) throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI("https://youtu.be/ejUZggjDd6Q"));
    }

    @FXML
    public void vid4hyperLink(ActionEvent event) throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI("https://youtu.be/XaIuKr4PUn8"));
    }

    @FXML
    public void vid5hyperLink(ActionEvent event) throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI("https://youtu.be/hYB1JxoqN0M"));
    }


    @FXML
    public void playerheroesButton(ActionEvent event) throws IOException {
        stage = (Stage) playerheroesbutton.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("heroView.fxml"));
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void playeritemsButton(ActionEvent event) throws IOException {
        stage = (Stage) playeritemsbutton.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("itemView.fxml"));
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void playerskinsButton(ActionEvent event) throws IOException {
        stage = (Stage) playerskinsbutton.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("skinView.fxml"));
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void logoutplayerButton(ActionEvent event) throws IOException {
        stage = (Stage) logoutplayerbutton.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("playerLogin.fxml"));
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void playerprofileButton(ActionEvent event) throws IOException {
        stage = (Stage) playerprofilebutton.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("profileView.fxml"));
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}