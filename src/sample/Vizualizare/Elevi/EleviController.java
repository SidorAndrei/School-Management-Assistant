package sample.Vizualizare.Elevi;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import sample.Main;
import sample.Obiecte.Clasa;
import sample.Obiecte.Elev;

public class EleviController {

    @FXML
    private ChoiceBox stareEleviChoiceBox;
    @FXML
    private ChoiceBox<String> sexChoiceBox;
    @FXML
    private ChoiceBox<String> localitateChoiceBox;
    @FXML
    private ChoiceBox<Clasa> clasaChoiceBox;
    @FXML
    private TableView<Elev> eleviTableView;

    public void initialize(){
        ObservableList<Clasa> clase = Main.getDbconnection().ReturnareClase();
        clasaChoiceBox.setItems(clase);
        Clasa clasa = new Clasa();
        clasa.setID(0);
        clasa.setNumar("Toate ");
        clasa.setLitera("clasele");
        clasaChoiceBox.getItems().add(clasa);
        clasaChoiceBox.setValue(clasa);

        ObservableList<String> localitati = Main.getDbconnection().ReturnareLocalitateElevi();
        localitateChoiceBox.setItems(localitati);
        String localitate = "Toate localitatile";
        localitateChoiceBox.getItems().add(localitate);
        localitateChoiceBox.setValue(localitate);

    }


    public void handleChoiceBoxFilter(){
        if(clasaChoiceBox.getValue()!=null && localitateChoiceBox.getValue()!=null)
        if(clasaChoiceBox.getValue().getID()==0 && localitateChoiceBox.getValue().equals("Toate localitatile")){
            ObservableList<Elev> elevi = Main.getDbconnection().ReturnareElevi();
            for (Elev e:elevi) {
                e.setClasa(Main.getDbconnection().VerificareClasa(e.getIdClasa()));
            }
            eleviTableView.setItems(elevi);
        }else if(clasaChoiceBox.getValue().getID()==0){
            ObservableList<Elev> elevi = Main.getDbconnection().ReturnareEleviDinLocalitate(localitateChoiceBox.getValue());
            for (Elev e:elevi) {
                e.setClasa(Main.getDbconnection().VerificareClasa(e.getIdClasa()));
            }
            eleviTableView.setItems(elevi);
        }else if(localitateChoiceBox.getValue().equals("Toate localitatile")){
            ObservableList<Elev> elevi = Main.getDbconnection().ReturnareEleviDinClasa(clasaChoiceBox.getValue().getID());
            for (Elev e:elevi) {
                e.setClasa(Main.getDbconnection().VerificareClasa(e.getIdClasa()));
            }
            eleviTableView.setItems(elevi);
        } else{
            ObservableList<Elev> elevi = Main.getDbconnection().ReturnareEleviDinLocalitateDinClasa(localitateChoiceBox.getValue(), clasaChoiceBox.getValue().getID());
            for (Elev e:elevi) {
                e.setClasa(Main.getDbconnection().VerificareClasa(e.getIdClasa()));
            }
            eleviTableView.setItems(elevi);
        }
    }

    public void handleSexChoiceBoxFilter() {
        switch (sexChoiceBox.getValue()) {
            case "Ambele sexe":
                handleChoiceBoxFilter();
                break;
            case "Masculin": {
                handleChoiceBoxFilter();
                ObservableList<Elev> elevi = eleviTableView.getItems();
                eleviTableView.setItems(Main.getDbconnection().FiltrareEleviSexMasculin(elevi));
                break;
            }
            case "Feminin": {
                handleChoiceBoxFilter();
                ObservableList<Elev> elevi = eleviTableView.getItems();
                eleviTableView.setItems(Main.getDbconnection().FiltrareEleviSexFeminin(elevi));
                break;
            }
        }
    }

    public void handleStareEleviChoiceBoxFilter() {
        String stare = String.valueOf(stareEleviChoiceBox.getValue());
        ObservableList<Elev> elevi;
        switch (stare){
            case "Toti Elevii":
                handleChoiceBoxFilter();
                break;
            case "Elevi actuali":
                handleChoiceBoxFilter();
                elevi = eleviTableView.getItems();
                eleviTableView.setItems(Main.getDbconnection().FiltrareEleviActuali(elevi));
                break;
            case "Absolventi":
                handleChoiceBoxFilter();
                elevi = eleviTableView.getItems();
                eleviTableView.setItems(Main.getDbconnection().FiltrareEleviAbsolventi(elevi));
                break;
            case "Exmatriculati":
                handleChoiceBoxFilter();
                elevi = eleviTableView.getItems();
                eleviTableView.setItems(Main.getDbconnection().FiltrareEleviExmatriculati(elevi));
                break;
        }
    }
}
