
    package uicontrollers;

import businessLogic.BlFacade;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import ui.MainGUI;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

    public class PlaceABetController implements Controller{

        @FXML
        private ResourceBundle resources;

        @FXML
        private URL location;

        @FXML
        private Slider amountMoneySlider;

        @FXML
        private TextField amountMoneyTextField;

        @FXML
        private Label availableMoneyLabel;

        @FXML
        private Button closeButton;

        @FXML
        private Label messageLabel;

        @FXML
        private Button placeBetButton;

        private final BlFacade businessLogic;
        private MainGUI mainGUI;

        public PlaceABetController(BlFacade bl) {
            this.businessLogic = bl;
        }


        @FXML
        void jButtonClose_actionPerformed(ActionEvent event) {
            closeButton.setVisible(false);
        }

        @FXML
        void jButtonCreateEvent_actionPerformed(ActionEvent event) {
            //try {

                messageLabel.setText("");
                String stringAmount = amountMoneyTextField.getText();
                if (stringAmount!= null){
                    Integer amount = Integer.parseInt(stringAmount);

                }else{
                    amountMoneySlider.valueProperty().addListener(new ChangeListener<Number>() {
                        @Override
                        public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                            amountMoneyTextField.setText(newValue.toString());
                            // quitarle dinero al user

                            if (amountMoneyTextField.getText() == null){
                                messageLabel.getStyleClass().setAll("lbl","lbl-danger");
                                messageLabel.setText("Error. The event could not be placed.");
                            }
                            else{
                                //Bet newBet = businessLogic.placeBet();

                                //if (newBet!=null){
                                    messageLabel.getStyleClass().setAll("lbl","lbl-success");
                                    messageLabel.setText("The bet has been succesfully placed.");
                                //} else { }
                            }
                        }
                    });
                }

            //} catch {

            //}
        }


        @FXML
        void initialize() {
            availableMoneyLabel.getStyleClass().setAll("lbl","lbl-info");
            availableMoneyLabel.setText("Your actual available amount of money: " + businessLogic.getUser().getMoneyAvailable());
            amountMoneySlider.setMin(0);
            amountMoneySlider.setMax(businessLogic.getUser().getMoneyAvailable());
            amountMoneySlider.setValue(0);
            amountMoneySlider.setBlockIncrement(1);

        }


        @Override
        public void setMainApp(MainGUI mainGUI) {
            this.mainGUI = mainGUI;
        }

    }



