package maxwainer.college.gui.pages.auth;

import static javafx.beans.binding.Bindings.isEmpty;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import maxwainer.college.gui.common.Alerts;
import maxwainer.college.gui.layout.NumericField;
import maxwainer.college.gui.web.enums.user.RegisterResult;
import maxwainer.college.gui.web.implementation.auth.RegisterWebFetcher;
import maxwainer.college.gui.web.params.WebParameters;
import maxwainer.college.gui.web.result.EnumResult;

public class RegisterPageController extends AbstractAuthPage implements Initializable {

  @FXML
  private PasswordField firstPasswordField;

  @FXML
  private PasswordField repeatPasswordField;

  @FXML
  private TextField usernameField;

  @FXML
  private TextField firstNameField;

  @FXML
  private TextField secondNameField;

  @FXML
  private TextField patronymicField;

  @FXML
  private NumericField passportIdField;

  @FXML
  private Button registerButton;


  @FXML
  protected void onRegisterClick() {
    final var password = firstPasswordField.getText();
    final var repeatedPassword = repeatPasswordField.getText();

    if (!password.equals(repeatedPassword)) {
      Alerts.showError("Input data", "Passwords is not equals!");
      return;
    }

    final var firstName = firstNameField.getText();
    final var secondName = secondNameField.getText();
    final var patronymic = patronymicField.getText();
    final var username = usernameField.getText();

    final var passportId = passportIdField.getValue();

    final var optional = webFetcherRegistry.findFetcher(RegisterWebFetcher.class);

    if (optional.isEmpty()) {
      Alerts.showError("Internal error, contact developer!",
          "Developer information: 'Auth fetcher is not present!'");
      return;
    }

    final var fetcher = optional.get();

    try {
      final var webParams = WebParameters.builder()
          .appendParam("firstName", firstName)
          .appendParam("secondName", secondName)
          .appendParam("patronymic", patronymic)
          .appendParam("username", username)
          .appendParam("password", password)
          .appendParam("passportId", passportId)
          .build();

      final var result = fetcher
          .fetchData(webParams)
          .join();

      if (result instanceof EnumResult enumResult) {
        final var enumValue = (RegisterResult) enumResult.value();

        if (enumValue == RegisterResult.PASSWORD_IS_NOT_STRONG) {
          Alerts.showError("Error while registering", "Your password is not that strong!");
        }

        if (enumValue == RegisterResult.USER_ALREADY_EXISTS) {
          Alerts.showError("Error while registering", "User with this name already exists!");
        }

        if (enumValue == RegisterResult.INTERNAL_ERROR) {
          Alerts.showError("Error while registering", "An internal error accquired!");
        }
      }

      handleResult(result);

    } catch (final Exception exception) {
      Alerts.showException(exception);
    }
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    registerButton.disableProperty()
        .bind(
            isEmpty(passportIdField.textProperty())
                .or(isEmpty(usernameField.textProperty()))
                .or(isEmpty(firstPasswordField.textProperty()))
                .or(isEmpty(repeatPasswordField.textProperty()))
                .or(isEmpty(firstNameField.textProperty()))
                .or(isEmpty(secondNameField.textProperty()))
                .or(isEmpty(patronymicField.textProperty()))
        );
  }
}
