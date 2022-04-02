package uicontrollers;

import businessLogic.BlFacade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import ui.MainGUI;

import java.net.URL;
import java.util.ResourceBundle;

public class RemoveBetController implements Controller{

    private MainGUI mainGUI;
    private BlFacade businessLogic;


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnRemove;

    @FXML
    private ComboBox<?> comboEvents;

    @FXML
    private ComboBox<?> comboEvents1;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Label lblErrorMinBet;

    public RemoveBetController() {
    }

    @FXML
    void removeClick(ActionEvent event) {

    }

    @FXML
    void closeClick(ActionEvent event) {

    }



    @Override
    public void setMainApp(MainGUI mainGUI) {

    }
}
