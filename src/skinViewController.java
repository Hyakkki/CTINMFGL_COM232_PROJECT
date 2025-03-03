import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class skinViewController implements Initializable {

    ObservableList<Skin> skinlist = FXCollections.observableArrayList();
    FilteredList<Skin> filteredData;

    @FXML
    private TextField skinsearchtextfield;

    @FXML
    private TableColumn<Skin, String> heronamecol;

    @FXML
    private Button returnskinviewbutton;

    @FXML
    private TableColumn<Skin, Integer> skinidcol;

    @FXML
    private TableColumn<Skin, String> skinnamecol;

    @FXML
    private TableView<Skin> skintable;

    @FXML
    private TableColumn<Skin, String> skintypecol;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @Override
    public void initialize(URL skurl, ResourceBundle skrb) {
        initializeskCol();
        displaySkins();
        setupSearchFilter();
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

        filteredData = new FilteredList<>(skinlist, p -> true);
        SortedList<Skin> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(skintable.comparatorProperty());

        skintable.setItems(sortedData);
    }

    private void setupSearchFilter() {
        skinsearchtextfield.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(skin -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                return String.valueOf(skin.getSkId()).contains(lowerCaseFilter) ||
                       skin.getSkName().toLowerCase().contains(lowerCaseFilter) ||
                       skin.getSkType().toLowerCase().contains(lowerCaseFilter) ||
                       skin.getHrName().toLowerCase().contains(lowerCaseFilter);
            });
        });
    }

    @FXML
    public void returnskinviewButton(ActionEvent event) throws IOException {
        stage = (Stage) returnskinviewbutton.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("playerHome.fxml"));
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}