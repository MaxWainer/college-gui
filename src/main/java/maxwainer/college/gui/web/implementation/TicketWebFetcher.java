package maxwainer.college.gui.web.implementation;

import com.google.inject.Inject;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import maxwainer.college.gui.object.web.Ticket;
import maxwainer.college.gui.web.WebFetcher;
import maxwainer.college.gui.exception.MissingPropertyException;
import maxwainer.college.gui.web.params.WebParameters;
import maxwainer.college.gui.web.result.ObjectResult;
import maxwainer.college.gui.web.result.Result;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;

public final class TicketWebFetcher implements WebFetcher<ObjectResult<Ticket>> {

  @Inject
  private OkHttpClient client;

  @Override
  public @NotNull CompletableFuture<ObjectResult<Ticket>> fetchData(
      @NotNull WebParameters parameters) throws MissingPropertyException, IOException {
    return null;
  }
}
