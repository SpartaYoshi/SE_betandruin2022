package uicontrollers;

import businessLogic.BlFacade;
import javafx.fxml.FXML;
import ui.MainGUI;

public class CheckMyResultsController implements Controller {
    public CheckMyResultsController(BlFacade bl) {

    }

    @Override public void setMainApp(MainGUI mainGUI) {
    }

    @FXML
    void selectBack() {
        mainGUI.showMyProfile();
    }

}