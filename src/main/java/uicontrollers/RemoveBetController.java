package uicontrollers;

import businessLogic.BlFacade;
import domain.Bet;
import domain.Event;
import domain.Result;
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

import java.time.Instant;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.*;



public class RemoveBetController implements Controller{

    private MainGUI mainGUI;
    private final BlFacade businessLogic;



    @FXML
    private DatePicker calendar;

    @FXML
    private TableView<Event> tblEvents;


    @FXML
    private TableView<Question> tblQuestions;

    @FXML
    private TableView<Bet> tblBets;


    @FXML
    private Label lblMessage;

    @FXML
    private Button removeBtn;


    @FXML
    private TableColumn<Event, Integer> ec1;

    @FXML
    private TableColumn<Event, String> ec2;

    @FXML
    private TableColumn<Question, Integer> qc1;

    @FXML
    private TableColumn<Question, String> qc2;

    @FXML
    private TableColumn<Bet, Float> betc1;

    @FXML
    private TableColumn<Bet, String> betc2;



    @FXML
    void backClick(ActionEvent event) {
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




    public RemoveBetController(BlFacade bl) {
        this.businessLogic = bl;
    }



    @FXML
    void removeClick(ActionEvent event) {

        LocalDate localDate = calendar.getValue();
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        Date date = Date.from(instant);


        Event event1 = tblEvents.getSelectionModel().getSelectedItem();
        Question question = tblQuestions.getSelectionModel().getSelectedItem();
        Bet bet = tblBets.getSelectionModel().getSelectedItem();

        try {
            if(date == null){
                lblMessage.setText("You must select a date.");
                lblMessage.getStyleClass().setAll("lbl", "lbl-danger");
            }
            if(event1 == null){
                lblMessage.setText("You must select an event.");
                lblMessage.getStyleClass().setAll("lbl", "lbl-danger");
            }
            if(question == null){
                lblMessage.setText("You must select a question.");
                lblMessage.getStyleClass().setAll("lbl", "lbl-danger");
            }
            if(bet == null){
                lblMessage.setText("You must select a bet.");
                lblMessage.getStyleClass().setAll("lbl", "lbl-danger");
            }
            else{
                Bet b1 = businessLogic.removeCurrentUserBet(businessLogic.getCurrentUser(), question, bet);
                if(b1!=null){
                    businessLogic.insertMoney(bet.getAmount());
                    lblMessage.getStyleClass().clear();
                    lblMessage.getStyleClass().setAll("lbl", "lbl-success");
                    lblMessage.setText("Bet removed, the money has been updated into your account");
                    tblBets.getItems().remove(bet);
                }
                else {
                    lblMessage.setText("You must select the bet you want to remove.");
                    lblMessage.getStyleClass().setAll("lbl", "lbl-danger");
                }
            }

        } catch (Exception e1) {
            lblMessage.setText("Couldn't remove bet.");
            lblMessage.getStyleClass().setAll("lbl", "lbl-danger");
        }
        tblEvents.getItems().clear();
        tblQuestions.getItems().clear();
        tblBets.getItems().clear();
        calendar.getEditor().clear();
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

                tblQuestions.getItems().clear();
                tblBets.getItems().clear();
                for (Question q : tblEvents.getSelectionModel().getSelectedItem().getQuestions()) {
                    tblQuestions.getItems().add(q);
                }
            }
        });
    }



    private void setupQuestionSelection() {
        tblQuestions.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                tblBets.getItems().clear();
                Question selectedQuestion = tblQuestions.getSelectionModel().getSelectedItem();
                Vector<Result> selectedResults = (Vector<Result>) selectedQuestion.getResults();


                for (Result r: selectedResults){
                    for (Bet b : r.getBets()) {
                        for(Bet userbet:businessLogic.getCurrentUser().getBets()){
                            if(userbet.getBetNum().equals(b.getBetNum())){
                                tblBets.getItems().add(b);
                            }
                        }

                    }
                }

            }
        });
    }


    public void clearAll(){
        tblEvents.getItems().clear();
        tblQuestions.getItems().clear();
        tblBets.getItems().clear();
        calendar.setValue(LocalDate.now());
        lblMessage.getStyleClass().clear();
        lblMessage.setText("");
        removeBtn.getStyleClass().setAll("btn", "btn-primary");
    }



    @FXML
    void initialize() {
        removeBtn.getStyleClass().setAll("btn", "btn-primary");

        setupEventSelection();
        setupQuestionSelection();



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


        betc1.setCellValueFactory(new PropertyValueFactory<>("amount"));
        betc2.setCellValueFactory(new PropertyValueFactory<>("result"));


    }




    @Override
    public void setMainApp(MainGUI mainGUI) {
        tblEvents.getItems().clear();
        tblQuestions.getItems().clear();
        tblBets.getItems().clear();
        calendar.getEditor().clear();

        this.mainGUI = mainGUI;
    }
}
