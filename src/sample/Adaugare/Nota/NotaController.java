package sample.Adaugare.Nota;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import sample.Obiecte.Elev;
import sample.Main;
import sample.Obiecte.Nota;

import java.sql.SQLException;

public class NotaController {

    @FXML
    private Label MesajLabel;
    @FXML
    private ChoiceBox<Integer> NotaChoiceBox;
    @FXML
    private ChoiceBox<Elev> ElevChoiceBox;
    @FXML
    private ChoiceBox LiteraChoiceBox;
    @FXML
    private ChoiceBox ClasaChoiceBox;

    private int nota;


    public void initialize() {
        handleClasaChoiceBox();
        handleNotaChoiceBox();
    }

    @FXML
    public void handleClasaChoiceBox() {
        ElevChoiceBox.getItems().clear();
        ObservableList<Elev> elevi;
        elevi =Main.getDbconnection().ReturnareEleviDinClasa(Main.getDbconnection().ReturnareIdClasa(ClasaChoiceBox.getValue().toString(), LiteraChoiceBox.getValue().toString()));

        ElevChoiceBox.setItems(elevi);
        if(!elevi.isEmpty()){
            ElevChoiceBox.setValue(elevi.get(0));
        }
    }

    @FXML
    private void handleNotaChoiceBox(){
        String sNota = String.valueOf(NotaChoiceBox.getValue());
        switch (sNota){
            case "1": nota=1;
                break;
            case "2": nota=2;
                break;
            case "3": nota=3;
                break;
            case "4": nota=4;
                break;
            case "5": nota=5;
                break;
            case "6": nota=6;
                break;
            case "7": nota=7;
                break;
            case "8": nota=8;
                break;
            case "9": nota=9;
                break;
            case "10": nota=10;
                break;
        }
    }

    public void AdaugaNota() {
        Elev elev = ElevChoiceBox.getSelectionModel().getSelectedItem();
        Nota notaElev = new Nota(nota,elev.getIdElev());

        try {
            if(Main.getDbconnection().AdaugareNota(notaElev)){
                MesajLabel.getStyleClass().clear();
                MesajLabel.getStyleClass().add("Label3");
                MesajLabel.setText("Elevul " + Main.getDbconnection().ReturnareElevDupaID(notaElev.getIdElev()) + " a primit nota " + notaElev.getNota() + " cu succes!");
            }
            else{
                MesajLabel.getStyleClass().clear();
                MesajLabel.getStyleClass().add("Label2");
                MesajLabel.setText("Elevul " + Main.getDbconnection().ReturnareElevDupaID(notaElev.getIdElev()) + " a mai primit nota astazi!");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
