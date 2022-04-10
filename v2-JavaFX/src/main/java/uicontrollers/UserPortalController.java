package uicontrollers;

import businessLogic.BlFacade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import ui.MainGUI;

public class UserPortalController implements Controller{

    private MainGUI mainGUI;
    private BlFacade businessLogic;

    public UserPortalController(BlFacade bl) {
        this.businessLogic = bl;
    }


    @FXML void selectBrowse(ActionEvent event) {mainGUI.showBrowseQuestions();}
    @FXML void selectPlaceABet(ActionEvent event) {mainGUI.showPlaceABet();}
    @FXML void selectRemoveABet(ActionEvent event) {mainGUI.showRemoveABet();}




    @Override public void setMainApp(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }


}
