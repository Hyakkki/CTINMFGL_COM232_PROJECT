import javafx.beans.property.SimpleStringProperty;

public class Player {
    
    private final SimpleStringProperty plid;
    private final SimpleStringProperty plusername;
    private final SimpleStringProperty plpassword;
    private final SimpleStringProperty plgameacc;

    public Player(int playerid, String ingameName, String playergameacc, String playerpassword) {
        this.plid = new SimpleStringProperty(String.valueOf(playerid));
        this.plusername = new SimpleStringProperty(ingameName);
        this.plpassword = new SimpleStringProperty(playerpassword);
        this.plgameacc = new SimpleStringProperty(playergameacc);
    }

    public String getPlId() {
        return plid.get();
    }

    public String getPlUsername() {
        return plusername.get();
    }

    public String getPlPassword() {
        return plpassword.get();
    }

    public String getPlGameacc() {
        return plgameacc.get();
    }
}
