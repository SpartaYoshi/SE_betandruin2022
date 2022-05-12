package uicontrollers;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;

import businessLogic.BlFacade;
import domain.*;
import javafx.fxml.FXML;
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

    //////

    @FXML
    private TableColumn<Event, String> columnEvent;

    @FXML
    private TableColumn<Question, String> columnQuestion;

    @FXML
    private TableColumn<Result, Integer> columnFinalResult;


    @FXML
    void initialize() {


        tableResults.getItems().clear();
        tableMyResults.getItems().clear();


        // Bind columns
        columnEvent.setCellValueFactory(new PropertyValueFactory<>("description"));
        columnQuestion.setCellValueFactory(new PropertyValueFactory<>("questionID"));
        columnFinalResult.setCellValueFactory(new PropertyValueFactory<>("finalResult"));

        columnMyResult.setCellValueFactory(new PropertyValueFactory<>("possibleResult"));


        User who = businessLogic.getCurrentUser();
        if (who!= null){
            Vector<Bet> usersBets = who.getBets();
            Vector<Result> allResults = businessLogic.getAllResults();
            for (Result res: allResults) {
                if (res.getFinalResult()!=0){
                    for (Bet b : usersBets) {
                        Result usersResult = b.getResult();
                        Event usersResultEvent = usersResult.getQuestion().getEvent(); // get the event
                        Event allResultsEvent = res.getQuestion().getEvent(); // get the event
                        if (usersResultEvent.equals(allResultsEvent)) { // if they are the same event:
                            tableResults.getItems().add(res); // insert the final result
                            tableMyResults.getItems().add(usersResult); // insert the result the user had selected
                        }
                    }
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
