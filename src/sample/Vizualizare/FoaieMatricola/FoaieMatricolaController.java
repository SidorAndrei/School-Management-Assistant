package sample.Vizualizare.FoaieMatricola;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import sample.Main;
import sample.Obiecte.Elev;
import sample.Obiecte.Medie;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;

public class FoaieMatricolaController {
    @FXML
    private TableView<Medie> MediiTableView;
    @FXML
    private ChoiceBox<Elev> ElevChoiceBox;
    @FXML
    private ChoiceBox LiteraChoiceBox;
    @FXML
    private ChoiceBox ClasaChoiceBox;

    private Elev elevActual;

    public void initialize() throws SQLException {
        handleClasaChoiceBox();
    }
    @FXML
    public void handleClasaChoiceBox() throws SQLException {

        ElevChoiceBox.getItems().clear();
        ObservableList<Elev> elevi;
        elevi = Main.getDbconnection().ReturnareEleviDinClasa(Main.getDbconnection().ReturnareIdClasa(ClasaChoiceBox.getValue().toString(), LiteraChoiceBox.getValue().toString()));

        ElevChoiceBox.setItems(elevi);
        if(!elevi.isEmpty()){
            ElevChoiceBox.setValue(elevi.get(0));
            elevActual.setClasa(ClasaChoiceBox.getValue().toString());
            PrevizualizareMedieElev();
        }else{
            MediiTableView.getItems().clear();
        }

    }
    @FXML
    public void handleElevChoiceBox() throws SQLException {
        if(!ElevChoiceBox.getItems().isEmpty()){
            elevActual=ElevChoiceBox.getValue();
            elevActual.setClasa(ClasaChoiceBox.getValue().toString());
            PrevizualizareMedieElev();
        }
    }


    public void PrevizualizareMedieElev() throws SQLException {
        if(!ElevChoiceBox.getItems().isEmpty()){
            int sum=0,count=0;

            ObservableList<Medie> medii=Main.getDbconnection().CalculareMediiElevSelectat(elevActual);
            for(Medie m:medii){
                count++;
                sum+=Double.parseDouble(m.getMedie());
            }
            double medie =(double) sum/count;
            medie=round(medie,2);
            Medie medieTotala = new Medie();
            medieTotala.setMedie(String.valueOf(medie));
            medieTotala.setMaterie("Medie Totala");
            medii.add(medieTotala);
            MediiTableView.setItems(medii);
        }
    }

    private   double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
