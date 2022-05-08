package uicontrollers;

import businessLogic.BlFacade;
import configuration.ConfigXML;
import domain.Event;
import domain.Question;
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

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

public class RemoveEventController implements  Controller{

    MainGUI mainGUI;
    BlFacade businessLogic;

    @FXML
    private DatePicker calendar;

    @FXML
    private TableColumn<Event, String> colDate;

    @FXML
    private TableColumn<Event, String> colEvents;

    @FXML
    private Label messageLbl;

    @FXML
    private Button removeBtn;

    @FXML
    private TableView<Event> tblEvents;

    public RemoveEventController(BlFacade bl) {
        this.businessLogic = bl;
    }

    @FXML
    void initialize() {
        removeBtn.setDisable(true);
        setEventsPrePost(LocalDate.now().getYear(), LocalDate.now().getMonth().getValue());

        calendar.setOnMouseClicked(e -> {

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
            Vector<Event> events = businessLogic.getEvents(Dates.convertToDate(calendar.getValue()));
            for (domain.Event ev : events) {
                tblEvents.getItems().add(ev);
            }
        });

        // Bind columns to Event attributes
        colDate.setCellValueFactory(new PropertyValueFactory<>("strDate"));
        colEvents.setCellValueFactory(new PropertyValueFactory<>("description"));
        setupEventSelection();




    }

    public void removeEv(MouseEvent mouseEvent) {
        ConfigXML config = ConfigXML.getInstance();
        Event newSelection=tblEvents.getSelectionModel().getSelectedItem();
        Event deleted=businessLogic.removeEvent(newSelection);
        if(deleted.getEventNumber()==newSelection.getEventNumber()){//we have removed the correct one
            tblEvents.getItems().remove(newSelection);
            messageLbl.getStyleClass().setAll("lbl", "lbl-success");

            switch (config.getLocale()) {
                case "en" -> messageLbl.setText("Event successfully removed");
                case "es" -> messageLbl.setText("Evento correctamente eliminado");
                case "eus" -> messageLbl.setText("Gertaera ondo ezabatuta");
            }


        }else{
            messageLbl.getStyleClass().setAll("lbl", "lbl-danger");
            switch (config.getLocale()) {
                case "en" -> messageLbl.setText("Error. Event couldn't be removed!");
                case "es" -> messageLbl.setText("Error. El evento no pudo ser eliminado");
                case "eus" -> messageLbl.setText("Errorea. Gertaera ezin da ezabatuta izan");
            }
        }

    }


    private void setupEventSelection() {
        tblEvents.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                removeBtn.setDisable(false);
            }
        });
    }

    @Override
    public void setMainApp(MainGUI mainGUI) {
        this.mainGUI=mainGUI;
    }
    private List<LocalDate> holidays = new ArrayList<>();
    private void setEventsPrePost(int year, int month) {
        LocalDate date = LocalDate.of(year, month, 1);
        setEvents(date.getYear(), date.getMonth().getValue());
        setEvents(date.plusMonths(1).getYear(), date.plusMonths(1).getMonth().getValue());
        setEvents(date.plusMonths(-1).getYear(), date.plusMonths(-1).getMonth().getValue());
    }

    private void setEvents(int year, int month) {
        Date date = Dates.toDate(year,month);

        for (Date day : businessLogic.getEventsMonth(date)) {
            holidays.add(Dates.convertToLocalDateViaInstant(day));
        }
    }
    @FXML
    void selectBack(ActionEvent event) {
        clearAll();
        switch(businessLogic.getSessionMode()) {
            case "Anon":
                mainGUI.showPortal();
                break;
            case "User":
                mainGUI.showUserPortal();
                break;
            case "Admin":
                mainGUI.showAdminPortal();
                break;
            default:
                break;
        }
    }

    public void clearAll(){
        tblEvents.getItems().clear();
        calendar.setValue(LocalDate.now());
        messageLbl.setText("");
        messageLbl.getStyleClass().setAll("lbl");
    }



}
