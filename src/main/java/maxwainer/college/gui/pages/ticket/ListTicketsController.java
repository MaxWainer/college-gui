package maxwainer.college.gui.pages.ticket;

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
import maxwainer.college.gui.object.web.Ticket;
import maxwainer.college.gui.pages.AbstractSubPage;
import maxwainer.college.gui.web.implementation.ticket.MultiTicketRemoveWebFetcher;
import maxwainer.college.gui.web.implementation.ticket.TicketListWebFetcher;
import maxwainer.college.gui.web.params.WebParameters;

public class ListTicketsController extends AbstractSubPage implements Initializable {

  @Inject
  private TicketListWebFetcher ticketListWebFetcher;

  @Inject
  private MultiTicketRemoveWebFetcher multiTicketRemoveWebFetcher;

  // table view
  // start

  @FXML
  private TableView<Ticket> ticketList;

  @FXML
  private TableColumn<Ticket, String> sittingIndexColumn;

  @FXML
  private TableColumn<Ticket, String> sittingTypeColumn;

  @FXML
  private TableColumn<Ticket, String> carriageColumn;

  @FXML
  private TableColumn<Ticket, String> trainColumn;

  @FXML
  private TableColumn<Ticket, String> endStationColumn;

  @FXML
  private TableColumn<Ticket, String> timeColumn;

  @FXML
  private TableColumn<Ticket, String> directionColumn;

  // end

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    initColumns();

    try {
      final var result = ticketListWebFetcher.fetchData(
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

  private void initColumns() {
    sittingIndexColumn.setCellValueFactory(
        new MethodVisitorCellValueFactory<>(ticket -> ticket.sitting().index()));

    sittingTypeColumn.setCellValueFactory(
        new MethodVisitorCellValueFactory<>(ticket -> ticket.sitting().sitType()));

    carriageColumn.setCellValueFactory(
        new MethodVisitorCellValueFactory<>(ticket -> ticket.sitting().relatedCarriage().index()));

    trainColumn.setCellValueFactory(
        new MethodVisitorCellValueFactory<>(ticket -> ticket.relatedActive().train().name()));

    endStationColumn.setCellValueFactory(
        new MethodVisitorCellValueFactory<>(ticket -> ticket.endStation().name()));

    timeColumn.setCellValueFactory(
        new MethodVisitorCellValueFactory<>(
            ticket -> MoreFormats.formatTime(ticket.relatedActive().startDateTime())));

    directionColumn.setCellValueFactory(
        new MethodVisitorCellValueFactory<>(ticket -> ticket.relatedDirection().name()));
  }

  @FXML
  public void onRemoveOrder() {
    final var items = ticketList.getSelectionModel().getSelectedItems();

    final var rawItems = items.stream()
        .map(Ticket::ticketId)
        .toArray(Integer[]::new);

    try {
      final var result = multiTicketRemoveWebFetcher.fetchData(WebParameters.builder()
          .appendParam("ticketIds", rawItems)
          .build());


    } catch (MissingPropertyException | IOException e) {
      Alerts.showException(e);
    }

  }
}
