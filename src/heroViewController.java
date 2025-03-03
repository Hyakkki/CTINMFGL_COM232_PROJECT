import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class heroViewController implements Initializable{

    ObservableList<Hero> herolist = FXCollections.observableArrayList();
    FilteredList<Hero> filteredData;


    @FXML
    private TableColumn<Hero, String> heroidcol;

    @FXML
    private TableColumn<Hero, String> heronamecol;

    @FXML
    private TableColumn<Hero, String> herorolecol;

    @FXML
    private TableView<Hero> herotable;

    @FXML
    private TextField herosearchtextfield;

    @FXML
    private Button returnheroviewbutton;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @Override
    public void initialize(URL hrurl, ResourceBundle hrrb) {
        initializehrCol();
        displayHeroes();
        setupSearchFilter();
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

        filteredData = new FilteredList<>(herolist, p -> true); // Initially show all data
        SortedList<Hero> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(herotable.comparatorProperty());

        herotable.setItems(sortedData);
    }

    private void setupSearchFilter() {
        herosearchtextfield.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(hero -> {
                // If search field is empty, show all heroes
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                return hero.getHrId().toLowerCase().contains(lowerCaseFilter) ||
                       hero.getHrName().toLowerCase().contains(lowerCaseFilter) ||
                       hero.getHrRole().toLowerCase().contains(lowerCaseFilter);
            });
        });
    }
        

    @FXML
    public void returnheroviewButton(ActionEvent event) throws IOException {
        stage = (Stage) returnheroviewbutton.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("playerHome.fxml"));
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
