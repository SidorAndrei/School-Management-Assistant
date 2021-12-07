package sample.Vizualizare.AbsenteElev;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import sample.Main;
import sample.Obiecte.Absenta;
import sample.Obiecte.Elev;

public class AbsenteController {
        @FXML
        private ChoiceBox SemestruChoiceBox;
        @FXML
        private Label AbsenteMotivate;
        @FXML
        private Label AbsenteNemotivate;
        @FXML
        private TableView<Absenta> AbsenteTableView;

        public void initialize(){
            PrevizualizareAbsenteElev();
        }

        @FXML
        private void PrevizualizareAbsenteElev() {
            Elev elev = Main.getDbconnection().ReturnareElevDupaID(Main.getDbconnection().getIdUtilizator());
            if(elev.getIdClasa()>=1 && elev.getIdClasa()<=4) elev.setClasa("IX");
            if(elev.getIdClasa()>=5 && elev.getIdClasa()<=8) elev.setClasa("X");
            if(elev.getIdClasa()>=9 && elev.getIdClasa()<=12) elev.setClasa("XI");
            if(elev.getIdClasa()>=13 && elev.getIdClasa()<=16) elev.setClasa("XII");
            System.out.println(Main.getDbconnection().getIdUtilizator());
            ObservableList<Absenta> absente = Main.getDbconnection().ReturnareAbsenteElev(elev);
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
