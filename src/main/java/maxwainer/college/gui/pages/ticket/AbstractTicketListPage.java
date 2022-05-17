package maxwainer.college.gui.pages.ticket;

import com.google.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.List;
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
import maxwainer.college.gui.pages.AbstractBasicSubPage;
import maxwainer.college.gui.web.enums.ticket.DeleteTicketResult;
import maxwainer.college.gui.web.implementation.ticket.MultiTicketRemoveWebFetcher;
import maxwainer.college.gui.web.params.WebParameters;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractTicketListPage extends AbstractBasicSubPage implements Initializable {


  @Inject
  private MultiTicketRemoveWebFetcher multiTicketRemoveWebFetcher;

  @FXML
  protected TableView<Ticket> ticketList;

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

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    initColumns();

    try {
      ticketList.getItems().addAll(listTickets());
    } catch (MissingPropertyException | IOException e) {
      Alerts.showException(e);
    }

    initialize0();
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

  protected void handleRemoveSelected() {
    final var items = ticketList.getSelectionModel().getSelectedItems();

    final var rawItems = items.stream()
        .map(Ticket::ticketId)
        .toArray(Integer[]::new);

    try {
      final var result = multiTicketRemoveWebFetcher.fetchData(WebParameters.builder()
              .appendParam("ticketIds", rawItems)
              .build())
          .join();

      final var value = result.value();

      if (value == DeleteTicketResult.SUCCESS) {
        Alerts.showInfo("Success!", "Successfully removed " + rawItems.length + " ticket(-s)");
      } else {
        Alerts.showError("Error!", "Some internal error acquired!");
      }
    } catch (MissingPropertyException | IOException e) {
      Alerts.showException(e);
    }
  }

  protected abstract void initialize0();

  protected abstract @NotNull Collection<Ticket> listTickets()
      throws MissingPropertyException, IOException;
}
