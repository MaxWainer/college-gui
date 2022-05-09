package maxwainer.college.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import maxwainer.college.gui.pages.LoginPageController;
import maxwainer.college.gui.values.AppValues;

public class HelloApplication extends Application {

  private AppValues values;

  @Override
  public void start(Stage stage) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(
        LoginPageController.class.getResource("pages/login-page.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), 320, 240);
    stage.setTitle("Trains - By Max_Wainer");
    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    launch();
  }
}