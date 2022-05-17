package maxwainer.college.gui.pages.editor.admin;

import com.google.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.stage.Stage;
import javafx.util.Callback;
import maxwainer.college.gui.common.Alerts;
import maxwainer.college.gui.common.MoreFormats;
import maxwainer.college.gui.common.MoreResources;
import maxwainer.college.gui.exception.MissingPropertyException;
import maxwainer.college.gui.object.web.User;
import maxwainer.college.gui.pages.AbstractBasicSubPage;
import maxwainer.college.gui.web.implementation.user.UserListWebFetcher;
import org.jetbrains.annotations.NotNull;

public class EditUsersPageController extends AbstractBasicSubPage implements Initializable {

  @Inject
  private Stage stage;

  @Inject
  private UserListWebFetcher userListWebFetcher;

  @FXML
  private ListView<User> usersView;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    usersView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    usersView.setCellFactory(UserCellFactory.INSTANCE);

    try {
      final var result = userListWebFetcher.fetchData()
          .join();

      usersView.getItems().addAll(result);
    } catch (MissingPropertyException | IOException e) {
      Alerts.showException(e);
    }
  }

  @FXML
  protected void onOpenEditorClick() {
    final var selected = usersView.getSelectionModel().getSelectedItem();

    if (selected.superUser()) {
      Alerts.showError("You don't have permission!", "You are not allowed to edit super-user!");
      return;
    }

    final var page = createEditor(selected);

    stage.setScene(page);
  }

  private @NotNull Scene createEditor(final @NotNull User target) {
    try {
      final var loader = injector.getInstance(FXMLLoader.class);

      final ScopedUserEditorPage page = loader.load(
          MoreResources.loadFxmlFile("editor/scope/scoped-user-editor"));

      page.applyTarget(target);
      page.initialize();

      return new Scene(page);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private static final class UserCellFactory implements Callback<ListView<User>, ListCell<User>> {

    public static final UserCellFactory INSTANCE = new UserCellFactory();

    private UserCellFactory() {}

    @Override
    public ListCell<User> call(ListView<User> param) {
      return new ListCell<>() {
        @Override
        protected void updateItem(User item, boolean empty) {
          if (item == null || empty) return;

          setText(MoreFormats.formatUser(item));
        }
      };
    }
  }
}
