package sample.Vizualizare.NoteElev;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import sample.Main;
import sample.Obiecte.Elev;
import sample.Obiecte.Nota;

import java.sql.SQLException;

public class NoteElevController {
    @FXML
    private ChoiceBox<String> SemestruChoiceBox;
    @FXML
    private TableView<Nota> NoteTableView;

    private Elev elev;

    public void initialize() throws SQLException {
        elev = Main.getDbconnection().ReturnareElevDupaID(Main.getDbconnection().getIdUtilizator());
        if(elev.getIdClasa()>=1 && elev.getIdClasa()<=4) elev.setClasa("IX");
        if(elev.getIdClasa()>=5 && elev.getIdClasa()<=8) elev.setClasa("X");
        if(elev.getIdClasa()>=9 && elev.getIdClasa()<=12) elev.setClasa("XI");
        if(elev.getIdClasa()>=13 && elev.getIdClasa()<=16) elev.setClasa("XII");
        PrevizualizareNoteElev();
    }

    @FXML
    public void PrevizualizareNoteElev() throws SQLException {
        ObservableList<Nota> note = Main.getDbconnection().ReturnareNoteElev(elev);
        ObservableList<Nota> noteFiltrate = FXCollections.observableArrayList();

        switch (SemestruChoiceBox.getValue()){
            case "I și II": noteFiltrate=note;
                            break;
            case "I":   for(Nota n:note){
                        if(n.getSemestru().equals("I")) noteFiltrate.add(n);
                        }
                        break;
            case "II":  for(Nota n:note){
                        if(n.getSemestru().equals("II")) noteFiltrate.add(n);
                        }
                        break;
        }
        NoteTableView.setItems(note);
    }
}
