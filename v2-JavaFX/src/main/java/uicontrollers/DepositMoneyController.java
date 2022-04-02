package uicontrollers;

import businessLogic.BlFacade;
import domain.User;
import exceptions.FailedMoneyUpdateException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import ui.MainGUI;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


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
        String sDate1="01/01/1980";
        Date date1= null;
        try {
            date1 = new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        User u= new User("prueba", "123", "Iosu", "Abal", date1);
        businessLogic.setUser(u);
    }

    @Override
    public void setMainApp(MainGUI mainGUI) {
        this.mainGUI=mainGUI;
    }

    @FXML
    public void insertMoney(MouseEvent mouseEvent) {

        try {
            double amount= Double.parseDouble(amountField.getText());
            double totalMoneyAv=businessLogic.insertMoney(amount);

            warningLbl.getStyleClass().setAll("lbl","lbl-success");

            warningLbl.setText("Done! You have inserted "+amount+"€. Total available money: "+ totalMoneyAv+" €");

        }catch (FailedMoneyUpdateException e){
            warningLbl.getStyleClass().setAll("lbl","lbl-danger");
            warningLbl.setText(e.getMessage());

        }catch (Exception e1){
            warningLbl.getStyleClass().setAll("lbl","lbl-danger");
            warningLbl.setText("Sorry, introduce a positive amount");
        }

    }

    public void closeClick(MouseEvent mouseEvent) {mainGUI.showPortal();    }
}