package sample.Adaugare.Elev;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import sample.Main;
import sample.Obiecte.Elev;

import java.sql.SQLException;

public class ElevController {
    @FXML
    private Label MesajLabel;
    @FXML
    private TextField NumeElevField;
    @FXML
    private TextField PrenumeElevField;
    @FXML
    private TextField PrenumeMamaField;
    @FXML
    private TextField PrenumeTataField;
    @FXML
    private TextField cnpField;
    @FXML
    private TextField NumarTelefonElevField;
    @FXML
    private TextField NumarTelefonParinteElevField;
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
    private ChoiceBox LiteraChoiceBox;


    public void initialize() {
    }

    public void AdaugaElev(){
        String numeElev = NumeElevField.getText();
        String prenumeElev = PrenumeElevField.getText();
        String prenumeMama = PrenumeMamaField.getText();
        String prenumeTata = PrenumeTataField.getText();
        long cnp = Long.parseLong(cnpField.getText());
        String numarTelefonElev = NumarTelefonElevField.getText();
        String numarTelefonParinte = NumarTelefonParinteElevField.getText();
        String judet = JudetField.getText();
        String localitate = LocalitateField.getText();
        String strada = StradaField.getText();
        int numar = Integer.parseInt(NumarStradaField.getText());
        String bloc = NumarBlocField.getText();
        String scara = ScaraField.getText();
        int etaj;
        if(EtajField.getText().equals("")){
            etaj = 0;
        }else{
            etaj = Integer.parseInt(EtajField.getText());
        }
        int apartament;
        if(NumarApartamentField.getText().equals("")){
            apartament= 0;
        }else{
            apartament = Integer.parseInt(NumarApartamentField.getText());
        }
        //String clasa = (String) ClasaChoiceBox.getValue();
        String litera =(String) LiteraChoiceBox.getValue();
        int idClasa = Main.getDbconnection().ReturnareIdClasa("IX",litera);
        System.out.println(idClasa);

        Elev elev = new Elev(numeElev,prenumeElev,cnp,judet,localitate,strada,numar,bloc,scara,etaj,apartament,idClasa,prenumeMama,prenumeTata,numarTelefonElev,numarTelefonParinte);


        if(Main.getDbconnection().VerificareLocClasa(elev.getIdClasa())<28) {
            if (Main.getDbconnection().VerificareCnpElev(elev.getCnp())) {
                Elev elev1 = Main.getDbconnection().CautareCnpElev(elev.getCnp());
                MesajLabel.setText("Dupa verificare, s-a constatat ca cnp-ul elevului este deja existent " + elev1);
            } else {
                Main.getDbconnection().AdaugareElev(elev);
                MesajLabel.getStyleClass().clear();
                MesajLabel.getStyleClass().add("Label3");
                MesajLabel.setText("Elevul " + elev + " a fost adaugat cu succes!");
            }
        }else {
            MesajLabel.getStyleClass().clear();
            MesajLabel.getStyleClass().add("Label2");
            MesajLabel.setText("Nu se poate adauga încă un elev în această clasă, deoarece există deja 28 de elevi în clasă.");
        }
    }


}
