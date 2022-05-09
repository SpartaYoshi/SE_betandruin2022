package uicontrollers;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import businessLogic.BlFacade;
import configuration.ConfigXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import ui.MainGUI;

public class MyProfileController implements Controller{
    public MyProfileController(BlFacade bl) {
            this.businessLogic = bl;
    }

    private MainGUI mainGUI;
    private BlFacade businessLogic;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label birthDateLabel;

    @FXML
    private Button editProfileButton;

    @FXML
    private ImageView imageUser;

    @FXML
    private Label nameLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private Label surnameLabel;

    @FXML
    private Label usernameLabel;

    @FXML
    void onEditProfileButton(ActionEvent event) {
        mainGUI.showBrowseQuestions();
    }


    @FXML
    void initialize() {
        ConfigXML config = ConfigXML.getInstance();
        switch (config.getLocale()) {
            case "en" -> birthDateLabel.setText("Birth Date: " + this.businessLogic.getCurrentUser().getBirthdate().toString());
            case "es" -> birthDateLabel.setText("Fecha de nacimiento: " + this.businessLogic.getCurrentUser().getBirthdate().toString());
            case "eus" -> birthDateLabel.setText("Jaiotze data: " + this.businessLogic.getCurrentUser().getBirthdate().toString());
        }
        switch (config.getLocale()) {
            case "en" -> nameLabel.setText("Name: " +this.businessLogic.getCurrentUser().getName());
            case "es" -> nameLabel.setText("Nombre: " +this.businessLogic.getCurrentUser().getName());
            case "eus" -> nameLabel.setText("Izena: " +this.businessLogic.getCurrentUser().getName());
        }
        switch (config.getLocale()) {
            case "en" -> surnameLabel.setText("Surname: " +this.businessLogic.getCurrentUser().getSurname());
            case "es" -> surnameLabel.setText("Apellido: " +this.businessLogic.getCurrentUser().getSurname());
            case "eus" -> surnameLabel.setText("Abizena: " +this.businessLogic.getCurrentUser().getSurname());
        }
        switch (config.getLocale()) {
            case "en" -> passwordLabel.setText("Password: " +this.businessLogic.getCurrentUser().getPasswd());
            case "es" -> passwordLabel.setText("Contraseña: " +this.businessLogic.getCurrentUser().getPasswd());
            case "eus" -> passwordLabel.setText("Pasahitza: " +this.businessLogic.getCurrentUser().getPasswd());
        }
        switch (config.getLocale()) {
            case "en" -> usernameLabel.setText("Username: " +this.businessLogic.getCurrentUser().getUsername());
            case "es" -> usernameLabel.setText("Nombre de usuario: " +this.businessLogic.getCurrentUser().getUsername());
            case "eus" -> usernameLabel.setText("Erabiltzaile izena: " +this.businessLogic.getCurrentUser().getUsername());
        }


    }

    @Override public void setMainApp(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }


}
