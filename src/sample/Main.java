package sample;

import Conn.ActiveConnection;
import Conn.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.SQLException;

public class Main extends Application {
    private static double xOffset = 0;
    private static double yOffset = 0;
    private static Stage guiStage;
    private static ActiveConnection activeConnection;
    private static DBConnection dbconnection;

    static {
        try {
            dbconnection = new DBConnection();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static DBConnection getDbconnection() {
        return dbconnection;
    }

    public static ActiveConnection getActiveConnection() {
        return activeConnection;
    }

    public static void setActiveConnection(ActiveConnection activeConnection) {
        Main.activeConnection = activeConnection;
    }

    public static Stage getGuiStage() {
        return guiStage;
    }


    @Override
    public void start(Stage primaryStage) throws Exception{
        guiStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("StartingScenes/StartPage/StartPage.fxml"));
        guiStage.setTitle("School Management Application - by SMA (Sidor Marian-Andrei)");
        guiStage.getIcons().add(new Image("Icons/icon.png"));
        Scene scene = new Scene(root, 300, 333);
        scene.getStylesheets().add("Style.css");
        guiStage.setScene(scene);
        guiStage.setResizable(true);
        guiStage.setOnCloseRequest(event -> {
            try {
                dbconnection.close();
                System.exit(1);
            } catch (Exception e) {e.printStackTrace();}
        });
        guiStage.initStyle(StageStyle.DECORATED.UNDECORATED);

        guiStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }

    public static double getxOffset() {
        return xOffset;
    }

    public static void setxOffset(double xOffset) {
        Main.xOffset = xOffset;
    }

    public static double getyOffset() {
        return yOffset;
    }

    public static void setyOffset(double yOffset) {
        Main.yOffset = yOffset;
    }
}
