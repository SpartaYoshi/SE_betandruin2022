package uicontrollers;

        import java.net.URL;
        import java.time.LocalDate;
        import java.util.ArrayList;
        import java.util.Date;
        import java.util.List;
        import java.util.ResourceBundle;

        import businessLogic.BlFacade;
        import configuration.ConfigXML;
        import dataAccess.PropertiesManager;
        import domain.Bet;
        import domain.Event;
        import domain.Movement;
        import domain.Question;
        import javafx.beans.property.SimpleStringProperty;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.scene.control.DatePicker;
        import javafx.scene.control.Label;
        import javafx.scene.control.TableColumn;
        import javafx.scene.control.TableView;
        import javafx.scene.control.cell.PropertyValueFactory;
        import org.intellij.lang.annotations.JdkConstants;
        import ui.MainGUI;

public class ShowMovementsController implements Controller{

    @FXML
    private TableView<Movement> tableMovements;
    @FXML
    private TableColumn<Movement, Double> tableAmount;

    @FXML
    private TableColumn<Movement, Date> tableDate;

    @FXML
    private TableColumn<Movement, String> tableDescription;

    @FXML
    private Label totalMoney;


    private MainGUI mainGUI;
    private final BlFacade businessLogic;


    public ShowMovementsController(BlFacade bl)  {
        businessLogic = bl;
    }

    @FXML
    public void initialize() {

        displayUsersMoney();

        tableMovements.getItems().clear();

        // Bind columns
        tableAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        tableDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        tableDescription.setCellValueFactory(features -> {
            String descriptionType = features.getValue().getDescriptionType();
            PropertiesManager propMgr = new PropertiesManager();
            return new SimpleStringProperty(propMgr.getTag(descriptionType));
        });


        if(businessLogic.getCurrentUser() != null) {
            for (Movement mov:businessLogic.getCurrentUser().getMovements())
                tableMovements.getItems().add(mov);
        }


        tableMovements.getSortOrder().add(tableDate);


    }



    public void displayUsersMoney() {
        if(businessLogic.getMoneyAvailable() <= 0){
            totalMoney.getStyleClass().setAll("lbl","lbl-danger");
        }else{
            totalMoney.getStyleClass().setAll("lbl", "lbl-success");
        }
        ConfigXML config = ConfigXML.getInstance();
        switch (config.getLocale()) {
            case "en" -> totalMoney.setText("Money available: " + businessLogic.getMoneyAvailable() + "€");
            case "es" -> totalMoney.setText("Dinero disponible: " + businessLogic.getMoneyAvailable() + "€");
            case "eus" -> totalMoney.setText("Diru erabilgarria: " + businessLogic.getMoneyAvailable() + "€");
        }
    }


    @FXML
    void selectBack(ActionEvent event) {
        //clearAll();


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


    @Override
    public void setMainApp(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }
}
