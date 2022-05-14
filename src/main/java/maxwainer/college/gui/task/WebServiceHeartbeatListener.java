package maxwainer.college.gui.task;

import com.google.inject.Inject;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import maxwainer.college.gui.CollegeGuiApplication;
import maxwainer.college.gui.common.AppLogger;
import maxwainer.college.gui.config.AppConfig;
import maxwainer.college.gui.exception.MissingPropertyException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;

public final class WebServiceHeartbeatListener implements Runnable {

  @Inject
  private Stage primaryStage;

  @Inject
  private AppConfig appConfig;

  @Inject
  private OkHttpClient client;

  private String checkUrl;

  @Override
  public void run() {
    try {
      final var request = new Request.Builder()
          .url(checkUrl())
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
    } catch (MalformedURLException | MissingPropertyException e) {
      AppLogger.LOGGER.log(Level.SEVERE, e, () -> "Error");
    } catch (IOException e) {
      exit();
    }
  }

  private @NotNull String checkUrl() throws MissingPropertyException {
    if (this.checkUrl == null) {
      this.checkUrl = String.format(
          "%s/dummy/Dummy/check",
          appConfig.baseUrl()
      );
    }

    return this.checkUrl;
  }

  private void exit() {
    AppLogger.LOGGER.severe("Server is down! Stopping application...");

//    final var alert = new Alert(AlertType.ERROR);
//    alert.setContentText("Application will be stopped");
//    alert.setHeaderText("Server stopped responding!");
//    alert.setTitle("Critical error!");
//
//    alert.show();


    primaryStage.setScene(null);
    // System.exit(1); // program exit exit
    Platform.exit(); // exit javafx application

    throw new RuntimeException("Unreachable");
  }
}
