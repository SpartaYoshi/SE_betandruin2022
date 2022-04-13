package uicontrollers;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

import businessLogic.BlFacade;
import configuration.ConfigXML;
import domain.Event;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;
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

public class CreateQuestionController implements Controller {

  private ObservableList<Event> oListEvents;

  public CreateQuestionController(BlFacade bl) {
    this.businessLogic = bl;
  }

  private BlFacade businessLogic;

  @FXML private DatePicker datePicker;

  @FXML private ComboBox<Event> comboEvents;
  @FXML private TextField txtQuestion;
  @FXML private TextField txtMinBet;
  @FXML private Button btnCreateQuestion;

  private MainGUI mainGUI;

  @FXML private Label lblErrorQuestion;
  @FXML private Label lblErrorMinBet;


  @FXML
  void selectBack(ActionEvent event) {
    clearErrorLabels();

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

  private void clearErrorLabels() {
    lblErrorQuestion.setText("");
    lblErrorMinBet.setText("");
    lblErrorMinBet.getStyleClass().clear();
    lblErrorQuestion.getStyleClass().clear();
  }

  @FXML
  void selectCreate(ActionEvent e) {

    clearErrorLabels();

    Event event = comboEvents.getSelectionModel().getSelectedItem();
    String inputQuestion = txtQuestion.getText();
    Float inputPrice;
    boolean showErrors = true;

    try {

      if (inputQuestion.length() > 0) {

        inputPrice = Float.valueOf(txtMinBet.getText());

        if (inputPrice <= 0) {
          lblErrorMinBet.getStyleClass().setAll("lbl","lbl-danger");
          ConfigXML config = ConfigXML.getInstance();
          switch (config.getLocale()) {
            case "en" -> lblErrorMinBet.setText("Min bet should be > 0");
            case "es" -> lblErrorMinBet.setText("La cantidad mínima debería ser > 0");
            case "eus" -> lblErrorMinBet.setText("Kantitate minimoa > 0 izan beharko litzateke");
          }
        } else {
          businessLogic.createQuestion(event, inputQuestion, inputPrice);
          lblErrorQuestion.getStyleClass().clear();
          lblErrorQuestion.getStyleClass().setAll("lbl", "lbl-success");
          ConfigXML config = ConfigXML.getInstance();
          switch (config.getLocale()) {
            case "en" -> lblErrorQuestion.setText("Question correctly created");
            case "es" -> lblErrorQuestion.setText("La pregunta ha sido creada correctamente");
            case "eus" -> lblErrorQuestion.setText("Galdera behar bezala sortu da");
          }
          lblErrorMinBet.getStyleClass().clear();
          showErrors = false;
        }
      } else {
          lblErrorQuestion.getStyleClass().setAll("lbl", "lbl-danger");
          ConfigXML config = ConfigXML.getInstance();
          switch (config.getLocale()) {
            case "en" -> lblErrorQuestion.setText("Question shouldn't be empty");
            case "es" -> lblErrorQuestion.setText("La pregunta no debería estar vacía");
            case "eus" -> lblErrorQuestion.setText("Galdera hutsik dago");
          }

      }

    } catch (NumberFormatException ex) {
        lblErrorMinBet.getStyleClass().setAll("lbl", "lbl-danger");
        ConfigXML config = ConfigXML.getInstance();
        switch (config.getLocale()) {
          case "en" -> lblErrorMinBet.setText("Introduce a number");
          case "es" -> lblErrorMinBet.setText("Introduzca un número");
          case "eus" -> lblErrorMinBet.setText("Zenbaki bat sartu ezazu");
        }

    } catch (EventFinished ex) {
      lblErrorQuestion.getStyleClass().setAll("lbl", "lbl-danger");
      ConfigXML config = ConfigXML.getInstance();
      switch (config.getLocale()) {
        case "en" -> lblErrorQuestion.setText("Event has finished");
        case "es" -> lblErrorQuestion.setText("El evento ha terminado");
        case "eus" -> lblErrorQuestion.setText("Gertaera bukatu da");
      }

    } catch (QuestionAlreadyExist ex) {
      lblErrorQuestion.getStyleClass().setAll("lbl", "lbl-danger");
      ConfigXML config = ConfigXML.getInstance();
      switch (config.getLocale()) {
        case "en" -> lblErrorQuestion.setText("Question already exists");
        case "es" -> lblErrorQuestion.setText("La pregunta ya existe");
        case "eus" -> lblErrorQuestion.setText("Galdera jadanik existitzen da");
      }

    } catch (Exception ex) {
      ex.printStackTrace();
    }

    if (lblErrorMinBet.getText().length() > 0 && showErrors) {
      lblErrorMinBet.getStyleClass().setAll("lbl", "lbl-danger");
    }
    if (lblErrorQuestion.getText().length() > 0 && showErrors) {
      lblErrorQuestion.getStyleClass().setAll("lbl", "lbl-danger");
    }

  }

  private List<LocalDate> holidays = new ArrayList<>();

  private void setEventsPrePost(int year, int month) {
    LocalDate date = LocalDate.of(year, month, 1);
    setEvents(date.getYear(), date.getMonth().getValue());
    setEvents(date.plusMonths(1).getYear(), date.plusMonths(1).getMonth().getValue());
    setEvents(date.plusMonths(-1).getYear(), date.plusMonths(-1).getMonth().getValue());
  }

  private void setEvents(int year, int month) {

    Date date = Dates.toDate(year, month);

    for (Date day : businessLogic.getEventsMonth(date)) {
      holidays.add(Dates.convertToLocalDateViaInstant(day));
    }
  }

  @FXML
  void initialize() {

    btnCreateQuestion.setDisable(true);

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


    // get a reference to datepicker inner content
    // attach a listener to the  << and >> buttons
    // mark events for the (prev, current, next) month and year shown
    datePicker.setOnMouseClicked(e -> {
      DatePickerSkin skin = (DatePickerSkin) datePicker.getSkin();
      skin.getPopupContent().lookupAll(".button").forEach(node -> {
        node.setOnMouseClicked(event -> {
          List<Node> labels = skin.getPopupContent().lookupAll(".label").stream().toList();
          String month = ((Label) (labels.get(0))).getText();
          String year = ((Label) (labels.get(1))).getText();
          YearMonth ym = Dates.getYearMonth(month + " " + year);
          setEventsPrePost(ym.getYear(), ym.getMonthValue());
        });
      });


    });

    datePicker.setDayCellFactory(new Callback<DatePicker, DateCell>() {
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
    datePicker.setOnAction(actionEvent -> {
      comboEvents.getItems().clear();

      oListEvents = FXCollections.observableArrayList(new ArrayList<>());
      oListEvents.setAll(businessLogic.getEvents(Dates.convertToDate(datePicker.getValue())));

      comboEvents.setItems(oListEvents);

      if (comboEvents.getItems().size() == 0)
        btnCreateQuestion.setDisable(true);
      else {
         btnCreateQuestion.setDisable(false);
        // select first option
        comboEvents.getSelectionModel().select(0);
      }

    });

  }

  @Override
  public void setMainApp(MainGUI mainGUI) {
    this.mainGUI = mainGUI;
  }
}
