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

public class itemTableController implements Initializable {

    ObservableList<Item> itemlist = FXCollections.observableArrayList();
    private static final List<String> VALID_TYPES = Arrays.asList("Attack", "Magic", "Defense", "Movement");

    @FXML
    private Button createitembutton;

    @FXML
    private Button deleteitembutton;

    @FXML
    private TableColumn<Item, Integer> itemidcol;

    @FXML
    private TextField itemidtextfield;

    @FXML
    private TableColumn<Item, String> itemnamecol;

    @FXML
    private TextField itemnametextfield;

    @FXML
    private TableColumn<Item, Integer> itempricecol;

    @FXML
    private TextField itempricetextfield;

    @FXML
    private TableView<Item> itemtable;

    @FXML
    private TableColumn<Item, String> itemtypecol;

    @FXML
    private TextField itemtypetextfield;

    @FXML
    private Button returnitembutton;

    @FXML
    private Button updateitembutton;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @Override
    public void initialize(URL iturl, ResourceBundle itrb) {
        itemtable.setOnMouseClicked (event -> {
            Item item = itemtable.getSelectionModel().getSelectedItem();
            if (item != null) {
            itemidtextfield.setText(String.valueOf(item.getItId()));
            itemnametextfield.setText(item.getItName());
            itemtypetextfield.setText(item.getItType());
            itempricetextfield.setText(String.valueOf(item.getItPrice()));
    }});
        initializeitCol();
        displayItems();
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

        itemtable.setItems(itemlist);
    }

    public void createitemButton(ActionEvent event) throws IOException {
        
        String itidStr = (itemidtextfield.getText());
        String itname = itemnametextfield.getText();
        String ittype = itemtypetextfield.getText();
        String itpriceStr = (itempricetextfield.getText());
        
        itidStr = itidStr.trim();
        itname = itname.trim();
        ittype = ittype.trim();
        itpriceStr = itpriceStr.trim();
        
        
        if (itidStr.length() == 0) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Empty Item ID");
            alert.setContentText("Please enter an Item ID.");
            alert.showAndWait();
            return;
        }

        if (itname.length() == 0) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Empty Item Name");
            alert.setContentText("Please enter an Item name.");
            alert.showAndWait();
            return;
        }
        
        if (ittype.length() == 0) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Empty Item Type");
            alert.setContentText("Please enter an Item Type.");
            alert.showAndWait();
            return;
        }

        if (itpriceStr.length() == 0) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Empty Price");
            alert.setContentText("Please enter an Item Price.");
            alert.showAndWait();
            return;
        }

        if (!VALID_TYPES.contains(ittype)) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Type");
            alert.setContentText("Please enter a valid item type: Attack, Magic, Defense, or Movement.");
            alert.showAndWait();
            return;
        }

        int itid;
        int itprice;
        try {
            itid = Integer.parseInt(itidStr);
            itprice = Integer.parseInt(itpriceStr);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Number Format");
            alert.setContentText("Please enter valid numbers for Item ID and Item Price.");
            alert.showAndWait();
            return;
        }

        
        
        Item item = new Item(itid, itname, ittype, itprice);

        if (DatabaseHandler.addItem(item)) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Success");
            alert.setContentText("Item added successfully!");
            alert.showAndWait();
        }
        
        else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("Item not added!");
            alert.showAndWait();
        }

        displayItems();
    }

    @FXML
    public void deleteitemButton(ActionEvent event) {

        Item item = itemtable.getSelectionModel().getSelectedItem();

        if (item == null) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Player Selection");
            alert.setContentText("Please select an item to delete");
            alert.showAndWait();
            return;
        }

        if (DatabaseHandler.deleteItem(item)) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Success");
            alert.setContentText("Item deleted successfully!");
            alert.showAndWait();
        }
        
        else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("Item not deleted!");
            alert.showAndWait();
        }

        displayItems();
    }

    @FXML
    public void updateitemButton(ActionEvent event) {

        String itidStr = (itemidtextfield.getText());
        String itname = itemnametextfield.getText();
        String ittype = itemtypetextfield.getText();
        String itpriceStr = (itempricetextfield.getText());
        
        itidStr = itidStr.trim();
        itname = itname.trim();
        ittype = ittype.trim();
        itpriceStr = itpriceStr.trim();;

        if (itidStr.length() == 0) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Empty Item ID");
            alert.setContentText("Please enter an item id.");
            alert.showAndWait();
            return;
        }
        
        if (itname.length() == 0) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Empty Item Name");
            alert.setContentText("Please enter an item name.");
            alert.showAndWait();
            return;
        }
        
        if (ittype.length() == 0) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Empty Item Type");
            alert.setContentText("Please enter an item type.");
            alert.showAndWait();
            return;
        }

        if (itpriceStr.length() == 0) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Empty Item Price");
            alert.setContentText("Please enter an item price.");
            alert.showAndWait();
            return;
        }

        if (!VALID_TYPES.contains(ittype)) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Type");
            alert.setContentText("Please enter a valid item type: Attack, Magic, Defense, or Movement.");
            alert.showAndWait();
            return;
        }

        int itid;
        int itprice;
        try {
            itid = Integer.parseInt(itidStr);
            itprice = Integer.parseInt(itpriceStr);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Number Format");
            alert.setContentText("Please enter valid numbers for Item ID and Item Price.");
            alert.showAndWait();
            return;
        }
        
        Item item = new Item(itid, itname, ittype, itprice);

        if (DatabaseHandler.updateItem(item)) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Success");
            alert.setContentText("Item updated successfully!");
            alert.showAndWait();
        }
        
        else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("Item not updated!");
            alert.showAndWait();
        }

        displayItems();
    }

    @FXML
    public void returnitemButton(ActionEvent event) throws IOException {
        stage = (Stage) returnitembutton.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("adminHome.fxml"));
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}