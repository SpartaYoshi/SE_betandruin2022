package uicontrollers;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import businessLogic.BlFacade;
import domain.Bet;
import domain.Event;
import domain.Question;
import exceptions.EventFinished;
import exceptions.TeamPlayingException;
import exceptions.TeamRepeatedException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
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
    private ComboBox<Event> comboEvents;

    @FXML
    private ComboBox<Question> comboQuestions;

    @FXML
    private Label lblError;

    @FXML
    private Label lblMessage;

    @FXML
    private Label listOfEventsLabel;

    @FXML
    private Label messageLabel;

    @FXML
    private Button placeBetButton;

    @FXML
    private Label availableMoneyLabel;












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
        void jButtonPlaceABet_actionPerformed(ActionEvent event) {
            //try {


            messageLabel.setText("");
            String stringAmount = amountMoneyTextField.getText();

            LocalDate localDate = calendar.getValue();
            Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
            Date date = Date.from(instant);

            if (stringAmount != null) {
                Double amount = Double.parseDouble(stringAmount);
                // Bet newBet = businessLogic.placeBet();

            } else {

            }

            //} catch {

            //}
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



    @FXML
        void initialize() {


            // only show the text of the event in the combobox (without the id)
            Callback<ListView<Event>, ListCell<Event>> factory = lv -> new ListCell<>() {
                @Override
                protected void updateItem(Event item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? "" : item.getDescription());
                }
            };

            comboEvents.setCellFactory(factory);
            comboEvents.setButtonCell(factory.call(null));


            setEventsPrePost(LocalDate.now().getYear(), LocalDate.now().getMonth().getValue());


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

            // when a date is selected...
            calendar.setOnAction(actionEvent -> {
                comboEvents.getItems().clear();

                oListEvents = FXCollections.observableArrayList(new ArrayList<>());
                oListEvents.setAll(businessLogic.getEvents(Dates.convertToDate(calendar.getValue())));

                comboEvents.setItems(oListEvents);

                if (comboEvents.getItems().size() == 0)
                    comboQuestions.setDisable(true);
                else {
                    comboQuestions.setDisable(false);
                    // select first option
                    comboEvents.getSelectionModel().select(0);
                }

            });



            // when an event is selected...
            comboEvents.setOnAction(actionEvent -> {
                comboQuestions.getItems().clear();

                oListQuestions = FXCollections.observableArrayList(new ArrayList<>());
                oListQuestions.setAll(businessLogic.getQuestions(comboEvents.getValue()));

                comboQuestions.setItems(oListQuestions);

                if (comboQuestions.getItems().size() == 0)
                    placeBetButton.setDisable(true);
                else {
                    placeBetButton.setDisable(false);
                    // select first option
                    comboQuestions.getSelectionModel().select(0);
                }

            });


            availableMoneyLabel.getStyleClass().setAll("lbl","lbl-info");
            availableMoneyLabel.setText("Your actual available amount of money: " + businessLogic.getCurrentUser().getMoneyAvailable());


        }


        @Override
        public void setMainApp(MainGUI mainGUI) {
            this.mainGUI = mainGUI;
        }

    }



