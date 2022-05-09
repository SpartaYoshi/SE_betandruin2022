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


    public ShowMovementsController(BlFacade bl) {
    }


    @Override
    public void setMainApp(MainGUI mainGUI) {
    }


    @FXML
    void initialize() {

    }

}
