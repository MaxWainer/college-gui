package maxwainer.college.gui.pages.ticket;

import static javafx.beans.binding.Bindings.isNull;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableListValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;
import maxwainer.college.gui.common.Alerts;
import maxwainer.college.gui.exception.MissingPropertyException;
import maxwainer.college.gui.object.web.Active;
import maxwainer.college.gui.object.web.Carriage;
import maxwainer.college.gui.object.web.Direction;
import maxwainer.college.gui.object.web.Sitting;
import maxwainer.college.gui.object.web.Station;
import maxwainer.college.gui.pages.AbstractSubPage;
import maxwainer.college.gui.web.enums.ticket.OrderTicketResult;
import maxwainer.college.gui.web.implementation.direction.DirectionWebFetcher;
import maxwainer.college.gui.web.implementation.ticket.OrderTicketWebFetcher;
import maxwainer.college.gui.web.params.WebParameters;
import org.jetbrains.annotations.NotNull;

public class OrderTicketController extends AbstractSubPage implements Initializable {

  @FXML
  private Button orderTicket;

  @FXML
  private ComboBox<Station> endStation;

  @FXML
  private ComboBox<Station> startStation;

  @FXML
  private ComboBox<Direction> directions;

  @FXML
  private ComboBox<Active> active;

  @FXML
  private ComboBox<Carriage> carriage;

  @FXML
  private ComboBox<Sitting> sitting;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    initConverters();

    final var optionalFetcher = webFetcherRegistry.findFetcher(DirectionWebFetcher.class);

    if (optionalFetcher.isEmpty()) {
      Alerts.showError("Error while processing!", "Fetcher is not present!");
      return;
    }

    final var directionFetcher = optionalFetcher.get();

    try {
      // fetch result
      final var directionResult = directionFetcher.fetchData()
          .join();

      // add from result all values into directions combobox
      directions.getItems()
          .addAll(directionResult.value());

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
    directions.valueProperty().addListener((collection, oldValue, newValue) -> {
      // get items
      final var startItems = startStation.getItems();

      // cleat them
      startItems.clear();

      // get all stations from new value
      final var stations = newValue
          .stations(); // from direction

      // fill up with new data
      startItems.addAll(stations);
    });

    initDisableProperty();
  }

  private void initDisableProperty() {
    // active
    active.disableProperty()
        .bind(isNull(directions.valueProperty()));

    // start station
    startStation.valueProperty()
        .addListener((collection, oldValue, newValue) -> truncate(newValue));
    startStation.disableProperty()
        .bind(isNull(active.valueProperty()));

    // end station
    endStation.disableProperty()
        .bind(isNull(startStation.valueProperty())
            .or(isNull(active.valueProperty())));

    // carriage
    carriage.disableProperty()
        .bind(isNull(startStation.valueProperty())
            .or(isNull(endStation.valueProperty())));

    // sitting
    sitting.disableProperty()
        .bind(isNull(startStation.valueProperty())
            .or(isNull(endStation.valueProperty()))
            .or(isNull(carriage.valueProperty())));

    // order ticket
    orderTicket.disableProperty()
        .bind(isNull(startStation.valueProperty())
            .or(isNull(endStation.valueProperty()))
            .or(isNull(directions.valueProperty()))
            .or(isNull(active.valueProperty()))
            .or(isNull(carriage.valueProperty()))
            .or(isNull(sitting.valueProperty())));
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

    startStation.setConverter(new StringConverter<>() {
      @Override
      public String toString(Station object) {
        return object.name();
      }

      @Override
      public Station fromString(String string) {
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
  }

  private void truncate(final @NotNull Station start) {
    final var direction = directions.getValue();

    final var from = direction.stations()
        .stream()
        .dropWhile(station -> station.stationId() <= start.stationId())
        .toList();

    final var items = endStation.getItems();

    items.clear();
    items.addAll(from);
  }

  // c# class:
  // public class OrderModel
  //{
  //    [Required] public int TrainId { get; set; }
  //    [Required] public int CarriageId { get; set; }
  //    [Required] public int ActiveId { get; set; }
  //    [Required] public int PassportId { get; set; }
  //    [Required] public int SittingId { get; set; }
  //    [Required] public int EndStationId { get; set; }
  //}
  @FXML
  protected void onOrderTicketClick() {
    final var active = this.active.getValue();

    final var trainId = active.trainId();
    final var carriageId = carriage.getValue().carriageId();
    final var activeId = active.activeId();
    final var passportId = appValues.user().passportId();
    final var sittingId = sitting.getValue().sitId();
    final var endStationId = endStation.getValue().stationId();

    final var optionalFetcher = webFetcherRegistry.findFetcher(OrderTicketWebFetcher.class);

    if (optionalFetcher.isEmpty()) {
      return;
    }

    final var fetcher = optionalFetcher.get();

    try {
      final var result = fetcher.fetchData(
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
