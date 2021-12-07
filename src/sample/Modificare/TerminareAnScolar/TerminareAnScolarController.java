package sample.Modificare.TerminareAnScolar;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import sample.Main;

import java.sql.SQLException;

public class TerminareAnScolarController {
    @FXML
    private Label MesajLabel;

    public void TerminareAn() throws SQLException {
        if(Main.getDbconnection().getSemestruActiv().equals("II")) {
            if (Main.getDbconnection().TerminareAnScolar()) {
                MesajLabel.getStyleClass().clear();
                MesajLabel.getStyleClass().add("Label3");
                MesajLabel.setText("Anul scolar s-a terminat!");
            } else {
                MesajLabel.getStyleClass().add("Label2");
                MesajLabel.setText("Anul scolar nu s-a terminat!");
            }
        }else{
            MesajLabel.getStyleClass().clear();
            MesajLabel.getStyleClass().add("Label2");
            MesajLabel.setText("Nu s-a terminat nici primul semestru!!");
        }
    }
}
