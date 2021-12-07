package sample.Adaugare.Profesor;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import sample.Main;
import sample.Obiecte.Elev;
import sample.Obiecte.Profesor;

import java.sql.SQLException;

public class ProfesorController {

    @FXML
    private Label MesajLabel;
    @FXML
    private TextField NumeProfesorField;
    @FXML
    private TextField PrenumeProfesorField;
    @FXML
    private TextField cnpField;
    @FXML
    private TextField NumarTelefonProfesorField;
    @FXML
    private TextField JudetField;
    @FXML
    private TextField LocalitateField;
    @FXML
    private TextField StradaField;
    @FXML
    private TextField NumarStradaField;
    @FXML
    private TextField NumarBlocField;
    @FXML
    private TextField ScaraField;
    @FXML
    private TextField EtajField;
    @FXML
    private TextField NumarApartamentField;
    @FXML
    private ChoiceBox<sample.Obiecte.Materie> MaterieChoiceBox;

    public void initialize(){

        MaterieChoiceBox.setItems(Main.getDbconnection().ReturnareMaterii());
    }

    public void AdaugaProfesor()  {
        Profesor profesor = new Profesor(NumeProfesorField.getText(),PrenumeProfesorField.getText(),Long.parseLong(cnpField.getText()),
                JudetField.getText(),LocalitateField.getText(),StradaField.getText(),Integer.parseInt(NumarStradaField.getText()),
                NumarBlocField.getText(),ScaraField.getText(),Integer.parseInt(EtajField.getText()),Integer.parseInt(NumarApartamentField.getText()),
                MaterieChoiceBox.getSelectionModel().getSelectedItem().toString(),NumarTelefonProfesorField.getText());

        try {
            if(Main.getDbconnection().VerificareCnpProfesor(profesor)) {
                MesajLabel.getStyleClass().clear();
                MesajLabel.getStyleClass().add("Label3");
                MesajLabel.setText("Dupa verificare, s-a constatat ca cnp-ul profesorului este deja existent " + profesor);
            } else {
                Main.getDbconnection().AdaugareProfesor(profesor);
                MesajLabel.getStyleClass().clear();
                MesajLabel.getStyleClass().add("Label2");
                MesajLabel.setText("Profesorul " + profesor + " a fost adaugat cu succes!");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
