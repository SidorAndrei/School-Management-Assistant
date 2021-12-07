package sample.StartingScenes.StartPage;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import sample.Adaugare.ContUtilizator.ContUtilizatorController;
import sample.Main;
import sample.Obiecte.ContUtilizator;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;

public class StartPageController {

    @FXML
    private BorderPane MainPage;

    public StartPageController(){

    }

    public void initialize(){
        MainPage.setId("background");
    }

    public void LoginConnection() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/sample/StartingScenes/Login/Login.fxml")));
        Scene scene = new Scene(root);


        scene.getStylesheets().add("Style.css");
        Main.getGuiStage().getIcons().add(new Image("Icon.png"));
        Main.getGuiStage().setTitle("SMA - Login");
        Main.getGuiStage().setScene(scene);
        Main.getGuiStage().setFullScreen(true);

    }

    public void CheckConnection() throws IOException {
        if(Main.getDbconnection()!=null){
            //System.out.printf("%s is on an active connection code %s\n",activeConnection.getUsername(),activeConnection.getCodAcces());
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("CHECKED Connection");
            alert.setHeaderText("Connection found!");
            String s = "There is a possible connection!\nPlease log in to the School Management Assistant.";
            alert.setContentText(s);
            alert.show();
        }else {
            //System.out.println("There is no active connection!");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("CHECKED Connection");
            alert.setHeaderText("No Connection found!");
            String s = "There is no possible connection!";
            alert.setContentText(s);
            alert.show();
        }

        /*Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/sample/StartingScenes/MainPageAdmin/MainPageAdmin.fxml")));
        Scene scene = new Scene(root);
        Main.getGuiStage().setScene(scene);
        Main.getGuiStage().setMaximized(true);*/
    }

    public void SignUp() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/sample/Adaugare/ContUtilizator/ContUtilizator2.fxml")));
        Scene scene = new Scene(root);
        Main.getGuiStage().setScene(scene);

    }





    public void EXIT(){
        System.exit(10);
    }

    
    
}
