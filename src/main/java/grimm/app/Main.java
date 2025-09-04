package grimm.app;

import java.io.IOException;

import grimm.ui.MainWindow;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Grimm using FXML.
 */
public class Main extends Application {

    private Grimm grimm = new Grimm();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setMinHeight(220);
            stage.setMinWidth(417);
            fxmlLoader.<MainWindow>getController().setGrimm(grimm);  // inject the Grimm instance
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

