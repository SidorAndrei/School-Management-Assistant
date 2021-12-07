package sample.Vizualizare.Absente;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import sample.Main;
import sample.Obiecte.Absenta;
import sample.Obiecte.Elev;

@SuppressWarnings("rawtypes")
public class AbsenteController {
    @FXML
    private ChoiceBox SemestruChoiceBox;
    @FXML
    private Label AbsenteMotivate;
    @FXML
    private Label AbsenteNemotivate;
    @FXML
    private TableView<Absenta> AbsenteTableView;
    @FXML
    private ChoiceBox<Elev> ElevChoiceBox;
    @FXML
    private ChoiceBox LiteraChoiceBox;
    @FXML
    private ChoiceBox ClasaChoiceBox;

    private Elev elevActual;

    public void initialize(){
        handleClasaChoiceBox();
    }
    @FXML
    public void handleClasaChoiceBox() {

        ElevChoiceBox.getItems().clear();
        ObservableList<Elev> elevi;
        elevi = Main.getDbconnection().ReturnareEleviDinClasa(Main.getDbconnection().ReturnareIdClasa(ClasaChoiceBox.getValue().toString(), LiteraChoiceBox.getValue().toString()));

        ElevChoiceBox.setItems(elevi);
        if(!elevi.isEmpty()){
            ElevChoiceBox.setValue(elevi.get(0));
            elevActual.setClasa(ClasaChoiceBox.getValue().toString());
            PrevizualizareAbsenteElev();
        }else{
            AbsenteTableView.getItems().clear();
        }

    }
    @FXML
    public void handleElevChoiceBox() {
        if(!ElevChoiceBox.getItems().isEmpty()){
            elevActual=ElevChoiceBox.getValue();
            elevActual.setClasa(ClasaChoiceBox.getValue().toString());
            PrevizualizareAbsenteElev();
        }
    }

    private void PrevizualizareAbsenteElev() {
        ObservableList<Absenta> absente = Main.getDbconnection().ReturnareAbsenteElev(elevActual);
        ObservableList<Absenta> absenteFiltrate = FXCollections.observableArrayList();
        int motivate=0,nemotivate=0;
        switch (SemestruChoiceBox.getValue().toString()){
            case "I": for(Absenta a:absente){
                if(a.getSemestru().equals("I")){
                    absenteFiltrate.add(a);
                    if(a.getMotivata().equals("DA")) motivate++;
                    if(a.getMotivata().equals("NU")) nemotivate++;
                }
            }
            AbsenteMotivate.setText("Absente motivate: " + motivate);
            AbsenteNemotivate.setText("Absente nemotivate: " + nemotivate);
            AbsenteTableView.setItems(absenteFiltrate);
            break;
            case "II": for(Absenta a:absente){
                if(a.getSemestru().equals("II")){
                    absenteFiltrate.add(a);
                    if(a.getMotivata().equals("DA")) motivate++;
                    if(a.getMotivata().equals("NU")) nemotivate++;
                }
                }
                AbsenteMotivate.setText("Absente motivate: " + motivate);
                AbsenteNemotivate.setText("Absente nemotivate: " + nemotivate);
                AbsenteTableView.setItems(absenteFiltrate);
                break;
            default: for(Absenta a:absente){
                    absenteFiltrate.add(a);
                    if(a.getMotivata().equals("DA")) motivate++;
                    if(a.getMotivata().equals("NU")) nemotivate++;
                }
                AbsenteMotivate.setText("Absente motivate: " + motivate);
                AbsenteNemotivate.setText("Absente nemotivate: " + nemotivate);
                AbsenteTableView.setItems(absenteFiltrate);
                break;
        }
    }

}
