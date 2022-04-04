package uicontrollers;

import java.net.URL;
import java.util.ResourceBundle;

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
    private MainAdminController initWindow1;
    private BrowseQuestionsController initWindow2;



    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;



    @FXML
    private Label messageLabel;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private TextField usernameTextField;

    public UserLoginController(BlFacade bl) {
        this.businessLogic = bl;
    }


    @FXML
    void closeClick(ActionEvent event) {
        mainGUI.showPortal();
    }

    @FXML
    void loginClick(ActionEvent event) {

        String username = usernameTextField.getText();
        String password = passwordTextField.getText();


        try {
            User user = businessLogic.loginUser(username, password);
            businessLogic.setUser(user);
            if (user.isAdmin()) {
                mainGUI.showMainAdmin();
            }
            else {
                messageLabel.setText("Login was successful!");
                messageLabel.getStyleClass().setAll("lbl","lbl-success");
                mainGUI.showBrowseQuestions();

            }

        } catch (FailedLoginException e) {
            messageLabel.setText("The credentials are incorrect");
            messageLabel.getStyleClass().setAll("lbl", "lbl-danger");
        }
    }

    @FXML
    void initialize() {



    }







    @Override
    public void setMainApp(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }
}
