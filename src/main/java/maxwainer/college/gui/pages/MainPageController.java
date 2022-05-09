package maxwainer.college.gui.pages;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import maxwainer.college.gui.common.Alerts;
import maxwainer.college.gui.common.FXMLQuickLoader;
import org.jetbrains.annotations.NotNull;

public class MainPageController {

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

  private void openPage(final @NotNull String page) {
    try {
      final Stage parentStage = (Stage) registerButton.getScene().getWindow();
      final Parent parent = FXMLQuickLoader.load(page + ".fxml");

      parentStage.setScene(new Scene(parent));
      throw new IOException("I am exception");
    } catch (final IOException exception) {
      Alerts.showAlert(exception);
    }
  }

}
