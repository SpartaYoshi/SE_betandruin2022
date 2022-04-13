package ui;

import businessLogic.BlFacade;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;
import uicontrollers.*;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainGUI {

  private Window portal, adminPortal, userPortal, createQuestion, browseQuestions, setFee, removeBet, userLogin, userRegister, createEvent, placeAbet, depositMoney;

  private BlFacade businessLogic;
  private Stage stage;
  private Scene scene;

  public BlFacade getBusinessLogic() {
    return businessLogic;
  }

  public void setBusinessLogic(BlFacade afi) {
    businessLogic = afi;
  }

  public MainGUI(BlFacade bl) {
    Platform.startup(() -> {
      try {
        setBusinessLogic(bl);
        init(new Stage());
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
  }


  static class Window {
    Controller c;
    Parent ui;
  }

  private Window load(String fxmlfile) throws IOException {
    Window window = new Window();
    FXMLLoader loader = new FXMLLoader(MainGUI.class.getResource(fxmlfile), ResourceBundle.getBundle("Etiquetas", Locale.getDefault()));
    loader.setControllerFactory(controllerClass -> {

      if (controllerClass == PortalController.class)    // For both Portal and AdminPortal
        return new PortalController(businessLogic);

      if (controllerClass == BrowseQuestionsController.class)
        return new BrowseQuestionsController(businessLogic);

      if (controllerClass == CreateQuestionController.class)
        return new CreateQuestionController(businessLogic);

      if(controllerClass== SetFeeController.class)
        return new SetFeeController(businessLogic);

      if(controllerClass== UserLoginController.class)
        return new UserLoginController(businessLogic);

      if(controllerClass== UserRegisterController.class)
        return new UserRegisterController(businessLogic);

      if (controllerClass == CreateNewEventController.class)
        return new CreateNewEventController(businessLogic);

      if (controllerClass == DepositMoneyController.class)
        return new DepositMoneyController(businessLogic);

      if (controllerClass == PlaceABetController.class)
        return new PlaceABetController(businessLogic);

      if (controllerClass == RemoveBetController.class)
        return new RemoveBetController(businessLogic);



      else {
        // default behavior for controllerFactory:
        try {
          return controllerClass.getDeclaredConstructor().newInstance();
        } catch (Exception exc) {
          exc.printStackTrace();
          throw new RuntimeException(exc); // fatal, just bail...
        }
      }


    });
    window.ui = loader.load();
    ((Controller) loader.getController()).setMainApp(this);
    window.c = loader.getController();
    return window;
  }

  public void init(Stage stage) throws IOException {

    this.stage = stage;

    portal = load("/Portal.fxml");
    adminPortal = load("/AdminPortal.fxml");
    userPortal = load("/UserPortal.fxml");
    browseQuestions = load("/BrowseQuestions.fxml");
    createQuestion = load("/CreateQuestion.fxml");
    setFee = load("/SetFee.fxml");
    createEvent = load("/CreateNewEvent.fxml");
    userLogin = load("/UserLogin.fxml");
    userRegister = load("/UserRegister.fxml");
    depositMoney = load("/DepositMoney.fxml");
    placeAbet = load("/PlaceABetv2.fxml");
    removeBet = load("/RemoveABet.fxml");

    showPortal();

  }

  public void showPortal(){setupScene(portal.ui, "Portal", 395, 315);}

  public void showAdminPortal(){setupScene(adminPortal.ui, "AdminPortal", 395, 470);}

  public void showUserPortal(){setupScene(userPortal.ui, "UserPortal", 395, 375);}

  public void showBrowseQuestions() {
    setupScene(browseQuestions.ui, "BrowseQuestions", 1000, 500);
  }

  public void showCreateQuestion() {
    setupScene(createQuestion.ui, "CreateQuestion", 550, 400);
  }

  public void showSetFee() {
    setupScene(setFee.ui, "SetFee", 1050, 480);
  }

  public void showCreateEvent() {
    setupScene(createEvent.ui, "CreateEvent", 650, 520);
  }

  public void showUserLogin() {
    setupScene(userLogin.ui, "Login", 565, 305);
  }

  public void showUserRegister() {
    setupScene(userRegister.ui, "Register", 600, 420);
  }

  public void showDepositMoney() { setupScene(depositMoney.ui,  "Deposit", 600, 450);}

  public void showPlaceABet(){setupScene(placeAbet.ui, "PlaceABet", 900, 680);}

  public void showRemoveABet(){setupScene(removeBet.ui, "RemoveABet", 940, 600);}



  private void setupScene(Parent ui, String title, int width, int height) {
    if (scene == null){
      scene = new Scene(ui, width, height);
      scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
      //scene.getStylesheets().add(getClass().getClassLoader().getResource("styles/hover.css").toString());
      stage.setScene(scene);
    }
    stage.setWidth(width);
    stage.setHeight(height);
    stage.setTitle(ResourceBundle.getBundle("Etiquetas",Locale.getDefault()).getString(title));
    scene.setRoot(ui);
    stage.show();
  }
}
