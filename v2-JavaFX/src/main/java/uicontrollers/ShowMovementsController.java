package uicontrollers;

        import java.net.URL;
        import java.util.ResourceBundle;

        import businessLogic.BlFacade;
        import javafx.fxml.FXML;
        import ui.MainGUI;

public class ShowMovementsController implements Controller{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;


    private final BlFacade businessLogic;
    private MainGUI mainGUI;

    public ShowMovementsController(BlFacade bl) {
        this.businessLogic = bl;
    }


    @Override
    public void setMainApp(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }


    @FXML
    void initialize() {

    }

}
