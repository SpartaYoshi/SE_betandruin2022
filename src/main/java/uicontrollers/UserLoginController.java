package uicontrollers;

import configuration.ConfigXML;
import domain.User;
import exceptions.FailedLoginException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import businessLogic.BlFacade;
import ui.MainGUI;

public class UserLoginController implements Controller{
    private MainGUI mainGUI;

    private final BlFacade businessLogic;

    @FXML private Label messageLabel;
    @FXML private PasswordField passwordTextField;
    @FXML private TextField usernameTextField;
    @FXML private Button closeButton;
    @FXML private Button loginButton;

    public UserLoginController(BlFacade bl) {
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
    void selectLogin(){

        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        try {
            User user = businessLogic.loginUser(username, password);
            businessLogic.setCurrentUser(user);
            if (user.isAdmin()) {
                //messageLabel.setText("Login was successful!");
                //messageLabel.getStyleClass().setAll("lbl","lbl-success");
                businessLogic.setSessionMode("Admin");
                mainGUI.showAdminPortal();
            }
            else {
                //messageLabel.setText("Login was successful!");
                //messageLabel.getStyleClass().setAll("lbl","lbl-success");
                businessLogic.setSessionMode("User");
                mainGUI.showUserPortal();
            }

        } catch (FailedLoginException e) {
            ConfigXML config = ConfigXML.getInstance();
            switch (config.getLocale()) {
                case "en" -> messageLabel.setText("The credentials are incorrect.");
                case "es" -> messageLabel.setText("Los datos introducidos son incorrectos.");
                case "eus" -> messageLabel.setText("Sartutako datuak ez dira zuzenak.");
            }
            messageLabel.getStyleClass().setAll("lbl", "lbl-danger");
        }
    }

    @FXML
    void initialize() {
        closeButton.setDisable(false);
        loginButton.setDisable(false);
        messageLabel.setVisible(true);
    }


    @Override
    public void setMainApp(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }
}
