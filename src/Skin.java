import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Skin {
    
    private final SimpleIntegerProperty skid;
    private final SimpleStringProperty skname;
    private final SimpleStringProperty sktype;
    private final SimpleStringProperty hrname;
    

    public Skin(int skid, String skinname, String skintype, String heroname) {
        this.skid = new SimpleIntegerProperty(skid);
        this.skname = new SimpleStringProperty(skinname);
        this.sktype = new SimpleStringProperty(skintype);
        this.hrname = new SimpleStringProperty(heroname);
    }

    public int getSkId() {
        return skid.get();
    }

    public String getSkName() {
        return skname.get();
    }

    public String getSkType() {
        return sktype.get();
    }
    
    public String getHrName() {
        return hrname.get();
    }
}