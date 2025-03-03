import javafx.beans.property.SimpleStringProperty;

public class Hero {
    
    private final SimpleStringProperty hrid;
    private final SimpleStringProperty hrname;
    private final SimpleStringProperty hrrole;
    

    public Hero(String heroid, String heroname, String herorole) {
        this.hrid = new SimpleStringProperty(String.valueOf(heroid));
        this.hrname = new SimpleStringProperty(heroname);
        this.hrrole = new SimpleStringProperty(herorole);
    }

    public String getHrId() {
        return hrid.get();
    }

    public String getHrName() {
        return hrname.get();
    }

    public String getHrRole() {
        return hrrole.get();
    }
}