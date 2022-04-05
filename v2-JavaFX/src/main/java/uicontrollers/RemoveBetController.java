package uicontrollers;

import businessLogic.BlFacade;
import domain.Event;
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
    private ComboBox<Event> comboEvents;

    @FXML
    private ComboBox<Question> comboQuestions;

    @FXML
    private Label lblError;

    @FXML
    private Label lblMessage;

    @FXML
    private Label listOfEventsLabel;



    public RemoveBetController(BlFacade bl) {
        this.businessLogic = bl;
    }



    @FXML
    void removeClick(ActionEvent event) {

        LocalDate localDate = calendar.getValue();
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        Date date = Date.from(instant);


        Event event1 = comboEvents.getSelectionModel().getSelectedItem();

        try {
            if (event1 != null) {
                //businessLogic.remove(event);
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




    @FXML
    public void closeClick(MouseEvent mouseEvent) {mainGUI.showPortal();}







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

            if (comboQuestions.getItems().size() == 0){
                btnRemove.setDisable(true);
            }
            else {
                btnRemove.setDisable(false);
                // select first option
                comboQuestions.getSelectionModel().select(0);
            }

        });

    }




    @Override
    public void setMainApp(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }
}
