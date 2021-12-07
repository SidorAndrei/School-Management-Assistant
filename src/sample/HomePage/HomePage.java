package sample.HomePage;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import sample.Main;

public class HomePage {
    @FXML
    private Label SecondLabel;
    @FXML
    private Label FirstLabel;

    public void initialize(){
        FirstLabel.setText("Bine ai venit " + Main.getDbconnection().getNumeUtilizator() + " " + Main.getDbconnection().getPrenumeUtilizator() + "!");
        String conxiune="";
        if(Main.getDbconnection().getCodAcces()==5){
            conxiune="V-ați conectat în calitate de secretar!";
        }else
        if(Main.getDbconnection().getCodAcces()==4){
            conxiune="V-ați conectat în calitate de profesor!";
        }else
        if(Main.getDbconnection().getCodAcces()==3){
            conxiune="V-ați conectat în calitate de părinte!";
        }else
        if(Main.getDbconnection().getCodAcces()==2){
            conxiune="V-ați conectat în calitate de părinte!";
        }else
        if(Main.getDbconnection().getCodAcces()==1){
            conxiune="V-ați conectat în calitate de elev!";
        }
        SecondLabel.setText(conxiune);
    }

}
