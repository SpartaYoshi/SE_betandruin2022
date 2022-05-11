package uicontrollers;

import java.net.URL;
import java.util.ResourceBundle;
import businessLogic.BlFacade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import ui.MainGUI;

public class CheckMyResultsController implements Controller {

    private MainGUI mainGUI;
    private final BlFacade businessLogic;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button closeButton;

    @FXML
    void selectBack(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert closeButton != null : "fx:id=\"closeButton\" was not injected: check your FXML file 'CheckMyResults.fxml'.";

    }

    public CheckMyResultsController(BlFacade bl) {
        businessLogic = bl;
    }

    @FXML
    void selectBack() {
        mainGUI.showMyProfile();
    }

    @Override
    public void setMainApp(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }


}
