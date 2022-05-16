package maxwainer.college.gui.web.implementation.ticket;

import com.google.gson.JsonElement;
import java.io.IOException;
import maxwainer.college.gui.exception.MissingPropertyException;
import maxwainer.college.gui.object.model.MultiTicketDeleteModel;
import maxwainer.college.gui.web.enums.ticket.DeleteTicketResult;
import maxwainer.college.gui.web.implementation.AbstractWebFetcher;
import maxwainer.college.gui.web.implementation.MediaTypes;
import maxwainer.college.gui.web.params.WebParameters;
import maxwainer.college.gui.web.result.EnumResult;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.jetbrains.annotations.NotNull;

public final class MultiTicketRemoveWebFetcher extends
    AbstractWebFetcher<EnumResult<DeleteTicketResult>> {

  @Override
  protected @NotNull Request buildRequest(@NotNull WebParameters parameters)
      throws MissingPropertyException, IOException {
    final var body = RequestBody.create(
        gson.toJson(parameters.toModel(MultiTicketDeleteModel.class)),
        MediaTypes.JSON
    );

    return makeAuthorizedRequest("ticket/Ticket/multiDelete")
        .post(body)
        .build();
  }

  @Override
  protected @NotNull EnumResult<DeleteTicketResult> convertElement(
      @NotNull JsonElement element) {
    final var result = element
        .getAsJsonObject()
        .get("result"); // get result as primitive

    return new EnumResult<>(DeleteTicketResult.values()[result.getAsInt()]);
  }
}
