package uicontrollers;

import businessLogic.BlFacade;
import javafx.event.ActionEvent;
import ui.MainGUI;

import java.util.function.BiConsumer;

public class PublishResultsController implements Controller{
    private MainGUI mainGUI;
    private final BlFacade businessLogic;

    public PublishResultsController(BlFacade bl) {
        businessLogic = bl;
    }

    @Override
    public void setMainApp(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }

    public void publishResult(ActionEvent actionEvent) {
    }

    public void selectBack(ActionEvent actionEvent) {
    }
}
