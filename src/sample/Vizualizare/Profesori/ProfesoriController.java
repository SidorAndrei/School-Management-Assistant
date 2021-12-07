package sample.Vizualizare.Profesori;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import sample.Main;
import sample.Obiecte.Elev;
import sample.Obiecte.Materie;
import sample.Obiecte.Profesor;

public class ProfesoriController {

    @FXML
    private ChoiceBox<String> sexChoiceBox;
    @FXML
    private ChoiceBox<String> localitateChoiceBox;
    @FXML
    private ChoiceBox<Materie> profesorChoiceBox;
    @FXML
    private TableView<Profesor> profesoriTableView;

    public void initialize(){
        ObservableList<Materie> materii = Main.getDbconnection().ReturnareMateriiProfesori();
        profesorChoiceBox.setItems(materii);
        Materie materie = new Materie();
        materie.setNume_Materie("Toate materiile");
        profesorChoiceBox.getItems().add(materie);
        profesorChoiceBox.setValue(materie);
        ObservableList<String> localitati = Main.getDbconnection().ReturnareLocalitateProfesori();
        String localitate = "Toate localitatile";
        localitateChoiceBox.setItems(localitati);
        localitateChoiceBox.getItems().add(localitate);
        localitateChoiceBox.setValue(localitate);
    }

    public void handleChoiceBoxFilter(){
        if (profesorChoiceBox.getValue().toString()!=null && localitateChoiceBox.getValue()!=null)
        if(profesorChoiceBox.getValue().toString().equals("Toate materiile") && localitateChoiceBox.getValue().equals("Toate localitatile")){
            profesoriTableView.getItems().clear();
            ObservableList<Profesor> profesori = Main.getDbconnection().ReturnareProfesori();
            profesoriTableView.setItems(profesori);
        }
        else if(localitateChoiceBox.getValue().equals("Toate localitatile")){
            profesoriTableView.getItems().clear();
            ObservableList<Profesor> profesori = Main.getDbconnection().ReturnareProfesoriDupaMaterie(profesorChoiceBox.getValue().toString());
            profesoriTableView.setItems(profesori);
        }else if(profesorChoiceBox.getValue().toString().equals("Toate materiile")){
            profesoriTableView.getItems().clear();
            ObservableList<Profesor> profesori = Main.getDbconnection().ReturnareProfesoriDupaLocalitate(localitateChoiceBox.getValue());
            profesoriTableView.setItems(profesori);
        }
        else{
            profesoriTableView.getItems().clear();
            ObservableList<Profesor> profesori = Main.getDbconnection().ReturnareProfesoriDupaMaterieSiLocalitate(profesorChoiceBox.getValue().toString(),localitateChoiceBox.getValue().toString());
            profesoriTableView.setItems(profesori);
        }
    }

    public void handleSexChoiceBoxFilter() {
        switch (sexChoiceBox.getValue()) {
            case "Ambele sexe":
                handleChoiceBoxFilter();
                break;
            case "Masculin": {
                handleChoiceBoxFilter();
                ObservableList<Profesor> profesori = profesoriTableView.getItems();
                profesoriTableView.setItems(Main.getDbconnection().FiltrareProfesoriSexMasculin(profesori));
                break;
            }
            case "Feminin": {
                handleChoiceBoxFilter();
                ObservableList<Profesor> profesori = profesoriTableView.getItems();
                profesoriTableView.setItems(Main.getDbconnection().FiltrareProfesoriSexFeminin(profesori));
                break;
            }
        }
    }

}
