package maxwainer.college.gui.web.implementation.ticket;

import com.google.gson.JsonElement;
import java.io.IOException;
import maxwainer.college.gui.exception.MissingPropertyException;
import maxwainer.college.gui.object.model.OrderTicketModel;
import maxwainer.college.gui.web.enums.ticket.OrderTicketResult;
import maxwainer.college.gui.web.implementation.AbstractWebFetcher;
import maxwainer.college.gui.web.implementation.MediaTypes;
import maxwainer.college.gui.web.params.WebParameters;
import maxwainer.college.gui.web.result.EnumResult;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.jetbrains.annotations.NotNull;

public final class OrderTicketWebFetcher extends AbstractWebFetcher<EnumResult<OrderTicketResult>> {

  @Override
  protected @NotNull Request buildRequest(@NotNull WebParameters parameters)
      throws MissingPropertyException, IOException {
    final var body = RequestBody.create(
        gson.toJson(parameters.toModel(OrderTicketModel.class)),
        MediaTypes.JSON);

    return routeAuthorizedRequest("tickets/Ticket/orderTicket")
        .post(body)
        .build();
  }

  @Override
  protected @NotNull EnumResult<OrderTicketResult> convertElement(
      @NotNull JsonElement element) {
    return null;
  }
}
