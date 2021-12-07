package sample.Vizualizare.CalculareMedieElev;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import sample.Main;
import sample.Obiecte.Elev;
import sample.Obiecte.Medie;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;

@SuppressWarnings("rawtypes")
public class CalculareMedieElevController {
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
        if(Main.getDbconnection().getCodAcces()>=4)handleClasaChoiceBox();
        else {
            elevActual=Main.getDbconnection().ReturnareElevDupaID(Main.getDbconnection().getIdUtilizator());
            if(elevActual.getIdClasa()>=1 && elevActual.getIdClasa()<=4) elevActual.setClasa("IX");
            if(elevActual.getIdClasa()>=5 && elevActual.getIdClasa()<=8) elevActual.setClasa("X");
            if(elevActual.getIdClasa()>=9 && elevActual.getIdClasa()<=12) elevActual.setClasa("XI");
            if(elevActual.getIdClasa()>=13 && elevActual.getIdClasa()<=16) elevActual.setClasa("XII");
            PrevizualizareMedieElev2();
        }

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
                System.out.println(m.getMedie()+ " " + m.getMedie2());
                if(!m.getMedie().equals("Nu exista destule note")) {
                    count++;
                    sum += Double.parseDouble(m.getMedie());
                }
            }
            if(count==14){
                double medie =(double) sum/count;
                medie=round(medie,2);
                Medie medieTotala = new Medie();
                medieTotala.setMedie(String.valueOf(medie));
                medieTotala.setMaterie("Medie Totala");
                medii.add(medieTotala);
            }
            MediiTableView.setItems(medii);
        }
    }

    @FXML
    public void PrevizualizareMedieElev2() throws SQLException {
        int sum=0,count=0;

        ObservableList<Medie> medii=Main.getDbconnection().CalculareMediiElevSelectat(elevActual);
        for(Medie m:medii){
            System.out.println(m.getMedie()+ " " + m.getMedie2());
            if(!m.getMedie().equals("Nu exista destule note")) {
                count++;
                sum += Double.parseDouble(m.getMedie());
            }
        }
        if(count==14){
            double medie =(double) sum/count;
            medie=round(medie,2);
            Medie medieTotala = new Medie();
            medieTotala.setMedie(String.valueOf(medie));
            medieTotala.setMaterie("Medie Totala");
            medii.add(medieTotala);
        }
        MediiTableView.setItems(medii);
    }

    private   double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }


    public Elev getElevActual() {
        return elevActual;
    }
}
