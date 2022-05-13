package maxwainer.college.gui.pages;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import maxwainer.college.gui.common.Alerts;
import maxwainer.college.gui.common.MoreResources;
import maxwainer.college.gui.values.AppValues;
import maxwainer.college.gui.web.WebFetcherRegistry;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractPage extends Parent {

  @Inject
  protected Injector injector;

  @Inject
  protected Stage parentStage;

  @Inject
  protected WebFetcherRegistry webFetcherRegistry;

  @Inject
  protected AppValues appValues;

  @Inject
  protected Provider<FXMLLoader> fxmlLoaderProvider;

  protected void openPage(final @NotNull String page) {
    try {
      final var loader = fxmlLoaderProvider.get();

      final var result = (Parent) loader.load(MoreResources.loadFxmlFile(page));

      parentStage.setScene(new Scene(result));
    } catch (final IOException exception) {
      Alerts.showException(exception);
    }
  }

}
