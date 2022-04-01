package uicontrollers;

import businessLogic.BlFacade;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import ui.MainGUI;

public class DepositMoneyController implements Controller {
    private MainGUI mainGUI;
    private BlFacade businessLogic;
    @FXML
    private Button backBtn;

    @FXML
    private Button insertBtn;

    @FXML
    private Label instructionLbl;

    @FXML
    private Label titleLbl;

    @FXML
    private Label warningLbl;

    @FXML
    private TextField amountField;

    public DepositMoneyController(BlFacade bl){
        this.businessLogic=bl;
    }

    @Override
    public void setMainApp(MainGUI mainGUI) {
        this.mainGUI=mainGUI;
    }

    @FXML
    public void insertMoney(MouseEvent mouseEvent) {
        try {
            double amount= Double.parseDouble(amountField.getText());
            businessLogic.insertMoney(amount);
            warningLbl.getStyleClass().setAll("lbl","lbl-success");
            warningLbl.setText("Done! You have inserted "+amount+"€, which makes a total of X available");
        }catch (Exception e){
            warningLbl.getStyleClass().setAll("lbl","lbl-danger");
            warningLbl.setText("Sorry, introduce a positive amount");
        }

    }
}