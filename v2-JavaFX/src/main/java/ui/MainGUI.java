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

  private Window portal, adminPortal, userPortal, createQuestion, browseQuestions, setFee, removeBet, userLogin, userRegister, createEvent, placeAbet, depositMoney, showMovements;

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

      if (controllerClass == ShowMovementsController.class)
        return new ShowMovementsController(businessLogic);



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
    removeBet = load("/RemoveaBetv2.fxml");
    showMovements = load("/ShowMovements.fxml");

    showPortal();

  }

  public void showPortal(){setupScene(portal.ui, "Portal", 395, 315);}

  public void showAdminPortal(){setupScene(adminPortal.ui, "Admin Portal", 395, 470);}

  public void showUserPortal(){setupScene(userPortal.ui, "User Portal", 395, 375);}

  public void showBrowseQuestions() {
    setupScene(browseQuestions.ui, "Browse Questions", 1000, 500);
  }

  public void showCreateQuestion() {
    setupScene(createQuestion.ui, "Create Question", 550, 420);
  }

  public void showSetFee() {
    setupScene(setFee.ui, "Set Fee", 1050, 500);
  }

  public void showCreateEvent() {
    setupScene(createEvent.ui, "Create Event", 650, 520);
  }

  public void showUserLogin() {
    setupScene(userLogin.ui, "Login", 565, 305);
  }

  public void showUserRegister() {
    setupScene(userRegister.ui, "Register", 600, 420);
  }

  public void showDepositMoney() { setupScene(depositMoney.ui,  "Deposit Money", 600, 450);}

  public void showPlaceABet(){setupScene(placeAbet.ui, "Place A Bet", 900, 680);}

  public void showRemoveABet(){setupScene(removeBet.ui, "Remove A Bet", 940, 520);}

  public void showShowMovements(){setupScene(showMovements.ui, "Show Movements", 395, 470);}



  private void setupScene(Parent ui, String title, int width, int height) {
    if (scene == null){
      scene = new Scene(ui, width, height);
      scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
      stage.setScene(scene);
    }
    stage.setWidth(width);
    stage.setHeight(height);
    stage.setTitle(ResourceBundle.getBundle("Etiquetas",Locale.getDefault()).getString(title));
    scene.setRoot(ui);
    stage.show();
  }
}
