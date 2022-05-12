package uicontrollers;

import java.net.URL;
import java.util.ResourceBundle;

import businessLogic.BlFacade;
import configuration.ConfigXML;
import domain.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ui.MainGUI;


public class EditProfileController implements Controller, Initializable {
    private MainGUI mainGUI;
    private final BlFacade businessLogic;

    public EditProfileController(BlFacade bl) {
        this.businessLogic = bl;
    }


    @FXML
    private Label birthDateLabel;

    @FXML
    private Label messageLabelUsername;

    @FXML
    private Label messageLabelPassword;


    @FXML
    private Label nameLabel;

    @FXML
    private PasswordField passwdField;

    @FXML
    private Label surnameLabel;

    @FXML
    private TextField usernameField;

    @FXML
    void onEditProfileButton() {
        User u = businessLogic.getCurrentUser();
        if(this.usernameField != null) {
            String oldUserName = u.getUsername();
            String newUserName = usernameField.getText();
            if (oldUserName.toLowerCase().trim().equals(newUserName.toLowerCase().trim())) {
                messageLabelUsername.getStyleClass().setAll("lbl", "lbl-danger");
                ConfigXML config = ConfigXML.getInstance();
                switch (config.getLocale()) {
                    case "en" -> messageLabelUsername.setText("The new username is the same as the old one");
                    case "es" -> messageLabelUsername.setText("El nuevo nombre de usuario es el mismo que el antiguo");
                    case "eus" -> messageLabelUsername.setText("Erabiltzaile-izen berria aurrekoaren berdina da");
                }

            }else{
                businessLogic.editProfileUsername(newUserName);
                messageLabelUsername.getStyleClass().setAll("lbl", "lbl-success");
                ConfigXML config = ConfigXML.getInstance();
                switch (config.getLocale()) {
                    case "en" -> messageLabelUsername.setText("Username updated");
                    case "es" -> messageLabelUsername.setText("Nombre de usuario actualizado");
                    case "eus" -> messageLabelUsername.setText("Erabiltzaile-izena eguneratuta");
                }
            }
        }

        if(this.passwdField != null) {
            String oldPassword = u.getUsername();
            String newPassword = passwdField.getText();
            if (oldPassword.toLowerCase().trim().equals(newPassword.toLowerCase().trim())) {
                messageLabelPassword.getStyleClass().setAll("lbl", "lbl-danger");
                ConfigXML config = ConfigXML.getInstance();
                switch (config.getLocale()) {
                    case "en" -> messageLabelPassword.setText("The new password is the same as the old one");
                    case "es" -> messageLabelPassword.setText("La nueva contraseña es la misma que la antigua");
                    case "eus" -> messageLabelPassword.setText("Pasahitz berria aurrekoaren berdina da");
                }

            }else{
                businessLogic.editProfilePassword(newPassword);
                messageLabelUsername.getStyleClass().setAll("lbl", "lbl-success");
                ConfigXML config = ConfigXML.getInstance();
                switch (config.getLocale()) {
                    case "en" -> messageLabelPassword.setText("The password has been successfully changed");
                    case "es" -> messageLabelPassword.setText("La contraseña se ha cambiado exitosamente");
                    case "eus" -> messageLabelPassword.setText("Pasahitza ongi aldatu da");
                }
            }
        }
    }

    @FXML
    void selectBack() {
        mainGUI.showMyProfile();
    }

    @Override @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ConfigXML config = ConfigXML.getInstance();

        if (businessLogic.getCurrentUser() == null)
            return;


        switch (config.getLocale()) {
            case "en":
                birthDateLabel.setText("Birth Date: " + this.businessLogic.getCurrentUser().getBirthdate().toString());
                nameLabel.setText("Name: " +this.businessLogic.getCurrentUser().getName());
                surnameLabel.setText("Surname: " +this.businessLogic.getCurrentUser().getSurname());
                break;

            case "es":
                birthDateLabel.setText("Fecha de nacimiento: " + this.businessLogic.getCurrentUser().getBirthdate().toString());
                nameLabel.setText("Nombre: " +this.businessLogic.getCurrentUser().getName());
                surnameLabel.setText("Apellido: " +this.businessLogic.getCurrentUser().getSurname());
                break;

            case "eus":
                birthDateLabel.setText("Jaiotze data: " + this.businessLogic.getCurrentUser().getBirthdate().toString());
                nameLabel.setText("Izena: " +this.businessLogic.getCurrentUser().getName());
                surnameLabel.setText("Abizena: " +this.businessLogic.getCurrentUser().getSurname());
                break;

            default:
        }
    }

    @Override public void setMainApp(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }


}

