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

public class skinTableController implements Initializable {

    ObservableList<Skin> skinlist = FXCollections.observableArrayList();

    @FXML
    private Button createskinbutton;

    @FXML
    private Button deleteskinbutton;

    @FXML
    private TableColumn<Skin, String> heronamecol;

    @FXML
    private TextField heronametextfield;

    @FXML
    private TableView<Skin> skintable;

    @FXML
    private Button returnskinbutton;

    @FXML
    private TableColumn<Skin, Integer> skinidcol;

    @FXML
    private TextField skinidtextfield;

    @FXML
    private TableColumn<Skin, String> skinnamecol;

    @FXML
    private TextField skinnametextfield;

    @FXML
    private TableColumn<Skin, String> skintypecol;

    @FXML
    private TextField skintypetextfield;

    @FXML
    private Button updateskinbutton;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @Override
    public void initialize(URL skurl, ResourceBundle skrb) {
        initializeskCol();
        displaySkins();
    }

    private void initializeskCol() {
        skinidcol.setCellValueFactory(new PropertyValueFactory<Skin, Integer>("skId"));
        skinnamecol.setCellValueFactory(new PropertyValueFactory<Skin, String>("skName"));
        skintypecol.setCellValueFactory(new PropertyValueFactory<Skin, String>("skType"));
        heronamecol.setCellValueFactory(new PropertyValueFactory<Skin, String>("hrName"));
    }

    private void displaySkins() {

        skinlist.clear();

        ResultSet result = DatabaseHandler.getSkins();

        try {
            while (result.next()) {
                int skid = result.getInt("SkinID");
                String skname = result.getString("SkinName");
                String sktype = result.getString("SkinType");
                String hrname = result.getString("HeroName");

                Skin newskin = new Skin(skid, skname, sktype, hrname);
                skinlist.add(newskin);
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        skintable.setItems(skinlist);
    }

    @FXML
    private void createskinButton(ActionEvent event) throws IOException {

        String skidStr = skinidtextfield.getText().trim();
        String skname = skinnametextfield.getText().trim();
        String sktype = skintypetextfield.getText().trim();
        String hrname = heronametextfield.getText().trim();


        if (skidStr.isEmpty() || skname.isEmpty() || sktype.isEmpty() || hrname.isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Please fill in all fields");
            alert.showAndWait();
            return;
        }

        int skid;
        try {
            skid = Integer.parseInt(skidStr);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Number Format");
            alert.setContentText("Please enter valid numbers for Skin");
            alert.showAndWait();
            return;
        }

        Skin skin = new Skin(skid, skname, sktype, hrname);

        if (DatabaseHandler.addSkin(skin)) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setContentText("Skin added successfully");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Cannot create new skin");
            alert.showAndWait();
        }

        displaySkins();
    }

    @FXML
    public void deleteskinButton(ActionEvent event) {

        Skin skin = skintable.getSelectionModel().getSelectedItem();

        if (DatabaseHandler.deleteSkin(skin)) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Success");
            alert.setContentText("Skin deleted successfully!");
            alert.showAndWait();
        }
        
        else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("Skin not deleted!");
            alert.showAndWait();
        }

        displaySkins();
    }

    @FXML
    private void updateskinButton(ActionEvent event) throws IOException {

        String skidStr = skinidtextfield.getText().trim();
        String skname = skinnametextfield.getText().trim();
        String sktype = skintypetextfield.getText().trim();
        String hrname = heronametextfield.getText().trim();


        if (skidStr.isEmpty() || skname.isEmpty() || sktype.isEmpty() || hrname.isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Please fill in all fields");
            alert.showAndWait();
            return;
        }

        int skid;
        try {
            skid = Integer.parseInt(skidStr);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Number Format");
            alert.setContentText("Please enter valid numbers for Skin");
            alert.showAndWait();
            return;
        }

        Skin skin = new Skin(skid, skname, sktype, hrname);

        if (DatabaseHandler.updateSkin(skin)) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setContentText("Skin added successfully");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Cannot create new skin");
            alert.showAndWait();
        }

        displaySkins();
    }

    @FXML
    public void returnskinButton(ActionEvent event) throws IOException {
        stage = (Stage) returnskinbutton.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("adminHome.fxml"));
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    

}