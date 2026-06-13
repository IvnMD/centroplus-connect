package es.ies.puerto;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/es/ies/puerto/fxml/main.fxml"));
        Scene scene = new Scene(loader.load(), 900, 650);
        scene.getStylesheets().add(
                getClass().getResource("/es/ies/puerto/css/estilos.css").toExternalForm());
        stage.setTitle("CentroPlus Connect");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}