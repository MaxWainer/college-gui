package maxwainer.college.gui;

import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import maxwainer.college.gui.di.AppModule;

public final class CollegeGuiApplication extends Application {

  @Override
  public void start(Stage stage) throws IOException {
    // create injector
    final Injector injector = Guice.createInjector(new AppModule(stage));

    // get loader
    final FXMLLoader loader = injector.getInstance(FXMLLoader.class);

    // load main page content
    final Parent content = loader.load(
        CollegeGuiApplication
            .class
            .getClassLoader()
            .getResourceAsStream("maxwainer/college/gui/main-page.fxml"));

    // set scene
    final Scene scene = new Scene(content, 900, 600);
    stage.setTitle("Trains - By Ilya Koreysha");

    // lock screen
    stage.setMinHeight(600);
    stage.setMaxHeight(600);
    stage.setMinWidth(900);
    stage.setMaxWidth(900);

    // show it
    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    launch();
  }
}