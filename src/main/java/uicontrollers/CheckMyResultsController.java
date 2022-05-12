package uicontrollers;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;

import businessLogic.BlFacade;
import domain.*;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import ui.MainGUI;

public class CheckMyResultsController implements Controller {

    private MainGUI mainGUI;
    private final BlFacade businessLogic;

    @FXML
    private TableView<Result> tableMovements;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<Event, String> columnEvent;

    @FXML
    private TableColumn<Question, String> columnQuestion;

    @FXML
    private TableColumn<Result, Integer> columnResult;


    @FXML
    void initialize() {
        User who = businessLogic.getCurrentUser();
        Vector<Bet> usersBets = who.getBets();

        Vector<Result> allResults = businessLogic.getAllResults();



        for (Result res: allResults) {
            int finalRes = res.getFinalResult();
            for (Bet b : usersBets) {
                int usersBetResult = b.getResult().getFinalResult();
                if (usersBetResult ==finalRes){
                    tableMovements.getItems().add(res);

                }
            }
        }






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
