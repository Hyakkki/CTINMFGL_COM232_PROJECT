import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Item {
    
    private final SimpleIntegerProperty itid;
    private final SimpleStringProperty itname;
    private final SimpleStringProperty ittype;
    private final SimpleIntegerProperty itprice;
    

    public Item(int itid, String itemname, String itemtype, int itprice) {
        this.itid = new SimpleIntegerProperty(itid);
        this.itname = new SimpleStringProperty(itemname);
        this.ittype = new SimpleStringProperty(itemtype);
        this.itprice = new SimpleIntegerProperty(itprice);
    }

    public int getItId() {
        return itid.get();
    }

    public String getItName() {
        return itname.get();
    }

    public String getItType() {
        return ittype.get();
    }
    
    public int getItPrice() {
        return itprice.get();
    }
}