package uicontrollers;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import businessLogic.BlFacade;
import configuration.ConfigXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import ui.MainGUI;

public class CheckMyResultsController implements Controller {
    public CheckMyResultsController(BlFacade bl) {
        this.businessLogic = bl;
    }

    private MainGUI mainGUI;
    private BlFacade businessLogic;

    @Override public void setMainApp(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }

}