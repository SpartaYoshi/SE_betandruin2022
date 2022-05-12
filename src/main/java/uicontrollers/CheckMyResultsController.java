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
import javafx.scene.control.cell.PropertyValueFactory;
import ui.MainGUI;

public class CheckMyResultsController implements Controller {

    private MainGUI mainGUI;
    private final BlFacade businessLogic;

    @FXML
    private TableView<Result> tableResults;

    @FXML
    private TableView<Result> tableMyResults;


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<Result, Integer> columnMyResult;

    @FXML
    private TableColumn<Event, String> columnEvent;

    @FXML
    private TableColumn<Question, String> columnQuestion;

    @FXML
    private TableColumn<Result, Integer> columnResult;


    @FXML
    void initialize() {


        tableMovements.getItems().clear();

        // Bind columns
        columnEvent.setCellValueFactory(new PropertyValueFactory<>("description"));
        columnQuestion.setCellValueFactory(new PropertyValueFactory<>("questionID"));
        columnResult.setCellValueFactory(new PropertyValueFactory<>("finalResult"));
        columnMyResult.setCellValueFactory(new PropertyValueFactory<>("finalResult"));


        User who = businessLogic.getCurrentUser();
        Vector<Bet> usersBets = who.getBets();

        Vector<Result> allResults = businessLogic.getAllResults();



        for (Result res: allResults) {
            int finalRes = res.getFinalResult();
            for (Bet b : usersBets) {
                // if los eventos son el mismo
                tableResults.getItems().add(res); // la respuesta real
                int usersResult = b.getResult().getPossibleResult(); // la respuesta que eligió el user
                tableMyResults.getItems().add(usersResult);


                //}


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
