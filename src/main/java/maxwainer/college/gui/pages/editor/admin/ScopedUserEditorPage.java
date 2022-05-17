package maxwainer.college.gui.pages.editor.admin;

import com.google.common.collect.Sets;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import maxwainer.college.gui.common.Alerts;
import maxwainer.college.gui.exception.MissingPropertyException;
import maxwainer.college.gui.object.web.Ticket;
import maxwainer.college.gui.object.web.User;
import maxwainer.college.gui.pages.ticket.AbstractTicketListPage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ScopedUserEditorPage extends AbstractTicketListPage {

  private static final Set<String> ALLOWED_ROLES = Sets.newHashSet("User", "Moderator",
      "Administrator");

  private final AtomicReference<User> target = new AtomicReference<>(null);

  @FXML
  private ComboBox<String> rolesBox;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    if (target() == null) {
      return;
    }

    super.initialize(location, resources);
  }

  @Override
  protected void initialize0() {
    // roles start
    final var roleItems = rolesBox.getItems();

    for (final var allowedRole : ALLOWED_ROLES) {
      if (!target().role().equals(allowedRole)) {
        roleItems.add(allowedRole);
      }
    }

    rolesBox.getSelectionModel().selectedItemProperty()
        .addListener((__, ___, value) -> target.updateAndGet(user -> user.updateRole(value)));
    // roles end
  }

  @FXML
  protected void onSaveChangesClick() {

  }

  @FXML
  protected void onRemoveSelectedItems() {
    handleRemoveSelected();
  }

  @Override
  protected void onBackButtonClick() {
    final var confirm = Alerts.showConfirmation("Are you sure?",
            "All unsaved changes will be discarded!")
        .showAndWait();

    if (confirm.isEmpty()) {
      return;
    }

    final var result = confirm.get();

    if (result == ButtonType.CANCEL) {
      return;
    }

    super.onBackButtonClick();
  }

  @Override
  protected @NotNull Collection<Ticket> listTickets() throws MissingPropertyException, IOException {
    return target().tickets();
  }

  @Override
  protected String backPageName() {
    return "edit-users";
  }

  void applyTarget(User target) {
    this.target.set(target);
  }

  void initialize() {
    initialize(null, null);
  }

  @Nullable User target() {
    return target.get();
  }
}
