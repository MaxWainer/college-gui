package maxwainer.college.gui.pages;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Optional;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import maxwainer.college.gui.common.Alerts;
import maxwainer.college.gui.exception.MissingPropertyException;
import maxwainer.college.gui.values.AppValues;
import maxwainer.college.gui.web.WebFetcherRegistry;
import maxwainer.college.gui.web.enums.user.LoginResult;
import maxwainer.college.gui.web.implementation.auth.LoginWebFetcher;
import maxwainer.college.gui.web.params.WebParameters;
import maxwainer.college.gui.web.result.EnumResult;
import maxwainer.college.gui.web.result.StringResult;

public class LoginPageController extends AbstractPage {

  @Inject
  private WebFetcherRegistry fetcherRegistry;

  @Inject
  private AppValues appValues;

  @FXML
  private PasswordField passwordField;

  @FXML
  private TextField usernameField;

  @FXML
  protected void onLoginClick() {
    // get username
    final var username = usernameField.getAccessibleText();
    // get password
    final var password = passwordField.getAccessibleText();

    final var optional = fetcherRegistry.findFetcher(LoginWebFetcher.class);

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
              .appendParam("username", username)
              .appendParam("password", password)
              .build()
      ).join();

      if (result instanceof EnumResult enumResult) {
        final var enumValue = (LoginResult) enumResult.value();

        if (enumValue == LoginResult.INVALID_PASSWORD) {
          Alerts.showError("Error while logging in", "Invalid password!");
        }

        if (enumValue == LoginResult.UNKNOWN_USER) {
          Alerts.showError("Error while logging in", "You are not registered!");
        }
      }

      if (result instanceof StringResult stringResult) {
        final String token = stringResult.value();
        appValues.accessToken(token);
      }

    } catch (MissingPropertyException | IOException e) {
      Alerts.showException(e);
    }
  }

}
