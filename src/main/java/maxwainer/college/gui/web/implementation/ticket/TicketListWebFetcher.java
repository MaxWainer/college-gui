package maxwainer.college.gui.web.implementation.ticket;

import com.google.gson.JsonElement;
import java.io.IOException;
import maxwainer.college.gui.exception.MissingPropertyException;
import maxwainer.college.gui.object.web.Ticket;
import maxwainer.college.gui.web.implementation.AbstractWebFetcher;
import maxwainer.college.gui.web.params.WebParameters;
import maxwainer.college.gui.web.result.ObjectListResult;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;

public final class TicketListWebFetcher extends AbstractWebFetcher<ObjectListResult<Ticket>> {

  @Override
  protected @NotNull Request buildRequest(@NotNull WebParameters parameters)
      throws MissingPropertyException, IOException {
    return null;
  }

  @Override
  protected @NotNull ObjectListResult<Ticket> convertElement(@NotNull JsonElement element) {
    return null;
  }
}
