package uicontrollers;

import businessLogic.BlFacade;
import configuration.ConfigXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import ui.MainGUI;

public class MyProfileController implements Controller {
    public MyProfileController(BlFacade bl) {
            this.businessLogic = bl;
    }

    private MainGUI mainGUI;
    private final BlFacade businessLogic;

    @FXML
    private Button backButton;

    @FXML
    private Button showCheckMyResults;


    @FXML
    private Label birthDateLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private Label surnameLabel;

    @FXML
    private Label usernameLabel;

    @FXML
    void onEditProfileButton() {
        mainGUI.showEditPassword();
    }


    @FXML
    void initialize() {


    }

    public void usersData(){
        ConfigXML config = ConfigXML.getInstance();
        switch(config.getLocale()) {
            case "en":
                birthDateLabel.setText("Birth Date: " + this.businessLogic.getCurrentUser().getBirthdate().toString());
                nameLabel.setText("Name: " + this.businessLogic.getCurrentUser().getName());
                surnameLabel.setText("Surname: " + this.businessLogic.getCurrentUser().getSurname());
                passwordLabel.setText("Password: " + this.businessLogic.getCurrentUser().getPasswd());
                usernameLabel.setText("Username: " + this.businessLogic.getCurrentUser().getUsername());
                break;

            case "es":
                birthDateLabel.setText("Fecha de nacimiento: " + this.businessLogic.getCurrentUser().getBirthdate().toString());
                nameLabel.setText("Nombre: " + this.businessLogic.getCurrentUser().getName());
                surnameLabel.setText("Apellido: " + this.businessLogic.getCurrentUser().getSurname());
                passwordLabel.setText("Contraseña: " + this.businessLogic.getCurrentUser().getPasswd());
                usernameLabel.setText("Nombre de usuario: " + this.businessLogic.getCurrentUser().getUsername());
                break;

            case "eus":
                birthDateLabel.setText("Jaiotze data: " + this.businessLogic.getCurrentUser().getBirthdate().toString());
                nameLabel.setText("Izena: " + this.businessLogic.getCurrentUser().getName());
                surnameLabel.setText("Abizena: " + this.businessLogic.getCurrentUser().getSurname());
                passwordLabel.setText("Pasahitza: " + this.businessLogic.getCurrentUser().getPasswd());
                usernameLabel.setText("Erabiltzaile izena: " + this.businessLogic.getCurrentUser().getUsername());
                break;

            default:
        }
    }

    @Override public void setMainApp(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }

    @FXML public void onMyResultsButton(){
        mainGUI.showCheckMyResults();
    }

    @FXML public void onShowMovementsButton(){
        mainGUI.showShowMovements();

    }

    public void clearAll() {
        birthDateLabel.setText("");
        nameLabel.setText("");
        passwordLabel.setText("");
        surnameLabel.setText("");
        usernameLabel.setText("");
    }

    public void selectBack(ActionEvent actionEvent) {
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

}
