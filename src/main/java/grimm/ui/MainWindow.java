package grimm.ui;

import grimm.app.Grimm;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Grimm grimm;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/User.png"));
    private Image grimmImage = new Image(this.getClass().getResourceAsStream("/images/Grimm.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Grimm instance */
    public void setGrimm(Grimm g) {
        this.grimm = g;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Grimm's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = this.grimm.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getGrimmDialog(response, grimmImage)
        );
        userInput.clear();
    }
}
