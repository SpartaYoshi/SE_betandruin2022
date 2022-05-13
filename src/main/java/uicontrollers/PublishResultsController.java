package uicontrollers;

import businessLogic.BlFacade;
import configuration.ConfigXML;
import dataAccess.PropertiesManager;
import domain.*;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.skin.DatePickerSkin;
import javafx.util.Callback;
import ui.MainGUI;
import utils.Dates;


import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

public class PublishResultsController implements Controller{


    private  ConfigXML config = ConfigXML.getInstance();
    @FXML
    private DatePicker calendar;

    @FXML
    private Label instructionLbl;


    @FXML
    private TableColumn<Event, Integer> ec1;

    @FXML
    private TableColumn<Event, String> ec2;

    @FXML
    private Label eventDescriptionLabel;

    @FXML
    private TableColumn<Question, Integer> qc1;

    @FXML
    private TableColumn<Question, String> qc2;

    @FXML
    private Label listOfEventsLabel;

    @FXML
    private Label messageLabel;



    @FXML
    private Button publishButton;

    @FXML
    private TableColumn<Result, Float> fc1;

    @FXML
    private TableColumn<Result, Integer> fc2;

    @FXML
    private Label questionLabel;

    @FXML
    private Label resultLabel;

    @FXML
    private TableView<Event> tblEvents;

    @FXML
    private TableView<Question> tblQuestions;

    @FXML
    private TableView<Result> tblResults;

    private MainGUI mainGUI;
    private final BlFacade businessLogic;

    public PublishResultsController(BlFacade bl) {

        businessLogic = bl;
    }

    @Override
    public void setMainApp(MainGUI mainGUI) {

        this.mainGUI = mainGUI;
    }


    @FXML
    public void publishResult() {
        Result ourRes = tblResults.getSelectionModel().getSelectedItem();
        int howManyChanges = 0;
        if (ourRes != null) {
            int updatedRes = businessLogic.markAsFinalResult(ourRes);
            messageLabel.setText(String.valueOf(updatedRes));

           howManyChanges= businessLogic.processBets(ourRes);

        } else {// it is null
            messageLabel.getStyleClass().setAll("lbl", "lbl-danger");
            switch (config.getLocale()) {
                case "en" -> messageLabel.setText("Error. Please select a Result");
                case "es" -> messageLabel.setText("Error. Por favor, seleccione un resultado.");
                case "eus" -> messageLabel.setText("Errorea. Mesedez, sakatu emaitza bat");
            }
        }

        tblResults.getItems().remove(ourRes);
        messageLabel.getStyleClass().setAll("lbl", "lbl-success");
        switch (config.getLocale()) {
            case "en" -> messageLabel.setText(howManyChanges + " payments were made successfully");
            case "es" -> messageLabel.setText(howManyChanges + " pagos fueron realizados correctamente");
            case "eus" -> messageLabel.setText(howManyChanges + "ordainketa ondo egin dira");

        }

    }







    private final List<LocalDate> holidays = new ArrayList<>();


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
                eventDescriptionLabel.setText(tblEvents.getSelectionModel().getSelectedItem().getTeamTemplate());
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
                questionLabel.setText(tblQuestions.getSelectionModel().getSelectedItem().getQuestionID());
                resultLabel.setText("");

                tblResults.getItems().clear();
                for (Result r : tblQuestions.getSelectionModel().getSelectedItem().getResults()) {
                    tblResults.getItems().add(r);
                }


            }
        });
    }



    private void setupResultSelection() {
        tblResults.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                resultLabel.setText("" + tblResults.getSelectionModel().getSelectedItem().getPossibleResult());
                publishButton.setDisable(false);
            }
        });


    }



    public void clearAll() {
        tblEvents.getItems().clear();
        tblQuestions.getItems().clear();
        tblResults.getItems().clear();
        calendar.setValue(LocalDate.now());
        messageLabel.getStyleClass().clear();
        messageLabel.setText("");
        eventDescriptionLabel.setText("");
        questionLabel.setText("");
        resultLabel.setText("");
    }


     @FXML
    public void initialize() {
        publishButton.setDisable(true);

         switch (config.getLocale()) {
             case "en" -> instructionLbl.setText("Select a question and a result, or load all the results from the API automatically");
             case "es" -> instructionLbl.setText("Seleccione una pregunta y respuesta, o cargue todos los resultados de la API automáticamente.");
             case "eus" -> instructionLbl.setText("Aukeratu galdera eta emaitza bat, edo kargatu APIak emandako emaitza guztiak automatikoki.");
         }

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
         qc2.setCellValueFactory( features -> {
             String questionID = features.getValue().getQuestionID();
             PropertiesManager propMgr = new PropertiesManager();
             return new SimpleStringProperty(propMgr.getTag(questionID));
         });


        // Bind columns to Fee (result) attributes
        fc1.setCellValueFactory(new PropertyValueFactory<>("fee"));
        fc2.setCellValueFactory(new PropertyValueFactory<>("possibleResult"));



    }


    public void selectBack(ActionEvent actionEvent) {
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

    public void updateFromAPI(ActionEvent actionEvent) {
        try {
            int howManyChanges=businessLogic.updateResultsFromAPI();
            messageLabel.getStyleClass().setAll("lbl", "lbl-success");
            switch (config.getLocale()) {
                case "en" -> messageLabel.setText(howManyChanges + " payments were made successfully");
                case "es" -> messageLabel.setText(howManyChanges + " pagos fueron realizados correctamente");
                case "eus" -> messageLabel.setText(howManyChanges + "ordainketa ondo egin dira");
            }
        }catch(Exception e1){
                messageLabel.getStyleClass().setAll("lbl", "lbl-success");
                switch (config.getLocale()) {
                    case "en" -> messageLabel.setText("Error when fetching the API");
                    case "es" -> messageLabel.setText("Error al cargar la API");
                    case "eus" -> messageLabel.setText("Errorea APIa atzitzerakoan");
                }
            }

        }

    }

