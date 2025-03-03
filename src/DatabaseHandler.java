import java.sql.*;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class DatabaseHandler {

    private static DatabaseHandler handler = null;
        private static Statement stmt = null;
        private static PreparedStatement pstatement = null;

     public static DatabaseHandler getInstance() {
        if (handler == null) {
            handler = new DatabaseHandler();
        }
        return handler;
    }

    public static Connection getDBConnection()
    {
        Connection connection = null;
        String dburl = "jdbc:mysql://localhost:3306/mydatabase?serverTimezone=UTC";
        String userName = "root";
        String password = "021105";

        try
        {
            connection = DriverManager.getConnection(dburl, userName, password);

        } catch (Exception e){
            e.printStackTrace();
        }

        return connection;
    }

    public ResultSet execQuery(String query) {
        ResultSet result;
        try {
            stmt = getDBConnection().createStatement();
            result = stmt.executeQuery(query);
        }
        catch (SQLException ex) {
            System.out.println("Exception at execQuery:dataHandler" + ex.getLocalizedMessage());
            return null;
        }
        finally {
        }
        return result;
    }

    // For adminLoginController

    public static boolean validateLogin(String username, String password){

        getInstance();
        String query = "SELECT * FROM admin WHERE UserName = '" + username + "' AND Password = '" + password + "'";
        
        System.out.println(query);

        ResultSet result = handler.execQuery(query);
        try {
            if (result.next()) {
                Session.adminName = result.getString("Username");

                return true;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return false;
    }

    // For adminTableController

    public static ResultSet getUsers() {

        ResultSet result = null;

        try {
            String query = "SELECT * FROM admin";
            result = handler.execQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static boolean addUser(User user) {

        try {
            pstatement = getDBConnection().prepareStatement("INSERT INTO admin (Username, Password) VALUES (?, ?)");
            pstatement.setString(1, user.getUsername());
            pstatement.setString(2, user.getPassword());
            return pstatement.executeUpdate() > 0;
        
        } catch (SQLIntegrityConstraintViolationException e) {
        // Handle duplicate entry error
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Duplicate Entry");
        alert.setContentText("A user with this username already exists.");
        alert.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean deleteUser(User user) {

        try {
            pstatement = getDBConnection().prepareStatement("DELETE FROM admin WHERE Username=?");
            pstatement.setString(1, user.getUsername()); 

            int res = pstatement.executeUpdate();
            if (res > 0) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean updateUser(User user) {

        try {
            pstatement = getDBConnection().prepareStatement("UPDATE admin SET Username = ?, Password = ? WHERE Username = ?");
            pstatement.setString(1, user.getUsername());
            pstatement.setString(2, user.getPassword());
            pstatement.setString(3, user.getUsername());
            
            int res = pstatement.executeUpdate();
            
            if (res > 0) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // For playerLoginController

    public static boolean validatePlayerLogin(String plusername, String plpassword){

        getInstance();
        String query = "SELECT * FROM player WHERE IngameName = '" + plusername + "' AND PlayerPassword = '" + plpassword + "'";
        
        System.out.println(query);

        ResultSet result = handler.execQuery(query);
        try {
            if (result.next()) {
                Session.playerId = result.getInt("PlayerID");
                Session.playerName = result.getString("IngameName");
                Session.playerGameacc = result.getString("GameAccount");
                return true;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return false;
    }

    // playerTableController / playerCreateController

    public static ResultSet getPlayers() {

        ResultSet result = null;

        try {
            String query = "SELECT * FROM player";
            result = handler.execQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static boolean addPlayer(Player player) {
        try {
            pstatement = getDBConnection().prepareStatement("INSERT INTO player (IngameName, GameAccount, PlayerPassword) VALUES (?, ?, ?)");
            pstatement.setString(1, player.getPlUsername());
            pstatement.setString(2, player.getPlGameacc());
            pstatement.setString(3, player.getPlPassword());
            return pstatement.executeUpdate() > 0;

        } catch (SQLIntegrityConstraintViolationException e) {
            // Handle duplicate entry error
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Duplicate Entry");
            alert.setContentText("An Email already exists.");
            alert.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean deletePlayer(Player player) {

        try {
            pstatement = getDBConnection().prepareStatement("DELETE FROM player WHERE IngameName=?");
            pstatement.setString(1, player.getPlUsername()); 

            int res = pstatement.executeUpdate();
            if (res > 0) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean updatePlayer(Player player) {

        try {
            pstatement = getDBConnection().prepareStatement("UPDATE player SET IngameName = ?, GameAccount = ?, PlayerPassword = ? WHERE IngameName = ?");
            pstatement.setString(1, player.getPlUsername());
            pstatement.setString(2, player.getPlGameacc());
            pstatement.setString(3, player.getPlPassword());
            pstatement.setString(4, player.getPlUsername());
            
            int res = pstatement.executeUpdate();
            
            if (res > 0) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // heroTableController

    public static ResultSet getHeroes() {

        ResultSet result = null;

        try {
            String query = "SELECT * FROM hero";
            result = handler.execQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static boolean addHero(Hero hero) {
        try {
            pstatement = getDBConnection().prepareStatement("INSERT INTO hero (HeroID, HeroName, HeroRole) VALUES (?, ?, ?)");
            pstatement.setString(1, hero.getHrId());
            pstatement.setString(2, hero.getHrName());
            pstatement.setString(3, hero.getHrRole());
            return pstatement.executeUpdate() > 0;

        } catch (SQLIntegrityConstraintViolationException e) {
            // Handle duplicate entry error
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Duplicate Entry");
            alert.setContentText("A hero with this name already exists.");
            alert.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean deleteHero(Hero hero) {

        try {
            pstatement = getDBConnection().prepareStatement("DELETE FROM hero WHERE HeroID=?");
            pstatement.setString(1, hero.getHrId()); 

            int res = pstatement.executeUpdate();
            if (res > 0) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean updateHero(Hero hero) {

        try {
            pstatement = getDBConnection().prepareStatement("UPDATE hero SET HeroID = ?, HeroName = ?, HeroRole = ? WHERE HeroID = ?");
            pstatement.setString(1, hero.getHrId());
            pstatement.setString(2, hero.getHrName());
            pstatement.setString(3, hero.getHrRole());
            pstatement.setString(4, hero.getHrId());
            
            int res = pstatement.executeUpdate();
            
            if (res > 0) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // itemTableController
    
    public static ResultSet getItems() {

        ResultSet result = null;

        try {
            String query = "SELECT * FROM item";
            result = handler.execQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static boolean addItem(Item item) {
        try {
            pstatement = getDBConnection().prepareStatement("INSERT INTO item (ItemID, ItemName, ItemType, ItemPrice) VALUES (?, ?, ?, ?)");
            pstatement.setInt(1, item.getItId());
            pstatement.setString(2, item.getItName());
            pstatement.setString(3, item.getItType());
            pstatement.setInt(4, item.getItPrice());
            return pstatement.executeUpdate() > 0;

        } catch (SQLIntegrityConstraintViolationException e) {
            // Handle duplicate entry error
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Duplicate Entry");
            alert.setContentText("An item with this name already exists.");
            alert.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean deleteItem(Item item) {

        try {
            pstatement = getDBConnection().prepareStatement("DELETE FROM item WHERE ItemID=?");
            pstatement.setInt(1, item.getItId()); 

            int res = pstatement.executeUpdate();
            if (res > 0) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean updateItem(Item item) {

        try {
            pstatement = getDBConnection().prepareStatement("UPDATE item SET ItemID = ?, ItemName = ?, ItemType = ?, ItemPrice = ? WHERE ItemID = ?");
            pstatement.setInt(1, item.getItId());
            pstatement.setString(2, item.getItName());
            pstatement.setString(3, item.getItType());
            pstatement.setInt(4, item.getItPrice());
            pstatement.setInt(5, item.getItId());
            
            int res = pstatement.executeUpdate();
            
            if (res > 0) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // skinTableController

    public static ResultSet getSkins() {

        ResultSet result = null;

        try {
            String query = "SELECT SkinID, SkinName, SkinType, HeroName FROM skin INNER JOIN hero ON skin.HeroID = hero.HeroID";
            result = handler.execQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static boolean addSkin(Skin skin) {
        try {

            String queryHero = "SELECT HeroID FROM Hero WHERE HeroName = ?";
            PreparedStatement ps = getDBConnection().prepareStatement(queryHero);
            ps.setString(1, skin.getHrName());
            ResultSet rs = ps.executeQuery();
            String HeroID;
            if(rs.next()){
                HeroID = rs.getString("HeroID");
            } else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setContentText("Hero name does not exist");
                alert.showAndWait();
                return false;
            }

            String insertQuery = "INSERT INTO skin (SkinID, SkinName, SkinType, HeroID) VALUES (?, ?, ?, ?)";
            pstatement = getDBConnection().prepareStatement(insertQuery);
            pstatement.setInt(1, skin.getSkId());
            pstatement.setString(2, skin.getSkName());
            pstatement.setString(3, skin.getSkType());
            pstatement.setString(4, HeroID);

            int rowsAffected = pstatement.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            // Handle duplicate entry error
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Duplicate Entry");
            alert.setContentText("An item with this name already exists.");
            alert.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean deleteSkin(Skin skin) {

        try {
            pstatement = getDBConnection().prepareStatement("DELETE FROM skin WHERE SkinID = ?");
            pstatement.setInt(1, skin.getSkId());

            int res = pstatement.executeUpdate();
            if (res > 0) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;

    }

    public static boolean updateSkin(Skin skin) {

        try {
            String queryHero = "SELECT HeroID FROM Hero WHERE HeroName = ?";
            PreparedStatement ps = getDBConnection().prepareStatement(queryHero);
            ps.setString(1, skin.getHrName());
            ResultSet rs = ps.executeQuery();
            String HeroID;
            if(rs.next()){
                HeroID = rs.getString("HeroID");
            } else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setContentText("Hero name does not exist");
                alert.showAndWait();
                return false;
            }

            pstatement = getDBConnection().prepareStatement("UPDATE skin SET SkinID = ?, SkinName = ?, SkinType = ?, HeroID = ? WHERE SkinID = ?");
            pstatement.setInt(1, skin.getSkId());
            pstatement.setString(2, skin.getSkName());
            pstatement.setString(3, skin.getSkType());
            pstatement.setString(4, HeroID);
            pstatement.setInt(5, skin.getSkId());
            
            int res = pstatement.executeUpdate();
            
            if (res > 0) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}