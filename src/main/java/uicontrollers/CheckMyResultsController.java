package uicontrollers;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;

import businessLogic.BlFacade;
import configuration.ConfigXML;
import domain.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
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

    @FXML private Label messageLabel;

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


    public void getResults() {

        tableResults.getItems().clear();
        tableMyResults.getItems().clear();

        // Bind columns
        columnEvent.setCellValueFactory(new PropertyValueFactory<>("event"));
        columnQuestion.setCellValueFactory(new PropertyValueFactory<>("question"));
        columnFinalResult.setCellValueFactory(new PropertyValueFactory<>("finalResult"));

        columnMyResult.setCellValueFactory(new PropertyValueFactory<>("possibleResult"));


        User who = businessLogic.getCurrentUser();
        if (who != null) {
            Vector<Bet> usersBets = who.getBets();
            Vector<Result> allResults = businessLogic.getAllResults();
            for (Bet b : usersBets) {
                Result usersResult = b.getResult();
                Question currentquestion = usersResult.getQuestion();
                if (currentquestion.questionProcessed()) {
                    tableResults.getItems().add(usersResult); // insert the final result
                    tableMyResults.getItems().add(usersResult); // insert the result the user had selected

                }
            }
        }
        if (tableMyResults.getItems().isEmpty() || tableResults.getItems().isEmpty()){
            messageLabel.getStyleClass().setAll("lbl", "lbl-danger");
            ConfigXML config = ConfigXML.getInstance();
            switch (config.getLocale()) {
                case "en" -> messageLabel.setText("You have no result respective to the processed questions");
                case "es" -> messageLabel.setText("No tienes resultados respectivos a las preguntas procesadas");
                case "eus" -> messageLabel.setText("Ez duzu erantzunik galdera prozesatuentzat");
            }
        }
    }


    @FXML
    void initialize() {
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
