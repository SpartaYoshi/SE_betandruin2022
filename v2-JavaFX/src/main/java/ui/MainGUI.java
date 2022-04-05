package ui;

import businessLogic.BlFacade;
import javafx.application.Application;
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

  private Window portalLag, mainAdminLag, createQuestionLag, browseQuestionsLag,setFeeLag, UserLoginLag, createEventLag, userLoginLag,depositMoneyLag;

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


  class Window {
    Controller c;
    Parent ui;
  }

  private Window load(String fxmlfile) throws IOException {
    Window window = new Window();
    FXMLLoader loader = new FXMLLoader(MainGUI.class.getResource(fxmlfile), ResourceBundle.getBundle("Etiquetas", Locale.getDefault()));
    loader.setControllerFactory(controllerClass -> {

      if (controllerClass == MainAdminController.class) {
        return new MainAdminController(businessLogic);
      }
      if (controllerClass == PortalController.class) {
        return new PortalController(businessLogic);
      }
      if (controllerClass == BrowseQuestionsController.class) {
        return new BrowseQuestionsController(businessLogic);
      }
      if (controllerClass == CreateQuestionController.class) {
        return new CreateQuestionController(businessLogic);
      }
      if(controllerClass== SetFeeController.class){
        return new SetFeeController(businessLogic);
      }
      if(controllerClass== UserLoginController.class){
        return new UserLoginController(businessLogic);
      }
      if (controllerClass == CreateNewEventController.class) {
        return new CreateNewEventController(businessLogic);
      }
      if (controllerClass == DepositMoneyController.class) {
        return new DepositMoneyController(businessLogic);
      }

      if (controllerClass == PlaceABetController.class) {
        return new PlaceABetController(businessLogic);
      }



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

    mainAdminLag = load("/MainAdmin.fxml");
    portalLag = load("/Portal.fxml");
    browseQuestionsLag = load("/BrowseQuestions.fxml");
    createQuestionLag = load("/CreateQuestion.fxml");
    setFeeLag=load("/SetFee.fxml");
    createEventLag = load("/CreateNewEvent.fxml");
    userLoginLag = load("/UserLogin.fxml");
    depositMoneyLag=load("/DepositMoney.fxml");


    showDepositMoney();

  }

//  public void start(Stage stage) throws IOException {
//      init(stage);
//  }

  public void showMainAdmin(){setupScene(userLoginLag.ui, "MainAdmin", 320, 250);}

  public void showPortal(){setupScene(portalLag.ui, "Portal", 320, 250);}

  public void showBrowseQuestions() {
    setupScene(browseQuestionsLag.ui, "BrowseQuestions", 1000, 500);
  }

  public void showCreateQuestion() {
    setupScene(createQuestionLag.ui, "CreateQuestion", 550, 400);
  }

  public void showSetFee() {
    setupScene(setFeeLag.ui, "SetFee", 1050, 480);
  }

  public void showCreateNewEvent() {
    setupScene(createEventLag.ui, "CreateEvent", 650, 520);
  }

  public void showUserLogin() {
    setupScene(userLoginLag.ui, "Login", 650, 450);
  }

  public void showDepositMoney() { setupScene(depositMoneyLag.ui,  "Deposit", 600, 450);
  }


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

//  public static void main(String[] args) {
//    launch();
//  }
}
