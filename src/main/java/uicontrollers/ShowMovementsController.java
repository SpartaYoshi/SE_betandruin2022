package uicontrollers;

        import java.net.URL;
        import java.time.LocalDate;
        import java.util.ArrayList;
        import java.util.Date;
        import java.util.List;
        import java.util.ResourceBundle;

        import businessLogic.BlFacade;
        import domain.Bet;
        import domain.Event;
        import domain.Movement;
        import domain.Question;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.scene.control.DatePicker;
        import javafx.scene.control.Label;
        import javafx.scene.control.TableColumn;
        import javafx.scene.control.cell.PropertyValueFactory;
        import ui.MainGUI;

public class ShowMovementsController implements Controller{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

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
    void initialize() {
        //String totalMoneyString = String.valueOf(businessLogic.getCurrentUser().getMoneyAvailable());
       // totalMoney.setText(totalMoneyString);

        // Bind columns
        tableAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        tableDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        tableDescription.setCellValueFactory(new PropertyValueFactory<>("description"));


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
