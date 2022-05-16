package maxwainer.college.gui.pages;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import maxwainer.college.gui.common.MoreFormats;

public class BasePageController extends AbstractPage implements Initializable {

  @FXML
  private Label loggedInLabel;

  @FXML
  protected void onOrderTicketClick() {
    openPage("order-ticket");
  }

  @FXML
  protected void onListTicketsList() {
    openPage("list-tickets");
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    final var user = appValues.user();

    loggedInLabel.setText("Logged-in as: " + MoreFormats.formatUser(user));
  }
}
