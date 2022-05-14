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
import maxwainer.college.gui.object.web.Direction;
import maxwainer.college.gui.object.web.Station;
import maxwainer.college.gui.pages.AbstractSubPage;
import maxwainer.college.gui.web.implementation.direction.DirectionWebFetcher;
import org.jetbrains.annotations.NotNull;

public class OrderTicketController extends AbstractSubPage implements Initializable {

  @FXML
  public Button orderTicket;

  @FXML
  public ComboBox<Station> endStation;
  @FXML

  public ComboBox<Station> startStation;

  @FXML
  public ComboBox<Direction> directions;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    initConverters();

    final var optionalFetcher = webFetcherRegistry.findFetcher(DirectionWebFetcher.class);

    if (optionalFetcher.isEmpty()) {
      Alerts.showError("Error while processing!", "Fetcher is not present!");
      return;
    }

    final var fetcher = optionalFetcher.get();

    try {
      // fetch result
      final var result = fetcher.fetchData()
          .join();

      // add from result all values into directions combobox
      directions.getItems()
          .addAll(result.value());

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

    startStation.valueProperty()
        .addListener((collection, oldValue, newValue) -> truncate(newValue));

    endStation.disableProperty()
        .bind(isNull(startStation.valueProperty()));

    orderTicket.disableProperty()
        .bind(isNull(startStation.valueProperty())
            .or(isNull(endStation.valueProperty()))
            .or(isNull(directions.valueProperty()))
        );
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

  @FXML
  protected void onOrderTicketClick() {


  }
}
