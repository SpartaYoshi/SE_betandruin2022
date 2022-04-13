package uicontrollers;

import businessLogic.BlFacade;
import configuration.ConfigXML;
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
    void selectBack(ActionEvent event) {
        switch (businessLogic.getSessionMode()) {
            case "Anon" -> mainGUI.showPortal();
            case "User" -> mainGUI.showUserPortal();
            case "Admin" -> mainGUI.showAdminPortal();
        }
    }

    @FXML
    void selectRegister(ActionEvent event) {
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
                ConfigXML config = ConfigXML.getInstance();
                switch (config.getLocale()) {
                    case "en" -> errorMessage.setText("Register was successful. Now you can login.");
                    case "es" -> errorMessage.setText("Se ha registrado correctamente. Ya puede iniciar sesión.");
                    case "eus" -> errorMessage.setText("Behar bezala erregistratu da. Saioa hasi dezakezu.");
                }
            }
        } catch (NullPointerException e) {
            errorMessage.getStyleClass().setAll("lbl-warning");
            ConfigXML config = ConfigXML.getInstance();
            switch (config.getLocale()) {
                case "en" -> errorMessage.setText("Please, fill in all the fields.");
                case "es" -> errorMessage.setText("Por favor, rellene todos los datos.");
                case "eus" -> errorMessage.setText("datu guztiak sartu itzazu, mesedez.");
            }
        }
    }

    @Override public void setMainApp(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }
}
