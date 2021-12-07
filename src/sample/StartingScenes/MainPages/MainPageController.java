package sample.StartingScenes.MainPages;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import sample.Main;

import java.io.IOException;
import java.sql.SQLException;

public class MainPageController
{

    @FXML
    private Label SemestruLabel;
    @FXML
    private TitledPane ModificaTitledPane;
    @FXML
    private TitledPane AdaugaTitledPane;
    @FXML
    private TitledPane VizualizareTitledPane;
    @FXML
    public BorderPane MainBorder;



    public void initialize() {
        start();
        SemestruLabel();
    }

    public void start()  {
        Main.getGuiStage().setFullScreen(true);
        if(Main.getDbconnection().getCodAcces()==5){
            ModificaTitledPane.setExpanded(true);
        }
        if(Main.getDbconnection().getCodAcces()>=4){
            AdaugaTitledPane.setExpanded(true);
        }
        VizualizareTitledPane.setExpanded(true);
        HOME();
    }

    @FXML
    public void HOME(){
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/sample/HomePage/HomePage.fxml"));
        try {
            MainBorder.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        SemestruLabel();
    }

    @FXML
    public void TerminarAnScolar(){
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/sample/Modificare/TerminareAnScolar/TerminareAnScolar.fxml"));
        try {
            MainBorder.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        SemestruLabel();
    }

    @FXML
    public void TerminareSemestru(){
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/sample/Modificare/TerminareSemestruScolar/TerminareSemestruScolar.fxml"));
        try {
            MainBorder.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        SemestruLabel();
    }


    public void SemestruLabel(){
        SemestruLabel.setText("Semestrul " + Main.getDbconnection().getSemestruActiv());
    }

    @FXML
    public void VizualizareElevi(){
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/sample/Vizualizare/Elevi/Elevi.fxml"));
        try {
            MainBorder.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(!Main.getGuiStage().isFullScreen() && !Main.getGuiStage().isMaximized()){
            Main.getGuiStage().setWidth(1900);
            Main.getGuiStage().setHeight(900);
        }
        SemestruLabel();
    }

    @FXML
    public void VizualizareProfesori() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/sample/Vizualizare/Profesori/Profesori.fxml"));
        try {
            MainBorder.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(!Main.getGuiStage().isFullScreen() &&  !Main.getGuiStage().isMaximized()){
            Main.getGuiStage().setWidth(1900);
            Main.getGuiStage().setHeight(900);
        }
        SemestruLabel();
    }

    @FXML
    public void AdaugaManual() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/sample/Adaugare/Manual/Manual.fxml"));
        try {
            MainBorder.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(!Main.getGuiStage().isFullScreen() &&  !Main.getGuiStage().isMaximized()){
            Main.getGuiStage().setWidth(600);
            Main.getGuiStage().setHeight(400);
        }
        SemestruLabel();
    }

    @FXML
    public void AdaugaContUtilizator() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/sample/Adaugare/ContUtilizator/ContUtilizator.fxml"));
        try {
            MainBorder.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(!Main.getGuiStage().isFullScreen() &&  !Main.getGuiStage().isMaximized()){
            Main.getGuiStage().setWidth(900);
            Main.getGuiStage().setHeight(500);
        }
        SemestruLabel();
    }

    @FXML
    public void AdaugaNota() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/sample/Adaugare/Nota/Nota.fxml"));
        try {
            MainBorder.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(!Main.getGuiStage().isFullScreen() &&  !Main.getGuiStage().isMaximized()){
            Main.getGuiStage().setWidth(600);
            Main.getGuiStage().setHeight(400);
        }
        SemestruLabel();
    }

    @FXML
    public void AdaugaAbsenta() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/sample/Adaugare/Absente/Absente.fxml"));
        try {
            MainBorder.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(!Main.getGuiStage().isFullScreen() &&  !Main.getGuiStage().isMaximized()){
            Main.getGuiStage().setWidth(600);
            Main.getGuiStage().setHeight(400);
        }
        SemestruLabel();
    }

    @FXML
    public void AdaugaProfesor() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/sample/Adaugare/Profesor/Profesor.fxml"));
        try {
            MainBorder.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(!Main.getGuiStage().isFullScreen() &&  !Main.getGuiStage().isMaximized()){
            Main.getGuiStage().setX(10);
            Main.getGuiStage().setY(10);
            Main.getGuiStage().setWidth(1350);
            Main.getGuiStage().setHeight(500);
        }
        SemestruLabel();
    }

    @FXML
    public void AdaugaElev() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/sample/Adaugare/Elev/Elev.fxml"));
        try {
            MainBorder.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(!Main.getGuiStage().isFullScreen() &&  !Main.getGuiStage().isMaximized()){
            Main.getGuiStage().setX(10);
            Main.getGuiStage().setY(10);
            Main.getGuiStage().setWidth(1200);
            Main.getGuiStage().setHeight(500);
        }
        SemestruLabel();
    }

    public void CalculareMedie() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/sample/Vizualizare/CalculareMedieElev/CalculareMedieElev.fxml"));
        try {
            MainBorder.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(!Main.getGuiStage().isFullScreen() &&  !Main.getGuiStage().isMaximized()){
            Main.getGuiStage().setWidth(900);
            Main.getGuiStage().setHeight(800);
        }
        SemestruLabel();
    }

    @FXML
    public void EXIT() {
        try {
            Main.getDbconnection().close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.exit(510);
    }




    @FXML
    public void Minimize() {
        Main.getGuiStage().setIconified(true);
    }


    public void VizualizareNote() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/sample/Vizualizare/CatalogClasa/Catalog.fxml"));
        try {
            MainBorder.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        SemestruLabel();
    }

    public void VizualizareAbsente(){
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/sample/Vizualizare/Absente/Absente.fxml"));
        try {
            MainBorder.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        SemestruLabel();
    }


    public void VizualizareNoteElev() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/sample/Vizualizare/NoteElev/NoteElev.fxml"));
        try {
            MainBorder.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        SemestruLabel();
    }

    public void VizualizareAbsenteElev() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/sample/Vizualizare/AbsenteElev/Absente.fxml"));
        try {
            MainBorder.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        SemestruLabel();
    }

    public void CalculareMedieElev() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/sample/Vizualizare/CalculareMedieElev/CalculareMedieElev2.fxml"));
        try {
            MainBorder.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        SemestruLabel();
    }
}
