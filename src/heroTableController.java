import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.List;
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

public class heroTableController implements Initializable {

    ObservableList<Hero> herolist = FXCollections.observableArrayList();
    private static final List<String> VALID_ROLES = Arrays.asList("Tank", "Fighter", "Assassin", "Mage", "Marksman", "Support");

    @FXML
    private Button createherobutton;

    @FXML
    private TableColumn<Hero, String> heroidcol;

    @FXML
    private Button deleteherobutton;

    @FXML
    private TableColumn<Hero, String> heronamecol;

    @FXML
    private TextField heronametextfield;

    @FXML
    private TextField heroidtextfield;

    @FXML
    private TableColumn<Hero, String> herorolecol;

    @FXML
    private TextField heroroletextfield;

    @FXML
    private TableView<Hero> herotable;

    @FXML
    private Button returnherobutton;

    @FXML
    private Button updateherobutton;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @Override
    public void initialize(URL hrurl, ResourceBundle hrrb) {
        initializehrCol();
        displayHeroes();
    }

    private void initializehrCol() {
        heroidcol.setCellValueFactory(new PropertyValueFactory<Hero, String>("hrId"));
        heronamecol.setCellValueFactory(new PropertyValueFactory<Hero, String>("hrName"));
        herorolecol.setCellValueFactory(new PropertyValueFactory<Hero, String>("hrRole"));
    }

    private void displayHeroes() {

        herolist.clear();

        ResultSet result = DatabaseHandler.getHeroes();

        try {
            while (result.next()) {
                String hrid = result.getString("HeroID");
                String hrname = result.getString("HeroName");
                String hrrole = result.getString("HeroRole");
                
                Hero newhero = new Hero(hrid, hrname, hrrole);
                herolist.add(newhero);
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        herotable.setItems(herolist);
    }

    public void createheroButton(ActionEvent event) throws IOException {
        
        String hrid = heroidtextfield.getText();
        String hrname = heronametextfield.getText();
        String hrrole = heroroletextfield.getText();
        
        hrid = hrid.trim();
        hrname = hrname.trim();
        hrrole = hrrole.trim();
        
        if (hrid.length() == 0) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Empty Hero ID");
            alert.setContentText("Please enter a hero ID.");
            alert.showAndWait();
            return;
        }

        try {
            Integer.parseInt(hrid);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Hero ID");
            alert.setContentText("Hero ID must be a number.");
            alert.showAndWait();
            return;
        }

        if (hrname.length() == 0) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Empty Hero Name");
            alert.setContentText("Please enter a hero name.");
            alert.showAndWait();
            return;
        }
        
        if (hrrole.length() == 0) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Empty Role");
            alert.setContentText("Please enter a hero role.");
            alert.showAndWait();
            return;
        }

        if (!VALID_ROLES.contains(hrrole)) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Role");
            alert.setContentText("Please enter a valid hero role: Tank, Fighter, Assassin, Mage, Marksman, or Support.");
            alert.showAndWait();
            return;
        }
        
        Hero hero = new Hero(hrid, hrname, hrrole);

        if (DatabaseHandler.addHero(hero)) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Success");
            alert.setContentText("Hero added successfully!");
            alert.showAndWait();
        }
        
        else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("Hero not added!");
            alert.showAndWait();
        }

        displayHeroes();
    }

    @FXML
    public void deleteheroButton(ActionEvent event) {

        Hero hero = herotable.getSelectionModel().getSelectedItem();

        if (DatabaseHandler.deleteHero(hero)) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Success");
            alert.setContentText("Hero deleted successfully!");
            alert.showAndWait();
        }
        
        else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("Hero not deleted!");
            alert.showAndWait();
        }

        displayHeroes();
    }

    @FXML
    public void updateheroButton(ActionEvent event) {

        String hrid = heroidtextfield.getText();
        String hrname = heronametextfield.getText();
        String hrrole = heroroletextfield.getText();
        
        hrid = hrid.trim();
        hrname = hrname.trim();
        hrrole = hrrole.trim();

        if (hrid.length() == 0) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Empty Hero Name");
            alert.setContentText("Please enter a hero name.");
            alert.showAndWait();
            return;
        }

        try {
            Integer.parseInt(hrid);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Hero ID");
            alert.setContentText("Hero ID must be a number.");
            alert.showAndWait();
            return;
        }
        
        if (hrname.length() == 0) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Empty Hero Name");
            alert.setContentText("Please enter a hero name.");
            alert.showAndWait();
            return;
        }
        
        if (hrrole.length() == 0) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Empty Role");
            alert.setContentText("Please enter a hero role.");
            alert.showAndWait();
            return;
        }

        if (!VALID_ROLES.contains(hrrole)) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Role");
            alert.setContentText("Please enter a valid hero role: Tank, Fighter, Assassin, Mage, Marksman, or Support.");
            alert.showAndWait();
            return;
        }
        
        Hero hero = new Hero(hrid, hrname, hrrole);

        if (DatabaseHandler.updateHero(hero)) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Success");
            alert.setContentText("Hero updated successfully!");
            alert.showAndWait();
        }
        
        else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("Hero not updated!");
            alert.showAndWait();
        }

        displayHeroes();
    }

    @FXML
    public void returnheroButton(ActionEvent event) throws IOException {
        stage = (Stage) returnherobutton.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("adminHome.fxml"));
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
