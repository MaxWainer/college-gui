package maxwainer.college.gui.task;

import com.google.inject.Inject;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import javafx.application.Platform;
import javafx.stage.Stage;
import maxwainer.college.gui.common.AppLogger;
import maxwainer.college.gui.config.AppConfig;
import okhttp3.OkHttpClient;
import okhttp3.Request;

// TODO: Fix when application won't stop
public final class WebServiceHeartbeatListener implements Runnable {

  @Inject
  private Stage primaryStage;

  @Inject
  private AppConfig appConfig;

  @Inject
  private OkHttpClient client;

  @Override
  public void run() {
    try {
      final var request = new Request.Builder()
          .url(appConfig.baseUrl())
          .head()
          .build();

      final var call = client.newCall(request); // create new call

      try (final var response = call.execute()) { // execute it
        final var errorStatus = response.code();

        // all 500-more errors indicates server-side problem
        // service unavailable, internal error and etc.
        if (errorStatus >= 500) {
          exit();
        }
      }
    } catch (MalformedURLException e) {
      AppLogger.LOGGER.log(Level.SEVERE, e, () -> "Error");
    } catch (IOException e) {
      exit();
    }
  }

  private void exit() {
    AppLogger.LOGGER.severe("Server is down! Stopping application...");

//    final var alert = new Alert(AlertType.ERROR);
//    alert.setContentText("Application will be stopped");
//    alert.setHeaderText("Server stopped responding!");
//    alert.setTitle("Critical error!");
//
//    alert.show();

    primaryStage.hide();
    Platform.exit();
    System.exit(0);
  }
}
