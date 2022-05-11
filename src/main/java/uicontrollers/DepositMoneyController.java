package uicontrollers;

import businessLogic.BlFacade;
import configuration.ConfigXML;
import exceptions.FailedMoneyUpdateException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import ui.MainGUI;


public class DepositMoneyController implements Controller {
    private MainGUI mainGUI;
    private final BlFacade businessLogic;

    @FXML private Label warningLbl;
    @FXML private TextField amountField;
    @FXML private Button depositBtn;

    public DepositMoneyController(BlFacade bl){
        this.businessLogic=bl;
           }

    @Override
    public void setMainApp(MainGUI mainGUI) {
        this.mainGUI=mainGUI;
    }

    public void selectBack(MouseEvent mouseEvent) {

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

    /**
     * method that deposits money into users account when clicking
     * @param mouseEvent
     */
    @FXML
    public void insertMoney(MouseEvent mouseEvent) {

        try {
            depositBtn.getStyleClass().setAll("btn", "btn-primary");
            double amount= Double.parseDouble(amountField.getText());
            double totalMoneyAv=businessLogic.insertMoney(amount, null);

            warningLbl.getStyleClass().setAll("lbl","lbl-success");
            ConfigXML config = ConfigXML.getInstance();
            switch (config.getLocale()) {
                case "en" -> warningLbl.setText("Done! You have inserted "+amount+"€. Total available money: "+ totalMoneyAv+" €");
                case "es" -> warningLbl.setText("Hecho! Ha insertado "+amount+"€. Cantitad disponible de dinero: "+ totalMoneyAv+" €");
                case "eus" -> warningLbl.setText("Egina! "+amount+"€ sartu dituzu. Diru kantitate erabilgarri totala: "+ totalMoneyAv+" €");
            }


        }catch (FailedMoneyUpdateException e){
            warningLbl.getStyleClass().setAll("lbl","lbl-danger");
            ConfigXML config = ConfigXML.getInstance();
            switch (config.getLocale()) {
                case "en" -> warningLbl.setText(e.getMessage());
                case "es" -> warningLbl.setText(e.getMessage());
                case "eus" -> warningLbl.setText(e.getMessage());
            }

        }catch (Exception e1){
            warningLbl.getStyleClass().setAll("lbl","lbl-danger");
            ConfigXML config = ConfigXML.getInstance();
            switch (config.getLocale()) {
                case "en" -> warningLbl.setText("Sorry, introduce a positive amount");
                case "es" -> warningLbl.setText("Perdone, introduzca un valor positivo");
                case "eus" -> warningLbl.setText("Barkatu, balio positibo bat sartu ezazu");
            }
        }
        amountField.setText("");

    }

    public void clearAll(){
        warningLbl.setText("");
        warningLbl.getStyleClass().clear();
        depositBtn.getStyleClass().setAll("btn", "btn-primary");

    }


}