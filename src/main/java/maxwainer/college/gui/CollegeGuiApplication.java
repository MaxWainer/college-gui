package maxwainer.college.gui;

import com.google.inject.Guice;
import com.google.inject.Injector;
import java.io.IOException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import maxwainer.college.gui.common.AppLogger;
import maxwainer.college.gui.common.MoreResources;
import maxwainer.college.gui.di.AppModule;
import maxwainer.college.gui.exception.MissingPropertyException;
import maxwainer.college.gui.task.WebServiceHeartbeatListener;
import maxwainer.college.gui.values.AppValues;
import maxwainer.college.gui.web.WebFetcherRegistry;
import maxwainer.college.gui.web.implementation.auth.ClearCacheWebFetcher;

public final class CollegeGuiApplication extends Application {

  private Injector injector;

  @Override
  public void stop() throws Exception {
    final var values = injector.getInstance(AppValues.class);

    if (values.accessTokenNotPresent()) {
      AppLogger.LOGGER.info(() -> "Token not found!");
    } else {
      final var webFetcherRegistry = injector
          .getInstance(WebFetcherRegistry.class);
      final var optionalFetcher = webFetcherRegistry
          .findFetcher(ClearCacheWebFetcher.class);

      if (optionalFetcher.isEmpty()) {
        throw new UnsupportedOperationException();
      }

      final var fetcher = optionalFetcher.get();

      try {
        AppLogger.LOGGER.info("Clear cache result: " + fetcher.fetchData().join());
      } catch (MissingPropertyException | IOException e) {
        throw new RuntimeException(e);
      }
    }
  }

  @Override
  public void start(Stage stage) throws IOException {
    // create injector
    this.injector = Guice.createInjector(new AppModule(stage));

    // we should check is server up before the main window appears
    // start few heartbeat service
    final var scheduler = injector.getInstance(ScheduledExecutorService.class);

    // check each second, is server down
    scheduler.scheduleAtFixedRate(injector.getInstance(WebServiceHeartbeatListener.class),
        0, 1,
        TimeUnit.MILLISECONDS);

    // main application entry

    // get loader
    final FXMLLoader loader = injector.getInstance(FXMLLoader.class);

    // load main page content
    final Parent content = loader.load(MoreResources.loadFxmlFile("main-page"));

    // set scene
    final var scene = new Scene(content, 900, 600);
    stage.setTitle("Trains - By Ilya Koreysha");

    // lock screen
    stage.setMinHeight(600);
    stage.setMaxHeight(600);
    stage.setMinWidth(900);
    stage.setMaxWidth(900);

    // show it
    stage.setScene(scene);
    stage.show();

    stage.setOnCloseRequest(event -> {
      try {
        stop();
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    });
  }

  public static void main(String[] args) {
    launch();
  }
}