package maxwainer.college.gui.pages;

import javafx.fxml.FXML;

public class MainPageController extends AbstractPage {

  protected void onLoginClick() {
    openPage("login-page");
  }

  protected void onRegisterClick() {
    openPage("register-page");
  }
}
