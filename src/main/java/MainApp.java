import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


// Clase principal que ejecuta el programa
public class MainApp extends Application{

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("/ui/PersoanjeView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 566);
        scene.getStylesheets().add(getClass().getResource("/ui/estilos.css").toExternalForm());
        stage.setTitle("CRUD Personajes - MongoDB");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        Application.launch(MainApp.class, args);
    }
}
