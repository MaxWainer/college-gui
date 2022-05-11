package maxwainer.college.gui.pages;

import javafx.fxml.FXML;

public class MainPageController extends AbstractPage {

  @FXML
  protected void onLoginClick() {
    openPage("login-page");
  }

  @FXML
  protected void onRegisterClick() {
    openPage("register-page");
  }
}
