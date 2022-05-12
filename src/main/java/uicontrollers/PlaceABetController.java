package uicontrollers;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.*;

import businessLogic.BlFacade;
import configuration.ConfigXML;
import dataAccess.PropertiesManager;
import domain.*;
import exceptions.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.skin.DatePickerSkin;
import javafx.util.Callback;
import ui.MainGUI;
import utils.Dates;

public class PlaceABetController implements Controller, Initializable {


    @FXML private TextField amountMoneyTextField;

    @FXML private DatePicker calendar;

    @FXML private TableView<Event> tblEvents;
    @FXML private Label minimumBetlabel;
    @FXML private TableView<Question> tblQuestions;
    @FXML private TableView<Result> tblResults;

    @FXML private Label messageLabel;
    @FXML private Button placeBetButton;
    @FXML private Label availableMoneyLabel;

    @FXML private TableColumn<Event, Integer> ec1;
    @FXML private TableColumn<Event, String> ec2;
    @FXML private TableColumn<Question, Integer> qc1;
    @FXML private TableColumn<Question, String> qc2;
    @FXML private TableColumn<Result, Float> fc1;
    @FXML private TableColumn<Result, Integer> fc2;

    @FXML private Label eventDescriptionLabel;
    @FXML private Label questionLabel;
    @FXML private Label resultLabel;

    private final BlFacade businessLogic;
    private MainGUI mainGUI;

    public PlaceABetController(BlFacade bl)  {

        this.businessLogic = bl;

    }



