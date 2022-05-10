package maxwainer.college.gui.pages;

import com.google.inject.Provider;
import javafx.fxml.FXMLLoader;
import javax.inject.Inject;
import com.google.inject.Injector;
import java.io.IOException;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import maxwainer.college.gui.common.Alerts;
import maxwainer.college.gui.common.FXMLQuickLoader;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractPage extends Parent {

  @Inject
  protected Injector injector;

  @Inject
  protected Stage parentStage;

  @Inject
  protected Provider<FXMLLoader> fxmlLoaderProvider;

  protected void openPage(final @NotNull String page) {
    try {
      final var loader = fxmlLoaderProvider.get();

      final var result = (Parent) loader.load(FXMLQuickLoader.load(page));

      parentStage.setScene(new Scene(result));
    } catch (final IOException exception) {
      Alerts.showException(exception);
    }
  }

}
