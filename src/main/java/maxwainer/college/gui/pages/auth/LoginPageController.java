package maxwainer.college.gui.pages.auth;

import com.google.inject.Inject;
import java.io.IOException;
import javafx.fxml.FXML;
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

public class LoginPageController extends AbstractPage {

  @Inject
  private AppValues appValues;

  @FXML
  private PasswordField passwordField;

  @FXML
  private TextField usernameField;

  @FXML
  private NumericField passportIdField;

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

      if (result instanceof StringResult stringResult) {
        final String token = stringResult.value();
        appValues.accessToken(token);

        Alerts.showInfo("Successful logging-in!", "Your token: " + token);
        openPage("base-page");
      }

    } catch (MissingPropertyException | IOException e) {
      Alerts.showException(e);
    }
  }

}
