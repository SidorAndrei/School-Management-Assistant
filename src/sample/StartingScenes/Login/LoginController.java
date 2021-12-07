package sample.StartingScenes.Login;

import Conn.ActiveConnection;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import sample.Adaugare.ContUtilizator.ContUtilizatorController;
import sample.Main;
import sample.Obiecte.ContUtilizator;

import java.io.IOException;
import java.sql.*;
import java.util.Optional;

public class LoginController
{
    @FXML
    private Button EXITButton;
    @FXML
    private VBox LoginBox;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button LoginButton;

    public LoginController(){

    }


    public void initialize(){
        LoginButton.setDefaultButton(true);
        EXITButton.setCancelButton(true);
    }

    public void LoginConnection() {
        try {
            String username = usernameField.getText();
            String password = passwordField.getText();
            //if(Main.getDbconnection().getCodAcces()==5)
            {
                if (Main.getDbconnection().LoginConnection(username, password)) {
                    System.out.println("Login Succesful!");
                    ActiveConnection activeConnection = new ActiveConnection(Main.getDbconnection().getUserName(), Main.getDbconnection().getCodAcces(),
                            Main.getDbconnection().getNumeUtilizator(), Main.getDbconnection().getPrenumeUtilizator());
                    Main.setActiveConnection(activeConnection);
                    System.out.printf("%s is on an active connection code %s\n", Main.getActiveConnection().getUsername(), Main.getActiveConnection().getCodAcces());
                    SetScene(Main.getActiveConnection().getCodAcces());
                    Main.getGuiStage().setMaximized(true);
                } else {
                    System.out.println("Login failed!");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Login Failed!");
                    alert.setHeaderText("Login Failed!");
                    String s = "Introduced user deosn't exist!";
                    alert.setContentText(s);
                    alert.show();
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void handleKeyReleased(Event event){

    }
    public void SetScene(int CodAcces) throws IOException {
        if(CodAcces==5){
            Parent root = FXMLLoader.load(getClass().getResource("/sample/StartingScenes/MainPages/MainPageAdmin.fxml"));
            Scene scene = new Scene(root);
            Main.getGuiStage().setScene(scene);
        }else if(CodAcces==4){
            Parent root = FXMLLoader.load(getClass().getResource("/sample/StartingScenes/MainPages/MainPageProfesor.fxml"));
            Scene scene = new Scene(root);
            Main.getGuiStage().setScene(scene);
        }else if(CodAcces==1 || CodAcces==2 || CodAcces==3){
            Parent root = FXMLLoader.load(getClass().getResource("/sample/StartingScenes/MainPages/MainPageElev.fxml"));
            Scene scene = new Scene(root);
            Main.getGuiStage().setScene(scene);
        }
    }
    public void SignUp() throws SQLException {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(Main.getGuiStage().getScene().getWindow());
        dialog.setTitle("Adaugare Cont Utilizator");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/sample/Adaugare/ContUtilizator/ContUtilizator3.fxml"));
        try{

            dialog.getDialogPane().setContent(fxmlLoader.load());
        }catch (IOException e){
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return;
        }

        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent()){
            ContUtilizatorController controller = fxmlLoader.getController();
            ContUtilizator contUtilizator = controller.processResults();
            if(contUtilizator!=null) Main.getDbconnection().AdaugareContUtilizator(contUtilizator);
        }
    }
    @FXML
    public void EXIT(){
        Main.getGuiStage().close();
    }

}
