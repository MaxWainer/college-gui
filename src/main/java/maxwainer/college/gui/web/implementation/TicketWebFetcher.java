package maxwainer.college.gui.web.implementation;

import com.google.inject.Inject;
import java.util.concurrent.CompletableFuture;
import maxwainer.college.gui.object.web.Ticket;
import maxwainer.college.gui.web.WebFetcher;
import maxwainer.college.gui.exception.MissingPropertyException;
import maxwainer.college.gui.web.params.WebParameters;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;

public final class TicketWebFetcher implements WebFetcher<Ticket> {

  @Inject
  private OkHttpClient client;

  @Override
  public @NotNull CompletableFuture<Ticket> fetchData(@NotNull WebParameters parameters)
      throws MissingPropertyException {
    return null;
  }
}
