package maxwainer.college.gui.pages.ticket;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;
import maxwainer.college.gui.common.Alerts;
import maxwainer.college.gui.exception.MissingPropertyException;
import maxwainer.college.gui.object.web.Active;
import maxwainer.college.gui.object.web.Station;
import maxwainer.college.gui.pages.AbstractSubPage;
import maxwainer.college.gui.web.implementation.active.ActiveListWebFetcher;

public class OrderTicketController extends AbstractSubPage implements Initializable {

  @FXML
  public Button orderTicket;

  @FXML
  public ComboBox<Station> endStation;
  @FXML

  public ComboBox<Station> startStation;

  @FXML
  public ComboBox<Active> activeDirection;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    activeDirection.setConverter(new StringConverter<Active>() {
      @Override
      public String toString(Active object) {
        return object.mainDirection().name();
      }

      @Override
      public Active fromString(String string) {
        return null;
      }
    });

    final var optionalFetcher = webFetcherRegistry.findFetcher(ActiveListWebFetcher.class);

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
      activeDirection.getItems()
          .addAll(result.value());

      activeDirection.setOnAction(event -> {
        final var newValue = activeDirection.getValue();

        // get items
        final var startItems = startStation.getItems();
        final var endItems = endStation.getItems();

        // cleat them
        startItems.clear();
        endItems.clear();

        // get all stations from new value
        final var stations = newValue
            .mainDirection()
            .stations(); // from direction

        // fill up with new data
        startItems.addAll(stations);
        endItems.addAll(stations);
      });
    } catch (MissingPropertyException | IOException e) {
      throw new RuntimeException(e);
    }

  }
}
