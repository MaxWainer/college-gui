package maxwainer.college.gui.pages.ticket;

import static javafx.beans.binding.Bindings.isNull;

import com.google.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.StringConverter;
import maxwainer.college.gui.common.Alerts;
import maxwainer.college.gui.common.MethodVisitorCellValueFactory;
import maxwainer.college.gui.common.MoreFormats;
import maxwainer.college.gui.exception.MissingPropertyException;
import maxwainer.college.gui.object.web.Active;
import maxwainer.college.gui.object.web.Carriage;
import maxwainer.college.gui.object.web.Direction;
import maxwainer.college.gui.object.web.Sitting;
import maxwainer.college.gui.object.web.Station;
import maxwainer.college.gui.object.web.Train;
import maxwainer.college.gui.pages.AbstractSubPage;
import maxwainer.college.gui.web.enums.ticket.OrderTicketResult;
import maxwainer.college.gui.web.implementation.direction.DirectionListWebFetcher;
import maxwainer.college.gui.web.implementation.ticket.OrderTicketWebFetcher;
import maxwainer.college.gui.web.params.WebParameters;
import org.jetbrains.annotations.NotNull;

public class OrderTicketController extends AbstractSubPage implements Initializable {

  @Inject
  private OrderTicketWebFetcher orderFetcher;

  @Inject
  private DirectionListWebFetcher directionFetcher;

  @FXML
  private Button orderTicket;

  @FXML
  private ComboBox<Station> endStation;

  @FXML
  private ComboBox<Direction> directions;

  @FXML
  private ComboBox<Active> active;

  @FXML
  private ListView<Carriage> carriages;

  @FXML
  public ListView<Train> trains;

  // view properties start

  @FXML
  private TableView<Sitting> sittingView;

  @FXML
  private TableColumn<Sitting, Integer> priceColumn;

  @FXML
  private TableColumn<Sitting, String> statusColumn;

  @FXML
  private TableColumn<Sitting, String> sittingTypeColumn;

  @FXML
  private TableColumn<Sitting, String> sittingIndexColumn;

  // view properties end

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    initConverters();

