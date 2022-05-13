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

  private Window portal;
  private Window adminPortal;
  private Window userPortal;
  private Window createQuestion;
  private Window browseQuestions;
  private Window setFee;
  private Window removeBet;
  private Window userLogin;
  private Window userRegister;
  private Window createEvent;
  private Window placeAbet;
  private Window depositMoney;
  private Window showMovements;
  private Window removeEvent;
  private Window myProfile;
  private Window editProfile;
  private Window checkMyResults;
  private Window publishResults;

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

      if (controllerClass == MyProfileController.class)
        return new MyProfileController(businessLogic);

      if (controllerClass == EditPasswordController.class)
        return new EditPasswordController(businessLogic);

      if (controllerClass == ShowMovementsController.class)
        return new ShowMovementsController(businessLogic);

      if (controllerClass == RemoveEventController.class)
        return new RemoveEventController(businessLogic);

      if (controllerClass == CheckMyResultsController.class)
        return new CheckMyResultsController(businessLogic);

      if(controllerClass== PublishResultsController.class)
        return new PublishResultsController(businessLogic);



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

    this.stage = stage; //

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
    myProfile = load("/MyProfile.fxml");
    editProfile = load("/EditPassword.fxml");
    showMovements = load("/ShowMovements.fxml");
    removeEvent = load("/RemoveEvent.fxml");
    checkMyResults = load("/CheckMyResults.fxml");
    publishResults = load("/PublishResults.fxml");




    // DO NOT CHANGE - THE APPLICATION DETECTS IF YOU ARE LOGGED IN
    switch (businessLogic.getSessionMode()) {
      case "Anon" -> showPortal();
      case "User" -> showUserPortal();
      case "Admin" -> showAdminPortal();
    }









  }

  public void showPortal(){
    if (businessLogic.getCurrentUser() != null)
      businessLogic.refreshUser();
      setupScene(portal.ui, "Portal", 395, 315);
  }

  public void showAdminPortal(){
    if (businessLogic.getCurrentUser() != null)
      businessLogic.refreshUser();
    setupScene(adminPortal.ui, "AdminPortal", 395, 575);}

  public void showUserPortal(){
    if (businessLogic.getCurrentUser() != null)
      businessLogic.refreshUser();
    setupScene(userPortal.ui, "UserPortal", 395, 400);}

  public void showBrowseQuestions() {
    setupScene(browseQuestions.ui, "BrowseQuestions", 1000, 500);
  }

  public void showCreateQuestion() {
    setupScene(createQuestion.ui, "CreateQuestion", 615, 455);
  }

  public void showSetFee() {
    setupScene(setFee.ui, "SetFee", 1050, 500);
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

  public void showPlaceABet(){
    if (businessLogic.getCurrentUser() != null)
        ((PlaceABetController) placeAbet.c).displayUsersMoney();
    setupScene(placeAbet.ui, "PlaceBet", 900, 680);
  }

  public void showRemoveABet(){setupScene(removeBet.ui, "RemoveBet", 940, 520);}

  public void showRemoveEvent(){setupScene(removeEvent.ui, "RemoveEvent", 680, 475);}


  public void showShowMovements(){
    if (businessLogic.getCurrentUser() != null)
      ((ShowMovementsController) showMovements.c).initialize();
    setupScene(showMovements.ui, "ShowMovements", 650, 470);}


  public void showMyProfile() {
    if (businessLogic.getCurrentUser() != null)
      ((MyProfileController) myProfile.c).usersData();
    setupScene(myProfile.ui, "MyProfile", 750, 400);
  }


  public void showEditPassword(){
    if (businessLogic.getCurrentUser() != null)
      ((EditPasswordController) editProfile.c).usersSomeData();
    setupScene(editProfile.ui, "EditPassword", 600, 400);
  }


  public void showPublishResults(){setupScene(publishResults.ui, "Publish", 870, 590);}


  public void showCheckMyResults(){
    if (businessLogic.getCurrentUser() != null)
      ((CheckMyResultsController) checkMyResults.c).getResults();
    setupScene(checkMyResults.ui, "ChekMyResults", 890, 450);
  }




  private void setupScene(Parent ui, String title, int width, int height) {
    if (scene == null){
      scene = new Scene(ui);
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
