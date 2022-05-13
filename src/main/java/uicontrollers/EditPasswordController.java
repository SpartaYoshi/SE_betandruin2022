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
import ui.MainGUI;


public class EditPasswordController implements Controller, Initializable {
    private MainGUI mainGUI;
    private final BlFacade businessLogic;

    public EditPasswordController(BlFacade bl) {
        this.businessLogic = bl;
    }


    @FXML
    private Label birthDateLabel;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label messageLabelPassword;


    @FXML
    private Label nameLabel;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label surnameLabel;


    @FXML
    void onEditProfileButton() {
        User u = businessLogic.getCurrentUser();
        if(this.passwordField.getText().length() != 0) {
            String oldPassword = u.getPasswd();
            String newPassword = passwordField.getText();
            if (oldPassword.toLowerCase().trim().equals(newPassword.toLowerCase().trim())) {
                messageLabelPassword.getStyleClass().setAll("lbl", "lbl-danger");
                ConfigXML config = ConfigXML.getInstance();
                switch (config.getLocale()) {
                    case "en" -> messageLabelPassword.setText("The new password is the same as the old one");
                    case "es" -> messageLabelPassword.setText("La nueva contraseña es la misma que la antigua");
                    case "eus" -> messageLabelPassword.setText("Pasahitz berria aurrekoaren berdina da");
                }

            }else{
                businessLogic.editProfilePassword(u, newPassword);
                messageLabelPassword.getStyleClass().setAll("lbl", "lbl-success");
                ConfigXML config = ConfigXML.getInstance();
                switch (config.getLocale()) {
                    case "en" -> messageLabelPassword.setText("The password has been successfully changed");
                    case "es" -> messageLabelPassword.setText("La contraseña se ha cambiado exitosamente");
                    case "eus" -> messageLabelPassword.setText("Pasahitza ongi aldatu da");
                }
            }
        } else{
            messageLabelPassword.getStyleClass().setAll("lbl", "lbl-danger");
            ConfigXML config = ConfigXML.getInstance();
            switch (config.getLocale()) {
                case "en" -> messageLabelPassword.setText("No password written");
                case "es" -> messageLabelPassword.setText("No ha escrito ninguna contraseña");
                case "eus" -> messageLabelPassword.setText("Ez duzu pasahitzik idatzi");
            }
        }
    }

    public void usersSomeData(){

        ConfigXML config = ConfigXML.getInstance();
        switch (config.getLocale()) {
            case "en":
                birthDateLabel.setText("Birth Date: " + this.businessLogic.getCurrentUser().getBirthdate().toString());
                nameLabel.setText("Name: " +this.businessLogic.getCurrentUser().getName());
                surnameLabel.setText("Surname: " +this.businessLogic.getCurrentUser().getSurname());
                usernameLabel.setText("Username: " + this.businessLogic.getCurrentUser().getUsername());
                break;

            case "es":
                birthDateLabel.setText("Fecha de nacimiento: " + this.businessLogic.getCurrentUser().getBirthdate().toString());
                nameLabel.setText("Nombre: " +this.businessLogic.getCurrentUser().getName());
                surnameLabel.setText("Apellido: " +this.businessLogic.getCurrentUser().getSurname());
                usernameLabel.setText("Nombre de usuario: " + this.businessLogic.getCurrentUser().getUsername());

                break;

            case "eus":
                birthDateLabel.setText("Jaiotze data: " + this.businessLogic.getCurrentUser().getBirthdate().toString());
                nameLabel.setText("Izena: " +this.businessLogic.getCurrentUser().getName());
                surnameLabel.setText("Abizena: " +this.businessLogic.getCurrentUser().getSurname());
                usernameLabel.setText("Erabiltzaile-izena: " + this.businessLogic.getCurrentUser().getUsername());

                break;

            default:
        }
    }


    @FXML
    void selectBack() {
        mainGUI.showMyProfile();
    }

    @Override @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override public void setMainApp(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }


}

