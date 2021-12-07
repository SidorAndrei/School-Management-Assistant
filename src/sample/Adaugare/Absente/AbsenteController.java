package sample.Adaugare.Absente;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import sample.Main;
import sample.Obiecte.Absenta;
import sample.Obiecte.Elev;




public class AbsenteController {

    @FXML
    private Label MesajLabel;
    @FXML
    private ChoiceBox ClasaChoiceBox;
    @FXML
    private ChoiceBox LiteraChoiceBox;
    @FXML
    private DatePicker DataAbsenta;
    @FXML
    private ChoiceBox<Elev> ElevChoiceBox;
    @FXML
    private ChoiceBox MotivataChoiceBox;

    @FXML
    public void handleClasaChoiceBox() {
        ElevChoiceBox.getItems().clear();
        ObservableList<Elev> elevi;
        elevi = Main.getDbconnection().ReturnareEleviDinClasa(Main.getDbconnection().ReturnareIdClasa(ClasaChoiceBox.getValue().toString(), LiteraChoiceBox.getValue().toString()));

        ElevChoiceBox.setItems(elevi);
        if(!elevi.isEmpty()){
            ElevChoiceBox.setValue(elevi.get(0));
        }
    }

    @FXML
    public void AdaugareAbsenta(){
        Absenta absenta = new Absenta(ElevChoiceBox.getValue().getIdElev(),Main.getDbconnection().getMaterieProfesor(),DataAbsenta.getValue(),MotivataChoiceBox.getValue().toString());
            Main.getDbconnection().AdaugareAbsenta(absenta);
            if(Main.getDbconnection().VerificareAbsenta(absenta)) {
                MesajLabel.getStyleClass().clear();
                MesajLabel.getStyleClass().add("Label3");
                MesajLabel.setText("Absență introdusă!");
            }else{
                MesajLabel.getStyleClass().clear();
                MesajLabel.getStyleClass().add("Label2");
                MesajLabel.setText("Absența nu a fost introdusă!");
            }
    }
}
