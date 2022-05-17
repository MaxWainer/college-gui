package maxwainer.college.gui.pages;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import maxwainer.college.gui.common.MoreFormats;

public class BasePageController extends AbstractPage implements Initializable {

  @FXML
  private Label loggedInLabel;

  @FXML
  private Button activesEditor;

  @FXML
  private Button trainsEditor;

  @FXML
  private Button directionsEditor;

  @FXML
  private Button usersEditor;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    final var user = appValues.user();

    loggedInLabel.setText("Logged-in as: " + MoreFormats.formatUser(user));

    if (user.moderator()) {
      activesEditor.setVisible(true);
      trainsEditor.setVisible(true);
      directionsEditor.setVisible(true);
    }

    if (user.admin()) {
      usersEditor.setVisible(true);
    }
  }

  @FXML
  protected void onOrderTicketClick() {
    openPage("order-ticket");
  }

  @FXML
  protected void onListTicketsClick() {
    openPage("list-tickets");
  }

  @FXML
  protected void onCheckActivesClick() {
    openPage("list-actives");
  }

  // admin/moderator
  @FXML
  protected void onEditUsersClick() {
    openPage("editor/edit-users");
  }

  @FXML
  protected void onEditDirectionsClick() {
    openPage("editor/edit-directions");
  }

  @FXML
  protected void onEditTrainsClick() {
    openPage("editor/edit-trains");
  }

  @FXML
  protected void onEditActivesClick() {
    openPage("editor/edit-actives");
  }
}
