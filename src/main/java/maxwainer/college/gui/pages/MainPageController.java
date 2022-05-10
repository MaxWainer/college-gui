package maxwainer.college.gui.pages;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainPageController extends AbstractPage {

  // fxml
  @FXML
  private Button loginButton;

  @FXML
  private Button registerButton;

  @FXML
  protected void onLoginClick() {
    openPage("login-page");
  }

  @FXML
  protected void onRegisterClick() {
    openPage("register-page");
  }
}
