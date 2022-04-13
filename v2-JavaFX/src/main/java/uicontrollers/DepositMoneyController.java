package uicontrollers;

import businessLogic.BlFacade;
import domain.User;
import exceptions.FailedMoneyUpdateException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import ui.MainGUI;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DepositMoneyController implements Controller {
    private MainGUI mainGUI;
    private BlFacade businessLogic;

    @FXML private Label warningLbl;
    @FXML private TextField amountField;

    public DepositMoneyController(BlFacade bl){
        this.businessLogic=bl;
        //needs to be removed when login and register are done from here
        String sDate1="01/01/1980";
        Date date1= null;
        try {
            date1 = new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        User u= new User("prueba", "123", "Iosu", "Abal", date1);
        businessLogic.setCurrentUser(u);
        //to here


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
        amountField.setText("");

    }

    public void selectBack(MouseEvent mouseEvent) {
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
}