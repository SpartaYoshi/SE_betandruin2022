package uicontrollers;

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

    private BlFacade businessLogic;

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

    @FXML
    void selectLogin(ActionEvent event) {

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
            messageLabel.setText("The credentials are incorrect");
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
