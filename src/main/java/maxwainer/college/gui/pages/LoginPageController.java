package maxwainer.college.gui.pages;

import com.google.inject.Inject;
import java.util.Optional;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import maxwainer.college.gui.web.WebResult;
import maxwainer.college.gui.web.WebFetcherRegistry;
import maxwainer.college.gui.web.WebResult.InternalCodes;
import maxwainer.college.gui.web.implementation.AuthWebFetcher;
import maxwainer.college.gui.web.params.WebParameters;

public class LoginPageController {

  @Inject
  private WebFetcherRegistry fetcherRegistry;

  @FXML
  private PasswordField passwordField;

  @FXML
  private TextField usernameField;

  @FXML
  private AnchorPane pane;

  @FXML
  protected void onLoginClick() {
    // get username
    final String username = usernameField.getAccessibleText();
    // get password
    final String password = passwordField.getAccessibleText();

    final Optional<AuthWebFetcher> optional = fetcherRegistry.findFetcher(AuthWebFetcher.class);

    if (optional.isEmpty()) {
      pane.getChildren().add(new Label(
          "Internal error, contact developer! Developer information: 'Auth fetcher is not present!'"));
      return;
    }

    final AuthWebFetcher fetcher = optional.get();

    final WebResult<String> result;
    try {
      result = fetcher.fetchData(
          WebParameters
              .builder()
              .appendParam("username", username)
              .appendParam("password", password)
              .build()
      ).join();
    } catch (Throwable e) {

      throw new RuntimeException(e);
    }

    if (result.flagsPresent(InternalCodes.AUTH_INVALID_LOGIN)) {
      pane.getChildren().add(new Label("Invalid login!"));
      return;
    }

    if (result.flagsPresent(InternalCodes.AUTH_INVALID_PASSWORD)) {
      pane.getChildren().add(new Label("Invalid password!"));
      return;
    }
  }

}