        @FXML
        void selectBack() {
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

        @FXML
        void selectPlaceBet(ActionEvent event) {
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
                    ConfigXML config = ConfigXML.getInstance();
                    switch (config.getLocale()) {
                        case "en" -> messageLabel.setText("Error. A date must be selected.");
                        case "es" -> messageLabel.setText("Error. Debe selccionar una fecha");
                        case "eus" -> messageLabel.setText("Errorea. Data bat aukeratu ezazu, mesedez");
                    }
                }

                else {
                    Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
                    Date date = Date.from(instant);


                    if (event1 == null) {
                        messageLabel.getStyleClass().setAll("lbl", "lbl-danger");
                        ConfigXML config = ConfigXML.getInstance();
                        switch (config.getLocale()) {
                            case "en" -> messageLabel.setText("Error. No event has been selected.");
                            case "es" -> messageLabel.setText("Error. Ningún evento ha sido seleccionado");
                            case "eus" -> messageLabel.setText("Errorea. Ez duzu gertaerarik aukeratu");
                        }

                    } else if (question == null) {
                        messageLabel.getStyleClass().setAll("lbl", "lbl-danger");
                        ConfigXML config = ConfigXML.getInstance();
                        switch (config.getLocale()) {
                            case "en" -> messageLabel.setText("Error. No question has been selected.");
                            case "es" -> messageLabel.setText("Error. Ninguna pregunta ha sido seleccionada");
                            case "eus" -> messageLabel.setText("Errorea. Ez duzu galderarik aukeratu");
                        }

                    } else if (result == null) {
                        messageLabel.getStyleClass().setAll("lbl", "lbl-danger");
                        ConfigXML config = ConfigXML.getInstance();
                        switch (config.getLocale()) {
                            case "en" -> messageLabel.setText("Error. No result has been selected.");
                            case "es" -> messageLabel.setText("Error. Ninguna respuesta ha sido seleccionada");
                            case "eus" -> messageLabel.setText("Errorea. Ez duzu erantzunik aukeratu");
                        }
                    } else {

                        if (stringAmount != null) {
                            double amount = Double.parseDouble(stringAmount);

                            Bet newBet = businessLogic.placeBet(amount, question, result, date);

                            if (businessLogic.getCurrentUser() != null)
                                this.displayUsersMoney();

                            if (newBet != null) {
                                messageLabel.getStyleClass().setAll("lbl", "lbl-success");
                                ConfigXML config = ConfigXML.getInstance();
                                switch (config.getLocale()) {
                                    case "en" -> messageLabel.setText("The bet has been succesfully added.");
                                    case "es" -> messageLabel.setText("La apuesta se ha realizado correctamente");
                                    case "eus" -> messageLabel.setText("Apustua behar bezala egin da");
                                }

                            } else {
                                messageLabel.getStyleClass().setAll("lbl", "lbl-danger");
                                ConfigXML config = ConfigXML.getInstance();
                                switch (config.getLocale()) {
                                    case "en" -> messageLabel.setText("Error. The bet could not be added.");
                                    case "es" -> messageLabel.setText("Error. No se ha podido realizar la apuesta");
                                    case "eus" -> messageLabel.setText("Errorea. Ezin izan da apustua egin");
                                }

                            }
                        } else {
                            messageLabel.getStyleClass().setAll("lbl", "lbl-danger");
                            ConfigXML config = ConfigXML.getInstance();
                            switch (config.getLocale()) {
                                case "en" -> messageLabel.setText("Error. The amount field shouldn't be empty.");
                                case "es" -> messageLabel.setText("Error. El campo de la cantidad no puede estar vacío");
                                case "eus" -> messageLabel.setText("Errorea. Kantitatearen eremua ezin da hutsik egon");
                            }

                        }
                    }
                }

                } catch(NotEnoughMoneyException e1){
                    messageLabel.getStyleClass().setAll("lbl", "lbl-danger");
                    ConfigXML config = ConfigXML.getInstance();
                    switch (config.getLocale()) {
                        case "en" -> messageLabel.setText("Not enough money for the selected amount.");
                        case "es" -> messageLabel.setText("No tiene suficiente dinero para la cantidad seleccionada");
                        case "eus" -> messageLabel.setText("Ez duzu diru kantitate nahikoa");
                    }


                } catch(MinimumBetException e2){
                    messageLabel.getStyleClass().setAll("lbl", "lbl-danger");
                    ConfigXML config = ConfigXML.getInstance();
                    switch (config.getLocale()) {
                        case "en" -> messageLabel.setText("A larger amount must be selected. Check the minimum amount.");
                        case "es" -> messageLabel.setText("Debe seleccionar una mayor cantidad. Consulte la cantidad mínima. ");
                        case "eus" -> messageLabel.setText("Diru kantitate handiago bat aukeratu ezazu. Kontsultatu ezazu kantitate mínimoa.");
                    }


                } catch(EventAlreadyFinishedException e){
                    messageLabel.getStyleClass().setAll("lbl", "lbl-danger");
                    ConfigXML config = ConfigXML.getInstance();
                    switch (config.getLocale()) {
                        case "en" -> messageLabel.setText("The bet could not be created. Try again selecting another date");
                        case "es" -> messageLabel.setText("La apuesta no se ha podido realizar. Inténtelo de nuevo con otra fecha ");
                        case "eus" -> messageLabel.setText("Apustua ezin izan da egin. Saiatu zaitez beste data batekin");
                    }

                }catch (NumberFormatException e3){
                messageLabel.getStyleClass().setAll("lbl", "lbl-danger");
                ConfigXML config = ConfigXML.getInstance();
                switch (config.getLocale()) {
                    case "en" -> messageLabel.setText("Insert a numeric value");
                    case "es" -> messageLabel.setText("Introduzca un valor numérico ");
                    case "eus" -> messageLabel.setText("Mesedez, sartu zenbaki positibo bat");
                }
            }
            catch (Exception e4) {
                messageLabel.getStyleClass().setAll("lbl", "lbl-danger");
                ConfigXML config = ConfigXML.getInstance();
                switch (config.getLocale()) {
                    case "en" -> messageLabel.setText("Error, the bet was not created");
                    case "es" -> messageLabel.setText("Error, la apuesta no se realizó");
                    case "eus" -> messageLabel.setText("Errorea, apustua ez da egin");
                }
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
                Double min = businessLogic.getMoneyMinimumBet(tblQuestions.getSelectionModel().getSelectedItem());
                minimumBetlabel.getStyleClass().setAll("lbl","lbl-info");
                ConfigXML config = ConfigXML.getInstance();
                switch (config.getLocale()) {
                    case "en" -> minimumBetlabel.setText("Minimum bet for this question: "+ min + "€");
                    case "es" -> minimumBetlabel.setText("Cantidad mínima para esta pregunta: "+ min + "€");
                    case "eus" -> minimumBetlabel.setText("Galdera honetarako kantitate minimoa: "+ min + "€");
                }

            }
        });
    }



    private void setupResultSelection() {
        tblResults.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                resultLabel.setText("" + tblResults.getSelectionModel().getSelectedItem().getPossibleResult());
            }
        });


    }


    public void displayUsersMoney() {
        if(businessLogic.getMoneyAvailable() <= 0){
            availableMoneyLabel.getStyleClass().setAll("lbl","lbl-danger");
        }else{
            availableMoneyLabel.getStyleClass().setAll("lbl", "lbl-success");
        }
        ConfigXML config = ConfigXML.getInstance();
        switch (config.getLocale()) {
            case "en" -> availableMoneyLabel.setText("Money available: " + businessLogic.getMoneyAvailable() + "€");
            case "es" -> availableMoneyLabel.setText("Dinero disponible: " + businessLogic.getMoneyAvailable() + "€");
            case "eus" -> availableMoneyLabel.setText("Diru erabilgarria: " + businessLogic.getMoneyAvailable() + "€");
        }
    }

    public void clearAll() {
        tblEvents.getItems().clear();
        tblQuestions.getItems().clear();
        tblResults.getItems().clear();
        calendar.setValue(LocalDate.now());
        availableMoneyLabel.getStyleClass().clear();
        availableMoneyLabel.setText("");
        messageLabel.getStyleClass().clear();
        messageLabel.setText("");
        amountMoneyTextField.clear();
        minimumBetlabel.getStyleClass().clear();
        minimumBetlabel.setText("");
        eventDescriptionLabel.setText("");
        questionLabel.setText("");
        resultLabel.setText("");
        availableMoneyLabel.setText("");
        placeBetButton.getStyleClass().setAll("btn", "btn-primary");
    }


    @Override @FXML
    public void initialize(URL url, ResourceBundle resources) {
        placeBetButton.getStyleClass().setAll("btn", "btn-primary");

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
            Vector<Event> events = businessLogic.getEvents(Dates.convertToDate(calendar.getValue()));
            for (Event ev : events) {
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


        @Override
        public void setMainApp(MainGUI mainGUI) {
            this.mainGUI = mainGUI;
        }

    }



