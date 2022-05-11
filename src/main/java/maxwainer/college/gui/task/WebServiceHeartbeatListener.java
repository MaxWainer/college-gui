package maxwainer.college.gui.task;

import com.google.gson.JsonParser;
import com.google.inject.Inject;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import maxwainer.college.gui.common.AppLogger;
import maxwainer.college.gui.config.AppConfig;
import maxwainer.college.gui.exception.MissingPropertyException;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public final class WebServiceHeartbeatListener implements Runnable {

  @Inject
  private AppConfig appConfig;

  @Inject
  private OkHttpClient client;

  @Override
  public void run() {
    AppLogger.LOGGER.info("Checking server for heartbeat...");
    try {
      final var request = new Request.Builder()
          .url(appConfig.getOrThrow("base-url", String.class))
          .head()
          .build();

      final var call = client.newCall(request); // create new call

      try (final var response = call.execute()) { // execute it
        final var errorStatus = response.code();

        AppLogger.LOGGER.info(() -> String.valueOf(errorStatus));

        // all 500-more errors indicates server-side problem
        // service unavailable, internal error and etc.
        if (errorStatus >= 500) {
          exit();
        }
      }
    } catch (MalformedURLException | MissingPropertyException e) {
      AppLogger.LOGGER.log(Level.SEVERE, e, () -> "Error");
    } catch (IOException e) {
      exit();
    }
  }

  private static void exit() {
    AppLogger.LOGGER.severe("Server is down! Stopping application...");

    final var alert = new Alert(AlertType.ERROR);
    alert.setContentText("Application will be stopped");
    alert.setHeaderText("Server stopped responding!");
    alert.setTitle("Critical error!");

    Platform.exit(); // exit javafx application
    System.exit(0); // program exit exit
  }
}