    try {
      // fetch result
      final var directionResult = directionFetcher.fetchData()
          .join();

      // add from result all values into directions combobox
      directions.getItems().addAll(directionResult);

      initPropertyListeners();
    } catch (MissingPropertyException | IOException e) {
      throw new RuntimeException(e);
    }

  }

  @Override
  protected String backPageName() {
    return "base-page";
  }

  private void initPropertyListeners() {
    directions.valueProperty().addListener((__, ___, value) -> {
      if (value == null) {
        return;
      }

      // get items
      final var activeItems = active.getItems();

      // cleat them
      activeItems.clear();

      // fill up with new data
      activeItems.addAll(value.actives());
    });

    active.valueProperty().addListener((__, ___, value) -> {
      // get items
      final var stationItems = endStation.getItems();

      // carriage
      final var trainsItems = trains.getItems();

      // cleat them
      stationItems.clear();
      trainsItems.clear();

      if (value == null) {
        return;
      }

      final var station = value.station();

      // do everything what we need
      truncate(station);

      trainsItems.add(value.train());
    });

    var trainsModel = trains.getSelectionModel();

    trainsModel.setSelectionMode(SelectionMode.SINGLE);
    trainsModel.selectedItemProperty().addListener((__, ___, value) -> {

      // get items
      final var carriageItems = carriages.getItems();

      // clear them
      carriageItems.clear();

      if (value == null) {
        return;
      }

      // add all of them
      carriageItems.addAll(value.carriages());
    });

    // user can select only 1 carriage at 1 time
    var carriagesModel = carriages.getSelectionModel();

    carriagesModel.setSelectionMode(SelectionMode.SINGLE);
    carriagesModel.selectedItemProperty().addListener((__, ___, value) -> {
      // get items
      final var sittingItems = sittingView.getItems();

      // clear them
      sittingItems.clear();

      if (value == null) {
        return;
      }

      // add all of them
      sittingItems.addAll(value.sittingsFiltered());
    });

    // user can select only 1 sitting at 1 time
    sittingView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

    initDisableProperty();
  }

  private void initDisableProperty() {
    // active
    active.disableProperty()
        .bind(isNull(directions.valueProperty()));

    // end station
    endStation.disableProperty()
        .bind(isNull(active.valueProperty()));

    // carriage
    carriages.disableProperty()
        .bind(isNull(endStation.valueProperty())
            .or(isNull(trains.selectionModelProperty())));

    // sitting
    sittingView.disableProperty()
        .bind(isNull(endStation.valueProperty())
            .or(isNull(carriages.selectionModelProperty()))
            .or(isNull(trains.selectionModelProperty())));

    // order ticket
    orderTicket.disableProperty()
        .bind(isNull(endStation.valueProperty())
            .or(isNull(directions.valueProperty()))
            .or(isNull(active.valueProperty()))
            .or(isNull(carriages.selectionModelProperty()))
            .or(isNull(sittingView.selectionModelProperty()))
            .or(isNull(trains.selectionModelProperty())));
  }


  private void initConverters() {
    directions.setConverter(new StringConverter<>() {
      @Override
      public String toString(Direction object) {
        return object.name();
      }

      @Override
      public Direction fromString(String string) {
        return null;
      }
    });

    active.setConverter(new StringConverter<>() {
      @Override
      public String toString(Active object) {
        return object.station().name() + ' ' + MoreFormats.formatTime(object.startDateTime());
      }

      @Override
      public Active fromString(String string) {
        return null;
      }
    });

    endStation.setConverter(new StringConverter<>() {
      @Override
      public String toString(Station object) {
        return object.name();
      }

      @Override
      public Station fromString(String string) {
        return null;
      }
    });

    trains.setCellFactory(param -> new ListCell<>() {
      @Override
      protected void updateItem(Train item, boolean empty) {
        super.updateItem(item, empty);

        if (item != null) {
          setText(item.name());
        }
      }
    });

    carriages.setCellFactory(param -> new ListCell<>() {
      @Override
      protected void updateItem(Carriage item, boolean empty) {
        super.updateItem(item, empty);

        if (item != null) {
          setText(item.index());
        }
      }
    });

    initViewColumns();
  }

  private void truncate(final @NotNull Station start) {
    // get selected direction
    final var direction = directions.getValue();

    final var from = direction.stations()
        .stream()
        // as far as everything is ordered, we can do this :)
        .dropWhile(station -> station.stationId() <= start.stationId())
        .toList();

    // get items
    final var items = endStation.getItems();

    // clear
    items.clear();

    // add all of them
    items.addAll(from);
  }

  private void initViewColumns() {
    priceColumn.setCellValueFactory(new MethodVisitorCellValueFactory<>(Sitting::price));
    sittingIndexColumn.setCellValueFactory(new MethodVisitorCellValueFactory<>(Sitting::index));
    sittingTypeColumn.setCellValueFactory(new MethodVisitorCellValueFactory<>(Sitting::sitType));
    statusColumn.setCellValueFactory(new MethodVisitorCellValueFactory<>(
        sitting -> sitting.notToken() ? "Available"
            : "Already taken" + (sitting.willBeFree() ? " (Will be free)" : "")));
  }

  @FXML
  protected void onOrderTicketClick() {
    final var selectedActive = active.getValue();

    final var trainId = selectedActive.trainId();
    final var carriageId = carriages.getSelectionModel().getSelectedItem().carriageId();
    final var activeId = selectedActive.activeId();
    final var passportId = appValues.user().passportId();
    final var sittingId = sittingView.getSelectionModel().getSelectedItem().sitId();
    final var endStationId = endStation.getValue().stationId();

    try {
      final var result = orderFetcher.fetchData(
          WebParameters.builder()
              .appendParam("trainId", trainId)
              .appendParam("carriageId", carriageId)
              .appendParam("activeId", activeId)
              .appendParam("passportId", passportId)
              .appendParam("sittingId", sittingId)
              .appendParam("endStationId", endStationId)
              .build()).join();

      final var value = result.value();

      if (value == OrderTicketResult.ALREADY_CREATED_OR_NOT_EXISTS) {
        Alerts.showError("Error while ordering!", "Looks like this sitting already ordered!");
      } else {
        Alerts.showInfo("Success", "You are successfully ordered ticket!");

        openPage("base-page");
      }

    } catch (final Throwable throwable) {
      Alerts.showException(throwable);
    }

  }

}
