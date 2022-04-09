package uicontrollers;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.*;

import businessLogic.BlFacade;
import domain.Bet;
import domain.Event;
import domain.Fee;
import domain.Question;
import exceptions.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.skin.DatePickerSkin;
import javafx.util.Callback;
import ui.MainGUI;
import utils.Dates;

public class PlaceABetController implements Controller{


    private ObservableList<Event> oListEvents;
    private ObservableList<Question> oListQuestions;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField amountMoneyTextField;

    @FXML
    private DatePicker calendar;

    @FXML
    private Button closeButton;

    @FXML
    private TableView<Event> tblEvents;


    @FXML
    private TableView<Question> tblQuestions;

    @FXML
    private TableView<Fee> tblFees;


    @FXML
    private Label messageLabel;

    @FXML
    private Button placeBetButton;

    @FXML
    private Label availableMoneyLabel;

    @FXML
    private TableColumn<Event, Integer> ec1;

    @FXML
    private TableColumn<Event, String> ec2;

    @FXML
    private TableColumn<Question, Integer> qc1;

    @FXML
    private TableColumn<Question, String> qc2;


    @FXML
    private TableColumn<Fee, Float> fc1;

    @FXML
    private TableColumn<Fee, String> fc2;


    private final BlFacade businessLogic;
    private MainGUI mainGUI;

    public PlaceABetController(BlFacade bl)  {
            this.businessLogic = bl;
        }


        @FXML
        void jButtonClose_actionPerformed(ActionEvent event) {
            closeButton.setVisible(false);
        }


        @FXML
        void jButtonPlaceABet_actionPerformed(ActionEvent event) throws NotEnoughMoneyException, MinimumBetException, FailedMoneyUpdateException, EventFinished {
            //try {

            messageLabel.setText("");
            String stringAmount = amountMoneyTextField.getText();
            Question question = tblQuestions.getSelectionModel().getSelectedItem();
            Fee fee = tblFees.getSelectionModel().getSelectedItem();

            LocalDate localDate = calendar.getValue();
            Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
            Date date = Date.from(instant);

            if (stringAmount != null) {
                Double amount = Double.parseDouble(stringAmount);

                Bet newBet = null;
                newBet = businessLogic.placeBet(amount, question, fee);
                if (newBet != null) {
                    messageLabel.getStyleClass().setAll("lbl", "lbl-success");
                    messageLabel.setText("The bet has been succesfully added.");
                    //tblEvents.getItems().add(newEvent);
                    holidays.add(Dates.convertToLocalDateViaInstant(date));
                } else {
                    messageLabel.getStyleClass().setAll("lbl", "lbl-danger");
                    messageLabel.setText("Error. The bet could not be added.");
                }

            } else {

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



