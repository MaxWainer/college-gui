package maxwainer.college.gui.pages.ticket;

import com.google.inject.Inject;
import java.io.IOException;
import java.util.Collection;
import javafx.fxml.FXML;
import maxwainer.college.gui.common.Alerts;
import maxwainer.college.gui.exception.MissingPropertyException;
import maxwainer.college.gui.object.web.Ticket;
import maxwainer.college.gui.web.enums.ticket.DeleteTicketResult;
import maxwainer.college.gui.web.implementation.ticket.MultiTicketRemoveWebFetcher;
import maxwainer.college.gui.web.implementation.ticket.TicketListWebFetcher;
import maxwainer.college.gui.web.params.WebParameters;
import org.jetbrains.annotations.NotNull;

public class ListTicketsController extends AbstractTicketListPage {

  @Inject
  private TicketListWebFetcher ticketListWebFetcher;

  @Override
  protected void initialize0() {

  }

  @Override
  protected @NotNull Collection<Ticket> listTickets() throws MissingPropertyException, IOException {
    return ticketListWebFetcher.fetchData(
        WebParameters.builder()
            .appendParam("passportId", appValues.user().passportId())
            .build()
    ).join();

  }

  @Override
  protected String backPageName() {
    return "base-page";
  }

  @FXML
  public void onRemoveOrder() {
    handleRemoveSelected();
  }
}
