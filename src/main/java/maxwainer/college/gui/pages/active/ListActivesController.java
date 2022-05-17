package maxwainer.college.gui.pages.active;

import com.google.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import maxwainer.college.gui.common.Alerts;
import maxwainer.college.gui.common.MethodVisitorCellValueFactory;
import maxwainer.college.gui.common.MoreFormats;
import maxwainer.college.gui.exception.MissingPropertyException;
import maxwainer.college.gui.object.web.Active;
import maxwainer.college.gui.pages.AbstractSubPage;
import maxwainer.college.gui.web.implementation.active.ActiveListWebFetcher;

public class ListActivesController extends AbstractSubPage implements Initializable {

  @Inject
  private ActiveListWebFetcher activeListWebFetcher;

  @FXML
  private TableColumn<Active, String> trainColumn;

  @FXML
  private TableColumn<Active, String> arriveTimeColumn;

  @FXML
  private TableColumn<Active, String> stationColumn;

  @FXML
  private TableColumn<Active, String> directionColumn;

  @FXML
  private TableView<Active> activesTableView;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    initColumns();

    try {
      final var result = activeListWebFetcher.fetchData()
          .join();

      activesTableView.getItems().addAll(result);
    } catch (MissingPropertyException | IOException e) {
      Alerts.showException(e);
    }
  }

  private void initColumns() {

    trainColumn.setCellValueFactory(
        new MethodVisitorCellValueFactory<>(active -> active.train().name()));

    stationColumn.setCellValueFactory(
        new MethodVisitorCellValueFactory<>(active -> active.station().name()));

    arriveTimeColumn.setCellValueFactory(
        new MethodVisitorCellValueFactory<>(
            active -> MoreFormats.formatTime(active.startDateTime())));

    directionColumn.setCellValueFactory(
        new MethodVisitorCellValueFactory<>(active -> active.mainDirection().name()));
  }

  @Override
  protected String backPageName() {
    return "base-page";
  }
}
