import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class playerTableController implements Initializable {

    ObservableList<Player> playerlist = FXCollections.observableArrayList();

    @FXML
    private TextField playerpasslisttextfield;

    @FXML
    private TextField ingamenamelisttextfield;

    @FXML
    private TextField emaillisttextfield;

    @FXML
    private Button createplayerlistbutton;

    @FXML
    private Button deleteplayerlistbutton;

    @FXML
    private Button returnplayerbutton;

    @FXML
    private Button updateplayerlistbutton;

    @FXML
    private TableColumn<Player, String> gameaccountcol;

    @FXML
    private TableColumn<Player, String> ingamecol;

    @FXML
    private TableColumn<Player, String> playeridcol;

    @FXML
    private TableColumn<Player, String> playerpasswordcol;

    @FXML
    private TableView<Player> playertable;

    private Stage stage;
    private Scene scene;
    private Parent root;

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.com$"
    );

    @Override
    public void initialize(URL plurl, ResourceBundle plrb) {
        initializeplCol();
        displayPlayers();
    }

    private void initializeplCol() {
        playeridcol.setCellValueFactory(new PropertyValueFactory<Player, String>("plId"));
        ingamecol.setCellValueFactory(new PropertyValueFactory<Player, String>("plUsername"));
        playerpasswordcol.setCellValueFactory(new PropertyValueFactory<Player, String>("plPassword"));
        gameaccountcol.setCellValueFactory(new PropertyValueFactory<Player, String>("plGameacc"));
        
    }

    private void displayPlayers() {

        playerlist.clear();

        ResultSet result = DatabaseHandler.getPlayers();

        try {

            while (result.next()) {
                int plid = result.getInt("PlayerID");
                String plusername = result.getString("IngameName");
                String plgameacc = result.getString("GameAccount");
                String plpassword = result.getString("PlayerPassword");
                
                Player newplayer = new Player(plid, plusername, plgameacc, plpassword);
                playerlist.add(newplayer);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        playertable.setItems(playerlist);
    }

    public void createplayerlistButton(ActionEvent event) throws IOException {

        String playername = ingamenamelisttextfield.getText();
        String playergameacc = emaillisttextfield.getText();
        String playerpassword = playerpasslisttextfield.getText();
        

        playername = playername.trim();
        playergameacc = playergameacc.trim();
        playerpassword = playerpassword.trim();
        
        if (playername.length() == 0) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Username Entry");
            alert.setContentText("Please fill in Ingame Name");
            alert.showAndWait();
            return;
        }

        if (playergameacc.length() == 0) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Email Entry");
            alert.setContentText("Please fill in Email");
            alert.showAndWait();
            return;
        }

        if (!EMAIL_PATTERN.matcher(playergameacc).matches()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Email Format");
            alert.setContentText("Please enter a valid email address format.");
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
            alert.setContentText("Player account created successfully!");
            alert.showAndWait();
        }
        
        else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Player Entry");
            alert.setContentText("Player account not created!");
            alert.showAndWait();
        }

        displayPlayers();
    }

    @FXML
    private void deleteplayerlistButton(ActionEvent event) {

        Player player = playertable.getSelectionModel().getSelectedItem();

        if (DatabaseHandler.deletePlayer(player)) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Success");
            alert.setContentText("Player deleted successfully!");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error (5)");
            alert.setContentText("Player not deleted!");
            alert.showAndWait();
        }

        displayPlayers();
    }

    @FXML
    private void updateplayerlistButton(ActionEvent event) {

        String playername = ingamenamelisttextfield.getText();
        String playergameacc = emaillisttextfield.getText();
        String playerpassword = playerpasslisttextfield.getText();

        playername = playername.trim();
        playergameacc = playergameacc.trim();
        playerpassword = playerpassword.trim();


        if (playername.length () == 0) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Username Entry");
            alert.setContentText("Please fill in username");
            alert.showAndWait();
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
            alert.setContentText("Please enter a valid email address.");
            alert.showAndWait();
            return;
        }


        if (playerpassword.length () == 0) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Password Entry");
            alert.setContentText("Please fill in password");
            alert.showAndWait();
        }

        Player player = new Player(0, playername, playergameacc, playerpassword);

        if (DatabaseHandler.updatePlayer(player)) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Success");
            alert.setContentText("Player updated successfully!");
            alert.showAndWait();
        }
        
        else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("User Entry");
            alert.setContentText("Player not updated!");
            alert.showAndWait();
        }

        displayPlayers();
    }

    @FXML
    public void returnplayerButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("adminHome.fxml"));
        root = loader.load();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}