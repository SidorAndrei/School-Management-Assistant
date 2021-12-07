package sample.Adaugare.ContUtilizator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import sample.Main;
import sample.Obiecte.ContUtilizator;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class ContUtilizatorController {
    @FXML
    private Label MesajLabel;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label cnpLabel;
    @FXML
    private Label prenumeLabel;
    @FXML
    private Label numeLabel;
    @FXML
    private ChoiceBox UtilizatorChoiceBox;
    @FXML
    private TextField NumeField;
    @FXML
    private TextField PrenumeField;
    @FXML
    private TextField CnpField;

    private int codUtilizator;

    public void initialize(){
        handleChoiceBox();
    }

    public void handleChoiceBox(){
        if(UtilizatorChoiceBox.getValue().toString().equals("Elev")){
            numeLabel.setText("Nume:");
            prenumeLabel.setText("Prenume:");
            cnpLabel.setText("CNP Elev:");
            codUtilizator = 1;
        }
        if(UtilizatorChoiceBox.getValue().toString().equals("Mamă")) {
            numeLabel.setText("Nume:");
            prenumeLabel.setText("Prenume:");
            cnpLabel.setText("CNP Elev:");
            codUtilizator = 2;
        }
        if(UtilizatorChoiceBox.getValue().toString().equals("Tată")){
            numeLabel.setText("Nume:");
            prenumeLabel.setText("Prenume:");
            cnpLabel.setText("CNP Elev:");
            codUtilizator = 3;
        }
        if(UtilizatorChoiceBox.getValue().toString().equals("Profesor")){
            numeLabel.setText("Nume:");
            prenumeLabel.setText("Prenume:");
            cnpLabel.setText("CNP:");
            codUtilizator = 4;
        }
        if(UtilizatorChoiceBox.getValue().toString().equals("Secretar")){
            numeLabel.setText("Numer:");
            prenumeLabel.setText("Prenume:");
            cnpLabel.setText("CNP:");
            codUtilizator = 5;
        }
    }

    public ContUtilizator processResults() {
        ContUtilizator contUtilizator = new ContUtilizator();
        contUtilizator.setCodAcces(codUtilizator);
        contUtilizator.setNumeUtilizator(NumeField.getText());
        contUtilizator.setPrenumeUtilizator(PrenumeField.getText());
        contUtilizator.setUsername(usernameField.getText());
        contUtilizator.setPassword(passwordField.getText());
        try {
            contUtilizator.setIdUtilizator(Main.getDbconnection().VerificareExistenta(
                    contUtilizator.getNumeUtilizator(), contUtilizator.getPrenumeUtilizator(),
                    Long.valueOf(CnpField.getText()),codUtilizator));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return contUtilizator;
    }

    public void AdaugaCont(){
        ContUtilizator contUtilizator = new ContUtilizator();
        contUtilizator.setCodAcces(codUtilizator);
        contUtilizator.setNumeUtilizator(NumeField.getText());
        contUtilizator.setPrenumeUtilizator(PrenumeField.getText());
        contUtilizator.setUsername(usernameField.getText());
        contUtilizator.setPassword(passwordField.getText());
        try {
            contUtilizator.setIdUtilizator(Main.getDbconnection().VerificareExistenta(
                    contUtilizator.getNumeUtilizator(), contUtilizator.getPrenumeUtilizator(),
                    Long.valueOf(CnpField.getText()),codUtilizator));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if(contUtilizator.getIdUtilizator()!=0){
            if(Main.getDbconnection().VerificareUsername(contUtilizator.getUsername())){
                try {
                    Main.getDbconnection().AdaugareContUtilizator(contUtilizator);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                MesajLabel.getStyleClass().clear();
                MesajLabel.getStyleClass().add("Label3");
                MesajLabel.setText("Contul a fost creat cu succes! Acum vă puteți loga în contul dumneavoastră.");

            } else{
                MesajLabel.getStyleClass().clear();
                MesajLabel.getStyleClass().add("Label2");
                MesajLabel.setText("Username-ul introdus există deja în baza de date!");
            }
        } else{
            MesajLabel.getStyleClass().clear();
            MesajLabel.getStyleClass().add("Label2");
            MesajLabel.setText("Înregistrarea a eșuat! Datele persoanei introduse nu au fost găsite în baza de date!");
        }
    }

    public void AdaugaCont2() throws IOException {
        ContUtilizator contUtilizator = new ContUtilizator();
        contUtilizator.setCodAcces(codUtilizator);
        contUtilizator.setNumeUtilizator(NumeField.getText());
        contUtilizator.setPrenumeUtilizator(PrenumeField.getText());
        contUtilizator.setUsername(usernameField.getText());
        contUtilizator.setPassword(passwordField.getText());
        try {
            contUtilizator.setIdUtilizator(Main.getDbconnection().VerificareExistenta(
                    contUtilizator.getNumeUtilizator(), contUtilizator.getPrenumeUtilizator(),
                    Long.valueOf(CnpField.getText()),codUtilizator));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if(contUtilizator.getIdUtilizator()!=0){
            if(Main.getDbconnection().VerificareUsername(contUtilizator.getUsername())){
                try {
                    Main.getDbconnection().AdaugareContUtilizator(contUtilizator);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                Parent root = FXMLLoader.load(getClass().getResource("/sample/StartingScenes/StartPage/StartPage.fxml"));
                Scene scene = new Scene(root,300,333);
                Main.getGuiStage().setScene(scene);
            } else{
                MesajLabel.getStyleClass().clear();
                MesajLabel.getStyleClass().add("Label2");
                MesajLabel.setText("Username-ul introdus există deja în baza de date!");
            }
        } else{
            MesajLabel.getStyleClass().clear();
            MesajLabel.getStyleClass().add("Label2");
            MesajLabel.setText("Înregistrarea a eșuat! Datele persoanei introduse nu au fost găsite în baza de date!");
        }
    }


    public void CancelSignUp() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/sample/StartingScenes/StartPage/StartPage.fxml")));
        Scene scene = new Scene(root,300,333);
        Main.getGuiStage().setScene(scene);
    }

}

