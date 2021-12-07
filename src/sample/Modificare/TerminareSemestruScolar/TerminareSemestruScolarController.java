package sample.Modificare.TerminareSemestruScolar;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import sample.Main;
import sample.Obiecte.Elev;
import sample.Obiecte.Medie;

import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicBoolean;

public class TerminareSemestruScolarController {

    @FXML
    private Label MesajLabel;
    @FXML
    private Label Label;

    public void initialize(){
        Label.setText("Sunteți sigur că trebuie sa încheiați semestrul " + Main.getDbconnection().getSemestruActiv() + "?");
    }

    @FXML
    public void TerminareSemestru() throws SQLException {
        ObservableList<Elev> elevi = Main.getDbconnection().FiltrareEleviActuali(Main.getDbconnection().ReturnareElevi());
        boolean OK = true;
        for(Elev e:elevi){
            ObservableList<Medie> medii = Main.getDbconnection().CalculareMediiElevSelectat(e);
            System.out.println(e);
            boolean NoteElev = Main.getDbconnection().VerificareMedii3(medii);
            if(!NoteElev)OK = NoteElev;
        }

        if (!OK) {
            MesajLabel.getStyleClass().clear();
            MesajLabel.getStyleClass().add("Label2");
            MesajLabel.setText("Semestrul nu s-a incheiat!");
        } else {
            if (Main.getDbconnection().TerminareSemestru()) {
                Main.getDbconnection().setSemestruActiv();
                Label.setText("Sunteți sigur că trebuie sa încheiați semestrul " + Main.getDbconnection().getSemestruActiv() + "?");
                System.out.println(Main.getDbconnection().getSemestruActiv());
                MesajLabel.getStyleClass().clear();
                MesajLabel.getStyleClass().add("Label3");
                MesajLabel.setText("Semestrul s-a încheiat cu succes!");
            }
        }
    }
}
