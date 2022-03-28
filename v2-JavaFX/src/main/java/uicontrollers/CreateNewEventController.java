package uicontrollers;
import java.net.URL;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

import businessLogic.BlFacade;
import domain.Event;
import domain.Question;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.skin.DatePickerSkin;
import javafx.util.Callback;
import ui.MainGUI;
import utils.Dates;

public class CreateNewEventController implements Controller{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private DatePicker calendar;

    @FXML
    private Button closeButton;

    @FXML
    private Button createEventButton;

    @FXML
    private TextField eventtextField;

    @FXML
    private Label listOfEventsLabel;

    @FXML
    private Label messageLabel;

    @FXML
    private Label writeEventText;


    private final BlFacade businessLogic;
    private MainGUI mainGUI;

    public CreateNewEventController(BlFacade bl) {
        this.businessLogic = bl;
    }

    @FXML
    void jButtonClose_actionPerformed(ActionEvent event) {

    }

    @FXML
    void jButtonCreateEvent_actionPerformed(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert calendar != null : "fx:id=\"calendar\" was not injected: check your FXML file 'CreateNewEvent.fxml'.";
        assert closeButton != null : "fx:id=\"closeButton\" was not injected: check your FXML file 'CreateNewEvent.fxml'.";
        assert createEventButton != null : "fx:id=\"createEventButton\" was not injected: check your FXML file 'CreateNewEvent.fxml'.";
        assert eventtextField != null : "fx:id=\"eventtextField\" was not injected: check your FXML file 'CreateNewEvent.fxml'.";
        assert listOfEventsLabel != null : "fx:id=\"listOfEventsLabel\" was not injected: check your FXML file 'CreateNewEvent.fxml'.";
        assert messageLabel != null : "fx:id=\"messageLabel\" was not injected: check your FXML file 'CreateNewEvent.fxml'.";
        assert writeEventText != null : "fx:id=\"writeEventText\" was not injected: check your FXML file 'CreateNewEvent.fxml'.";

    }

    @Override
    public void setMainApp(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }




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

        setupEventSelection();

        setEventsPrePost(LocalDate.now().getYear(), LocalDate.now().getMonth().getValue());

        datepicker.setOnMouseClicked(e -> {
            // get a reference to datepicker inner content
            // attach a listener to the  << and >> buttons
            // mark events for the (prev, current, next) month and year shown
            DatePickerSkin skin = (DatePickerSkin) datepicker.getSkin();
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

        datepicker.setDayCellFactory(new Callback<DatePicker, DateCell>() {
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
        datepicker.setOnAction(actionEvent -> {
            tblEvents.getItems().clear();
            Vector<Event> events = businessLogic.getEvents(Dates.convertToDate(datepicker.getValue()));
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



}
