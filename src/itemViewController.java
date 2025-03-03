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

public class itemViewController implements Initializable {

    ObservableList<Item> itemlist = FXCollections.observableArrayList();
    FilteredList<Item> filteredData;

    @FXML
    private TableColumn<Item, Integer> itemidcol;

    @FXML
    private TableColumn<Item, String> itemnamecol;

    @FXML
    private TableColumn<Item, Integer> itempricecol;

    @FXML
    private TableView<Item> itemtable;

    @FXML
    private TableColumn<Item, String> itemtypecol;

    @FXML
    private Button returnitemviewbutton;

    @FXML
    private TextField itemsearchtextfield;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @Override
    public void initialize(URL iturl, ResourceBundle itrb) {
        initializeitCol();
        displayItems();
        setupSearchFilter();
    }

    private void initializeitCol() {
        itemidcol.setCellValueFactory(new PropertyValueFactory<Item, Integer>("itId"));
        itemnamecol.setCellValueFactory(new PropertyValueFactory<Item, String>("itName"));
        itemtypecol.setCellValueFactory(new PropertyValueFactory<Item, String>("itType"));
        itempricecol.setCellValueFactory(new PropertyValueFactory<Item, Integer>("itPrice"));
    }

    private void displayItems() {

        itemlist.clear();

        ResultSet result = DatabaseHandler.getItems();

        try {
            while (result.next()) {
                int itid = result.getInt("ItemID");
                String itname = result.getString("ItemName");
                String ittype = result.getString("ItemType");
                int itprice = result.getInt("ItemPrice");

                Item newitem = new Item(itid, itname, ittype, itprice);
                itemlist.add(newitem);
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        filteredData = new FilteredList<>(itemlist, p -> true);
        SortedList<Item> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(itemtable.comparatorProperty());

        itemtable.setItems(sortedData);
    }

    private void setupSearchFilter() {
        itemsearchtextfield.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(item -> {
                // If search field is empty, show all items
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                return String.valueOf(item.getItId()).contains(lowerCaseFilter) ||
                       item.getItName().toLowerCase().contains(lowerCaseFilter) ||
                       item.getItType().toLowerCase().contains(lowerCaseFilter) ||
                       String.valueOf(item.getItPrice()).contains(lowerCaseFilter);
            });
        });
    }


    @FXML
    public void returnitemviewButton(ActionEvent event) throws IOException {
        stage = (Stage) returnitemviewbutton.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("playerHome.fxml"));
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}