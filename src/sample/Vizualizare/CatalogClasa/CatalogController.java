package sample.Vizualizare.CatalogClasa;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import sample.Main;
import sample.Obiecte.Catalog;
import sample.Obiecte.Elev;
import sample.Obiecte.Materie;

import java.sql.SQLException;

public class CatalogController  {

    @FXML
    private ChoiceBox ClasaChoiceBox;
    @FXML
    private ChoiceBox LiteraChoiceBox;
    @FXML
    private ChoiceBox<Elev> ElevChoiceBox;
    @FXML
    private TableView<Catalog> CatalogTableView;

    private Elev elevActual;
    
    public void initialize() throws SQLException {
        handleClasaChoiceBox();
    }
    @FXML
    public void handleClasaChoiceBox() throws SQLException {

        CatalogTableView.getItems().clear();
        ObservableList<Elev> elevi;
        elevi = Main.getDbconnection().ReturnareEleviDinClasa(Main.getDbconnection().ReturnareIdClasa(ClasaChoiceBox.getValue().toString(), LiteraChoiceBox.getValue().toString()));
        handleElevi(elevi);

    }


    public void handleElevi(ObservableList<Elev> elevi) {
        CatalogTableView.getItems().clear();
        for(Elev e:elevi) {
            elevActual = e;
            elevActual.setClasa(ClasaChoiceBox.getValue().toString());
            VizualizareNote(elevActual);
        }
    }

    public void VizualizareNote(Elev elev) {
        ObservableList<Materie> materii = null;
        try {
            materii = Main.getDbconnection().ReturnareMateriElev(elevActual);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        ObservableList<Integer> noteMaterie = null;
        Catalog catalog = new Catalog();
        catalog.setElev(elev);
        for(Materie m:materii){
            try {
                noteMaterie=Main.getDbconnection().ReturnareNoteMaterie(m,elevActual);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            switch (m.getNume_Materie()){
                case "Admin":   catalog.setNoteAdmin(noteMaterie);
                                break;
                case "Biologie": catalog.setNoteBiologie(noteMaterie);
                                break;
                case "Chimie": catalog.setNoteChimie(noteMaterie);
                                break;
                case "Educație Fizică și Sport": catalog.setNoteSport(noteMaterie);
                    break;
                case "Engleză": catalog.setNoteEngleza(noteMaterie);
                    break;
                case "Fizică": catalog.setNoteFizica(noteMaterie);
                    break;
                case "Franceză": catalog.setNoteFranceza(noteMaterie);
                    break;
                case "Geografie": catalog.setNoteGeografie(noteMaterie);
                    break;
                case "Informatică": catalog.setNoteInformatica(noteMaterie);
                    break;
                case "Istorie": catalog.setNoteIstorie(noteMaterie);
                    break;
                case "Limba si Literatura Romană": catalog.setNoteRomana(noteMaterie);
                    break;
                case "Matematică": catalog.setNoteMatematica(noteMaterie);
                    break;
                case "Purtare": catalog.setNotePurtare(noteMaterie);
                    break;
                case "Religie": catalog.setNoteReligie(noteMaterie);
                    break;
            }
        }
        CatalogTableView.getItems().add(catalog);
    }
}
