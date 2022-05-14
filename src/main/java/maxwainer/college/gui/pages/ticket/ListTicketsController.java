package maxwainer.college.gui.pages.ticket;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import maxwainer.college.gui.common.Alerts;
import maxwainer.college.gui.exception.MissingPropertyException;
import maxwainer.college.gui.object.web.Ticket;
import maxwainer.college.gui.pages.AbstractSubPage;
import maxwainer.college.gui.web.implementation.ticket.TicketListWebFetcher;
import maxwainer.college.gui.web.params.WebParameters;
import org.jetbrains.annotations.NotNull;

public class ListTicketsController extends AbstractSubPage implements Initializable {

  @FXML
  private ListView<Ticket> ticketList;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    final var optionalFetcher = webFetcherRegistry.findFetcher(TicketListWebFetcher.class);

    if (optionalFetcher.isEmpty()) {
      return;
    }

    ticketList.setCellFactory(TicketCellFactory.INSTANCE);

    final var fetcher = optionalFetcher.get();

    try {
      final var result = fetcher.fetchData(
          WebParameters.builder()
              .appendParam("passportId", appValues.user().passportId())
              .build()
      ).join();

      for (final var ticket : result) {
        ticketList.getItems().add(ticket);
      }
    } catch (MissingPropertyException | IOException e) {
      Alerts.showException(e);
    }
  }

  @Override
  protected String backPageName() {
    return "base-page";
  }

  private static final class TicketCellFactory implements
      Callback<ListView<Ticket>, ListCell<Ticket>> {

    static final TicketCellFactory INSTANCE = new TicketCellFactory();

    private TicketCellFactory() {
    }

    @Override
    public ListCell<Ticket> call(ListView<Ticket> param) {
      return new ListCell<>() {
        @Override
        protected void updateItem(Ticket item, boolean empty) {
          super.updateItem(item, empty);

          if (!empty || item != null) {
            setText(formatTicket(item));
          }
        }
      };
    }

    private static String formatTicket(final @NotNull Ticket item) {
      final var direction = item.relatedDirection();
      final var endStation = item.endStation();
      final var date = item.startDate();
      final var sitting = item.sitting();
      final var carriage = sitting.relatedCarriage();

      return direction.name()
          + ' '
          + endStation.name()
          + " (" + date.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + ") "
          + "(Поезд: " + carriage.relatedTrain().name()
          + " / Вагон: " + carriage.index()
          + " / Место: "
          + sitting.index() + " [" + sitting.sitType() + "]"
          + ")";
    }
  }
}
