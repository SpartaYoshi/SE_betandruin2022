package uicontrollers;

import java.net.URL;
import java.util.ResourceBundle;

import businessLogic.BlFacade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import ui.MainGUI;

public class PortalController implements Controller {

    private MainGUI mainGUI;
    private BlFacade businessLogic;

    public PortalController(BlFacade bl) {
        this.businessLogic = bl;
    }

    @FXML void selectBrowse(ActionEvent event) {
        mainGUI.showBrowseQuestions();
    }
    @FXML void selectCreate(ActionEvent event) { mainGUI.showCreateQuestion();}
    @FXML void selectLogin(ActionEvent event) {
        mainGUI.showUserLogin();
    }
    @FXML void selectRegister(ActionEvent event) {
        mainGUI.showUserRegister();
    }
    @FXML void selectPlaceABet(ActionEvent event) {mainGUI.showPlaceABet();}
    @FXML void selectRemoveABet(ActionEvent event) {mainGUI.showRemoveABet();}

    @Override public void setMainApp(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }
}
