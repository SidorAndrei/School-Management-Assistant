package sample.Adaugare.Manual;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import sample.Main;
import sample.Obiecte.Manual;
import sample.Obiecte.Materie;

public class ManualController {

    @FXML
    private Label MesajLabel;
    @FXML
    private TextField EdituraManualField;
    @FXML
    private TextField NumeManualField;
    @FXML
    private ChoiceBox<Materie> MaterieChoiceBox;
    @FXML
    private ChoiceBox ClasaChoiceBox;


    public void initialize(){
        ObservableList<Materie> materii = FXCollections.observableArrayList(
                Main.getDbconnection().ReturnareMateriiClasa(ClasaChoiceBox.getValue().toString()));
        MaterieChoiceBox.setItems(materii);
        MaterieChoiceBox.setValue(materii.get(0));
    }

    public void AdaugaManual(){
        Manual manual = new Manual(NumeManualField.getText(),EdituraManualField.getText(),
                MaterieChoiceBox.getValue().numeMaterie(),ClasaChoiceBox.getValue().toString());
        if(!Main.getDbconnection().VerificareExistentaManual(manual)) {
            Main.getDbconnection().AdaugareManual(manual);
            MesajLabel.getStyleClass().clear();
            MesajLabel.getStyleClass().add("Label3");
            MesajLabel.setText("Manualul " + manual + " a fost adaugat cu succes!");
        }else{
            MesajLabel.getStyleClass().clear();
            MesajLabel.getStyleClass().add("Label2");
            MesajLabel.setText("Dupa verificare, s-a constatat ca manualul este deja existent " + manual);
        }
    }

}
