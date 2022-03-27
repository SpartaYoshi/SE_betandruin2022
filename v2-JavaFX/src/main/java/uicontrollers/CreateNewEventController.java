package uicontrollers;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CreateNewEventController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private DatePicker calendar;

    @FXML
    private Button closeButton;

    @FXML
    private Button createEventButton;

    @FXML
    private TextField eventtextField;

    @FXML
    private Label listOfEventsLabel;

    @FXML
    private Label messageLabel;

    @FXML
    private Label writeEventText;

    @FXML
    void jButtonClose_actionPerformed(ActionEvent event) {

    }

    @FXML
    void jButtonCreateEvent_actionPerformed(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert calendar != null : "fx:id=\"calendar\" was not injected: check your FXML file 'CreateNewEvent.fxml'.";
        assert closeButton != null : "fx:id=\"closeButton\" was not injected: check your FXML file 'CreateNewEvent.fxml'.";
        assert createEventButton != null : "fx:id=\"createEventButton\" was not injected: check your FXML file 'CreateNewEvent.fxml'.";
        assert eventtextField != null : "fx:id=\"eventtextField\" was not injected: check your FXML file 'CreateNewEvent.fxml'.";
        assert listOfEventsLabel != null : "fx:id=\"listOfEventsLabel\" was not injected: check your FXML file 'CreateNewEvent.fxml'.";
        assert messageLabel != null : "fx:id=\"messageLabel\" was not injected: check your FXML file 'CreateNewEvent.fxml'.";
        assert writeEventText != null : "fx:id=\"writeEventText\" was not injected: check your FXML file 'CreateNewEvent.fxml'.";

    }

}
