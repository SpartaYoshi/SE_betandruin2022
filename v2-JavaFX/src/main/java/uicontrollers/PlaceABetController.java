package uicontrollers;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.*;

import businessLogic.BlFacade;
import domain.*;
import exceptions.*;
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



    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField amountMoneyTextField;

    @FXML
    private DatePicker calendar;

    @FXML
    private TableView<Event> tblEvents;

    @FXML
    private Label minimumBetlabel;

    @FXML
    private TableView<Question> tblQuestions;

    @FXML
    private TableView<Result> tblResults;


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
    private TableColumn<Result, Float> fc1;

    @FXML
    private TableColumn<Result, String> fc2;

    @FXML
    private Label eventDescriptionLabel;

    @FXML
    private Label questionLabel;

    @FXML
    private Label resultLabel;


    private final BlFacade businessLogic;
    private MainGUI mainGUI;

    public PlaceABetController(BlFacade bl)  {

        this.businessLogic = bl;
        User u= new User("test9", "test9", "Nagore", "Bravo", new Date(), 200.0);
        businessLogic.setCurrentUser(u);
    }


        @FXML
        void jButtonClose_actionPerformed(ActionEvent event) {
            if (businessLogic.getCurrentUser().isAdmin())
                mainGUI.showAdminPortal();
            else mainGUI.showUserPortal();
        }


        @FXML
        void jButtonPlaceABet_actionPerformed(ActionEvent event) throws NotEnoughMoneyException, MinimumBetException {
            try {

                messageLabel.setText("");
                messageLabel.getStyleClass().clear();

                String stringAmount = amountMoneyTextField.getText();
                Event event1 = tblEvents.getSelectionModel().getSelectedItem();
                Question question = tblQuestions.getSelectionModel().getSelectedItem();
                Result result = tblResults.getSelectionModel().getSelectedItem();

                LocalDate localDate = calendar.getValue();
                if (localDate==null) {
                    messageLabel.getStyleClass().setAll("lbl", "lbl-danger");
                    messageLabel.setText("Error. A date must be selected.");
                }

                else {
                    Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
                    Date date = Date.from(instant);


                    if (event1 == null) {
                        messageLabel.getStyleClass().setAll("lbl", "lbl-danger");
                        messageLabel.setText("Error. No event has been selected.");
                    } else if (question == null) {
                        messageLabel.getStyleClass().setAll("lbl", "lbl-danger");
                        messageLabel.setText("Error. No question has been selected.");
                    } else if (result == null) {
                        messageLabel.getStyleClass().setAll("lbl", "lbl-danger");
                        messageLabel.setText("Error. No result has been selected.");
                    } else {

                        if (stringAmount != null) {
                            Double amount = Double.parseDouble(stringAmount);

                            Bet newBet = businessLogic.placeBet(amount, question, result, date);

                            if (newBet != null) {
                                usersMoney();
                                messageLabel.getStyleClass().setAll("lbl", "lbl-success");
                                messageLabel.setText("The bet has been succesfully added.");
                            } else {
                                messageLabel.getStyleClass().setAll("lbl", "lbl-danger");
                                messageLabel.setText("Error. The bet could not be added.");
                            }
                        } else {
                            messageLabel.getStyleClass().setAll("lbl", "lbl-danger");
                            messageLabel.setText("Error. The amount field shouldn't be empty.");
                        }
                    }
                }

                } catch(NotEnoughMoneyException e1){
                    messageLabel.getStyleClass().setAll("lbl", "lbl-danger");
                    messageLabel.setText("Not enough money for the selected amount.");

                } catch(MinimumBetException e2){
                    messageLabel.getStyleClass().setAll("lbl", "lbl-danger");
                    messageLabel.setText("A larger amount must be selected. Check the minimum amount.");

                } catch(EventFinished e){
                    messageLabel.getStyleClass().setAll("lbl", "lbl-danger");
                    messageLabel.setText("The event could not be created. Try again selecting another date");
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
                eventDescriptionLabel.setText(tblEvents.getSelectionModel().getSelectedItem().getDescription());
                questionLabel.setText("");
                resultLabel.setText("");

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
                questionLabel.setText(tblQuestions.getSelectionModel().getSelectedItem().getQuestion());
                resultLabel.setText("");

                tblResults.getItems().clear();
                for (Result r : tblQuestions.getSelectionModel().getSelectedItem().getResults()) {
                    tblResults.getItems().add(r);
                }
                Double min = businessLogic.getMoneyMinimumBet(tblQuestions.getSelectionModel().getSelectedItem());
                minimumBetlabel.getStyleClass().setAll("lbl","lbl-info");
                minimumBetlabel.setText("Minimum bet for this question: "+ min + "€");
            }
        });
    }



    private void setupResultSelection() {
        tblResults.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                resultLabel.setText(tblResults.getSelectionModel().getSelectedItem().getResult());
            }
        });


    }


    public void usersMoney(){
        if(businessLogic.getMoneyAvailable()==0){
            availableMoneyLabel.getStyleClass().setAll("lbl","lbl-danger");
        }else{
            availableMoneyLabel.getStyleClass().setAll("lbl", "lbl-success");
        }
        availableMoneyLabel.setText("Available money: "+ businessLogic.getMoneyAvailable() + "€");
    }




    @FXML
        void initialize() {
        placeBetButton.getStyleClass().setAll("btn", "btn-primary");

        usersMoney();

        setupEventSelection();
        setupQuestionSelection();
        setupResultSelection();

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

        // Bind columns to Question attributes
        qc1.setCellValueFactory(new PropertyValueFactory<>("questionNumber"));
        qc2.setCellValueFactory(new PropertyValueFactory<>("question"));

        // Bind columns to Fee (result) attributes
        fc1.setCellValueFactory(new PropertyValueFactory<>("fee"));
        fc2.setCellValueFactory(new PropertyValueFactory<>("result"));



        }


        @Override
        public void setMainApp(MainGUI mainGUI) {
            this.mainGUI = mainGUI;
        }

    }



