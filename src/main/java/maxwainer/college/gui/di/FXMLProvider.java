package maxwainer.college.gui.di;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;
import javafx.fxml.FXMLLoader;

final class FXMLProvider implements Provider<FXMLLoader> {

  @Inject
  private Injector injector;

  @Override
  public FXMLLoader get() {
    final var loader = new FXMLLoader();

    loader.setControllerFactory(injector::getInstance);

    return loader;
  }
}
