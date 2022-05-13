package maxwainer.college.gui.pages.auth;

import static javafx.beans.binding.Bindings.isEmpty;

import com.google.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import maxwainer.college.gui.common.Alerts;
import maxwainer.college.gui.exception.MissingPropertyException;
import maxwainer.college.gui.layout.NumericField;
import maxwainer.college.gui.pages.AbstractPage;
import maxwainer.college.gui.values.AppValues;
import maxwainer.college.gui.web.WebFetcherRegistry;
import maxwainer.college.gui.web.enums.user.TokenResult;
import maxwainer.college.gui.web.implementation.auth.LoginWebFetcher;
import maxwainer.college.gui.web.params.WebParameters;
import maxwainer.college.gui.web.result.EnumResult;
import maxwainer.college.gui.web.result.StringResult;

public class LoginPageController extends AbstractAuthPage {

  @Inject
  private AppValues appValues;

  @FXML
  private PasswordField passwordField;

  @FXML
  private TextField usernameField;

  @FXML
  private NumericField passportIdField;

  @FXML
  private Button loginButton;

  @FXML
  protected void onLoginClick() {
    // get username
    final var username = usernameField.getText();
    // get password
    final var password = passwordField.getText();

    final var passportId = passportIdField.getValue();

    final var optional = webFetcherRegistry.findFetcher(LoginWebFetcher.class);

    if (optional.isEmpty()) {
      Alerts.showError("Internal error, contact developer!",
          "Developer information: 'Auth fetcher is not present!'");
      return;
    }

    final var fetcher = optional.get();

    try {
      final var result = fetcher.fetchData(
          WebParameters
              .builder()
              .appendParam("passportId", passportId)
              .appendParam("username", username)
              .appendParam("password", password)
              .build()
      ).join();

      if (result instanceof EnumResult enumResult) {
        final var enumValue = (TokenResult) enumResult.value();

        if (enumValue == TokenResult.INVALID_PASSWORD) {
          Alerts.showError("Error while logging in", "Invalid password!");
        }

        if (enumValue == TokenResult.UNKNOWN_USER) {
          Alerts.showError("Error while logging in", "You are not registered!");
        }

        if (enumValue == TokenResult.ALREADY_LOGGED_IN) {
          Alerts.showError("Error while logging in", "You are already have active session!");
        }
      }

      handleResult(result);

    } catch (MissingPropertyException | IOException e) {
      Alerts.showException(e);
    }
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    loginButton.disableProperty()
        .bind(
            isEmpty(passportIdField.textProperty())
                .or(isEmpty(usernameField.textProperty()))
                .or(isEmpty(passwordField.textProperty()))
        );
  }
}
