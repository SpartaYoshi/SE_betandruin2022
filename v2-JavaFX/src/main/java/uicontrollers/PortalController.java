package uicontrollers;

import businessLogic.BlFacade;
import configuration.ConfigXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import ui.MainGUI;

public class PortalController implements Controller {

    private MainGUI mainGUI;
    private BlFacade businessLogic;
    @FXML private ToggleGroup lang;
    @FXML private Label rebootInfo;

    public PortalController(BlFacade bl) {
        this.businessLogic = bl;
    }


    @FXML void selectLanguage(ActionEvent event) {
        String sel = ((RadioButton) lang.getSelectedToggle()).getText();
        switch (sel) {
            case "English":
                ConfigXML.getInstance().setLocale("en");
                break;
            case "Español":
                ConfigXML.getInstance().setLocale("es");
                break;
            case "Euskara":
                ConfigXML.getInstance().setLocale("eus");
                break;
            default:
                break;
        }
        rebootInfo.setVisible(true);
    }

    @FXML void selectBrowse(ActionEvent event) {
        mainGUI.showBrowseQuestions();
    }
    @FXML void selectCreateQuestion(ActionEvent event) { mainGUI.showCreateQuestion();}
    @FXML void selectLogin(ActionEvent event) { mainGUI.showUserLogin();}
    @FXML void selectRegister(ActionEvent event) {
        mainGUI.showUserRegister();
    }
    @FXML void selectPlaceBet(ActionEvent event) {mainGUI.showPlaceABet();}
    @FXML void selectRemoveBet(ActionEvent event) {mainGUI.showRemoveABet();}
    @FXML void selectDeposit(ActionEvent event) { mainGUI.showDepositMoney();}
    @FXML void selectCreateNewEvent(ActionEvent event) { mainGUI.showCreateEvent();}
    @FXML void selectRemoveEvent(ActionEvent event) { mainGUI.showRemoveEvent();}
    @FXML void selectSetFee(ActionEvent event) { mainGUI.showSetFee();}
    @FXML void selectShowMovements(ActionEvent event) { mainGUI.showShowMovements();}

    @FXML void selectLogout(ActionEvent event) {
        businessLogic.setSessionMode("Anon");
        mainGUI.showPortal();
    }

    @Override public void setMainApp(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }
}
