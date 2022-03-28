package uicontrollers;

import businessLogic.BlFacade;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.skin.DatePickerSkin;
import ui.MainGUI;
import utils.Dates;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class UserLoginController implements Controller{
    private MainGUI mainGUI;

    private BlFacade businessLogic;
    private List<LocalDate> holidays = new ArrayList<>();

    public UserLoginController(BlFacade bl) {
        businessLogic = bl;

    }



    @FXML
    void initialize() {}







    @Override
    public void setMainApp(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }
}
