package maxwainer.college.gui.pages;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginPageController {

  @FXML
  private PasswordField passwordField;

  @FXML
  private TextField usernameField;

  @FXML
  protected void onLoginClick() {
    // get username
    final String username = usernameField.getAccessibleText();
    // get password
    final String password = passwordField.getAccessibleText();


  }

}
