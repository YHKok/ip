package grimm.app;

import java.io.IOException;

import grimm.ui.MainWindow;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * A GUI for Grimm using FXML.
 */
public class Main extends Application {

    private Grimm grimm = new Grimm();
    private static Stage stage;

    @Override
    public void start(Stage stage) {
        try {
            Main.stage = stage;
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

    public static void exit() {
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(event -> Main.stage.close());
        pause.play();
    }
}

