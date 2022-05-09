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
        import javafx.fxml.FXML;
        import javafx.scene.control.DatePicker;
        import javafx.scene.control.Label;
        import javafx.scene.control.TableColumn;
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

    }


    @Override
    public void setMainApp(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }
}
