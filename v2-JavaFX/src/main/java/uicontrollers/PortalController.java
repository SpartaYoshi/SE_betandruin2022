package uicontrollers;

import businessLogic.BlFacade;
import ui.MainGUI;

public class PortalController implements Controller{


    private MainGUI mainGUI;
    private BlFacade businessLogic;

    public PortalController(BlFacade bl) {
        this.businessLogic = bl;
    }












    @Override
    public void setMainApp(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }
}
