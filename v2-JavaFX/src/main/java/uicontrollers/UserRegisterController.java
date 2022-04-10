package uicontrollers;

import businessLogic.BlFacade;
import domain.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ui.MainGUI;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class UserRegisterController implements Controller{
    private MainGUI mainGUI;

    private BlFacade businessLogic;



    @FXML private TextField nameField;
    @FXML private TextField surnameField;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwdField;
    @FXML private DatePicker birthdatePck;
    @FXML private Label errorMessage;

    public UserRegisterController(BlFacade bl) {
        this.businessLogic = bl;
    }


    @FXML
    void backClick(ActionEvent event) {
        mainGUI.showPortal();
    }

    @FXML
    void registerClick(ActionEvent event) {
        errorMessage.setText(" "); //clear the label

        try {
            String name = nameField.getText();
            String surname = surnameField.getText();
            String username = usernameField.getText();
            String password = passwdField.getText();
            LocalDate tempDate = birthdatePck.getValue();
            System.out.println(tempDate.toString());

            if(name.length() == 0 || surname.length() == 0 || username.length() == 0 || password.length() == 0)
                throw new NullPointerException();

            Date birthdate = Date.from(tempDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

            User user = new User(username, password, name, surname, birthdate);

            String errorDet = businessLogic.registerUser(user);

            if (!errorDet.equals("")) {
                errorMessage.getStyleClass().setAll("lbl-danger");
                errorMessage.setText(errorDet);
            } else {
                errorMessage.getStyleClass().setAll("lbl-primary");
                errorMessage.setText("Register was successful. Now you can login.");
            }
        } catch (NullPointerException e) {
            errorMessage.getStyleClass().setAll("lbl-warning");
            errorMessage.setText("Please, fill in all the fields.");
        }
    }

    @Override public void setMainApp(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }
}
