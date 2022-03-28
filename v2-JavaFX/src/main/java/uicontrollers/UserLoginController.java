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
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.skin.DatePickerSkin;
import ui.MainGUI;
import utils.Dates;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class UserLoginController implements Controller{
    private MainGUI mainGUI;

    private BlFacade businessLogic;
    private MainGUIController initWindow1;
    private BrowseQuestionsController initWindow2;



    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnClose;

    @FXML
    private Button btnLogin;

    @FXML
    private Label messageLabel;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField textField;

    public UserLoginController(BlFacade bl) {
        this.businessLogic = bl;
    }


    @FXML
    void closeClick(ActionEvent event) {
        mainGUI.showMain();
    }

    @FXML
    void loginClick(ActionEvent event) {
        String username = textField.getText();
        String password = new String (passwordField.getText());

        try {
            User user = businessLogic.loginUser(username, password);

            if (user.isAdmin()) {
                initWindow1 = new MainGUIController();
                initWindow1.showMain();
            }
            else {
                messageLabel.setText("Login was succesful!");
                messageLabel.getStyleClass().setAll("lbl","lbl-success");
                initWindow2 = new BrowseQuestionsController(businessLogic);
                initWindow2.setVisible(true);

            }

        } catch (FailedLoginException e) {
            messageLabel.setText("Try again :)");
            messageLabel.getStyleClass().setAll("alert", "alert-danger");
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
