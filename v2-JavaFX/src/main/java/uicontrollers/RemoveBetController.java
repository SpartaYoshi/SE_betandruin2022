package uicontrollers;

import businessLogic.BlFacade;
import domain.Bet;
import domain.Event;
import domain.Fee;
import domain.Question;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.skin.DatePickerSkin;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import ui.MainGUI;
import utils.Dates;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.*;



public class RemoveBetController implements Controller{

    private MainGUI mainGUI;
    private BlFacade businessLogic;

    private ObservableList<Event> oListEvents;
    private ObservableList<Question> oListQuestions;
    private ObservableList<Bet> oListBets;



    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnRemove;

    @FXML
    private DatePicker calendar;

    @FXML
    private TableColumn<Event, Integer> ec1;

    @FXML
    private TableColumn<Event, String> ec2;

    @FXML
    private TableColumn<Question, Integer> fc1;

    @FXML
    private TableColumn<Question, String> fc2;

    @FXML
    private Label lblError;

    @FXML
    private Label lblMessage;

    @FXML
    private Label listOfEventsLabel;

    @FXML
    private TableColumn<?, ?> qc1;

    @FXML
    private TableColumn<?, ?> qc2;

    @FXML
    private TableView<Event> tblEvents;

    @FXML
    private TableView<Fee> tblFees;

    @FXML
    private TableView<Question> tblQuestions;

    @FXML
    void backClick(ActionEvent event) {mainGUI.showUserPortal();}




    public RemoveBetController(BlFacade bl) {
        this.businessLogic = bl;
    }



    @FXML
    void removeClick(ActionEvent event) {

        LocalDate localDate = calendar.getValue();
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        Date date = Date.from(instant);


        Question question = tblQuestions.getSelectionModel().getSelectedItem();
        Fee fee = tblFees.getSelectionModel().getSelectedItem();

        try {
            if (fee != null) {
                //businessLogic.removeCurrentUserBet(businessLogic.getCurrentUser(), fee);
                lblMessage.getStyleClass().clear();
                lblMessage.getStyleClass().setAll("lbl", "lbl-success");
                lblMessage.setText("Question correctly created");
                lblMessage.getStyleClass().clear();
            } else {
                lblMessage.setText("You must select an event.");
            }

            //if la fecha ya ha pasado

        } catch (Exception e1) {
            lblMessage.setText("Couldn't remove bet.");
            lblMessage.getStyleClass().setAll("lbl", "lbl-danger");
        }

    }







    private List<LocalDate> holidays = new ArrayList<>();


    private void setEvents(int year, int month) {
        Date date = Dates.toDate(year,month);

        for (Date day : businessLogic.getEventsMonth(date)) {
            holidays.add(Dates.convertToLocalDateViaInstant(day));
        }
    }


    private void setEventsPrePost(int year, int month) {
        LocalDate date = LocalDate.of(year, month, 1);
        setEvents(date.getYear(), date.getMonth().getValue());
        setEvents(date.plusMonths(1).getYear(), date.plusMonths(1).getMonth().getValue());
        setEvents(date.plusMonths(-1).getYear(), date.plusMonths(-1).getMonth().getValue());
    }


    private void setupEventSelection() {
        tblEvents.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {

                tblQuestions.getItems().clear();
                for (Question q : tblEvents.getSelectionModel().getSelectedItem().getQuestions()) {
                    tblQuestions.getItems().add(q);
                }
            }
        });
    }



    private void setupQuestionSelection() {
        tblQuestions.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {

                tblFees.getItems().clear();
                for (Fee f : tblQuestions.getSelectionModel().getSelectedItem().getFees()) {
                    tblFees.getItems().add(f);
                }
            }
        });
    }



    @FXML
    void initialize() {

        setupEventSelection();
        setupQuestionSelection();

        setEventsPrePost(LocalDate.now().getYear(), LocalDate.now().getMonth().getValue());

        calendar.setOnMouseClicked(e -> {
            // get a reference to datepicker inner content
            // attach a listener to the  << and >> buttons
            // mark events for the (prev, current, next) month and year shown
            DatePickerSkin skin = (DatePickerSkin) calendar.getSkin();
            skin.getPopupContent().lookupAll(".button").forEach(node -> {
                node.setOnMouseClicked(event -> {
                    List<Node> labels = skin.getPopupContent().lookupAll(".label").stream().toList();
                    String month = ((Label) (labels.get(0))).getText();
                    String year =  ((Label) (labels.get(1))).getText();
                    YearMonth ym = Dates.getYearMonth(month + " " + year);
                    setEventsPrePost(ym.getYear(), ym.getMonthValue());
                });
            });


        });

        calendar.setDayCellFactory(new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);

                        if (!empty && item != null) {
                            if (holidays.contains(item)) {
                                this.setStyle("-fx-background-color: pink");
                            }
                        }
                    }
                };
            }
        });

        // a date has been chosen, update the combobox of Events
        calendar.setOnAction(actionEvent -> {
            tblEvents.getItems().clear();
            Vector<domain.Event> events = businessLogic.getEvents(Dates.convertToDate(calendar.getValue()));
            for (domain.Event ev : events) {
                tblEvents.getItems().add(ev);
            }
        });

        // Bind columns to Event attributes
        ec1.setCellValueFactory(new PropertyValueFactory<>("eventNumber"));
        ec2.setCellValueFactory(new PropertyValueFactory<>("description"));

        qc1.setCellValueFactory(new PropertyValueFactory<>("questionNumber"));
        qc2.setCellValueFactory(new PropertyValueFactory<>("question"));


        fc1.setCellValueFactory(new PropertyValueFactory<>("fee"));
        fc2.setCellValueFactory(new PropertyValueFactory<>("result"));


    }




    @Override
    public void setMainApp(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }
}
