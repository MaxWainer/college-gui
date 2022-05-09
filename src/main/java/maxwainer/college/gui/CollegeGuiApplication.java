package maxwainer.college.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import maxwainer.college.gui.values.AppValues;

public final class CollegeGuiApplication extends Application {

  private AppValues values;

  @Override
  public void start(Stage stage) throws IOException {
    final FXMLLoader fxmlLoader = new FXMLLoader();
    final Parent content = fxmlLoader.load(
        CollegeGuiApplication
            .class
            .getClassLoader()
            .getResourceAsStream("maxwainer/college/gui/main-page.fxml"));

    final Scene scene = new Scene(content, 900, 600);
    stage.setTitle("Trains - By Max_Wainer");

    // lock screen
    stage.setMinHeight(400);
    stage.setMaxHeight(400);
    stage.setMinWidth(600);
    stage.setMaxWidth(600);

    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    launch();
  }
}