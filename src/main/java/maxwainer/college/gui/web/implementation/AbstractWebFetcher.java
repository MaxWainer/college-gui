package maxwainer.college.gui.web.implementation;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.inject.Inject;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ForkJoinPool;
import maxwainer.college.gui.common.AppLogger;
import maxwainer.college.gui.config.AppConfig;
import maxwainer.college.gui.exception.MissingPropertyException;
import maxwainer.college.gui.values.AppValues;
import maxwainer.college.gui.web.WebFetcher;
import maxwainer.college.gui.web.implementation.active.ActiveListWebFetcher;
import maxwainer.college.gui.web.implementation.auth.AbstractAuthWebFetcher;
import maxwainer.college.gui.web.implementation.direction.DirectionListWebFetcher;
import maxwainer.college.gui.web.implementation.ticket.MultiTicketRemoveWebFetcher;
import maxwainer.college.gui.web.implementation.ticket.OrderTicketWebFetcher;
import maxwainer.college.gui.web.implementation.ticket.TicketListWebFetcher;
import maxwainer.college.gui.web.implementation.user.UserListWebFetcher;
import maxwainer.college.gui.web.params.WebParameters;
import maxwainer.college.gui.web.result.ObjectListResult;
import maxwainer.college.gui.web.result.Result;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;

public abstract sealed class AbstractWebFetcher<T extends Result> implements WebFetcher<T> permits
    ActiveListWebFetcher, TicketListWebFetcher, AbstractAuthWebFetcher, DirectionListWebFetcher,
    OrderTicketWebFetcher, MultiTicketRemoveWebFetcher, UserListWebFetcher {

  @Inject
  protected OkHttpClient client;

  @Inject
  protected ForkJoinPool dataFetcherPool;

  @Inject
  protected Gson gson;

  @Inject
  protected AppConfig config;

  @Inject
  protected AppValues values;

  @Override
  @SuppressWarnings("unchecked")
  public @NotNull CompletableFuture<T> fetchData(@NotNull WebParameters parameters)
      throws MissingPropertyException, IOException {
    final var request = buildRequest(parameters);

    return (CompletableFuture<T>) CompletableFuture.supplyAsync(
        () -> {
          try {
            final var call = client.newCall(request); // create new call

            try (final var response = call.execute()) { // execute it
              final var body = response.body(); //get body

              if (body == null) { // check body
                AppLogger.LOGGER.warning(() -> "Received body is null!");
                return Result.invalidResult("Body is null!");
              }

              final var rawJson = body.string(); // get raw json

              AppLogger.LOGGER.info(() -> "Received body: " + rawJson);

              final var jsonElement = gson.fromJson(rawJson, JsonElement.class);

              return convertElement(jsonElement);
            }
          } catch (IOException e) {
            throw new CompletionException(e);
          }
        }, dataFetcherPool);
  }

  protected abstract @NotNull Request buildRequest(final @NotNull WebParameters parameters)
      throws MissingPropertyException, IOException;

  protected abstract @NotNull T convertElement(final @NotNull JsonElement element);

  protected Request.Builder makeRequest(final @NotNull String subPath)
      throws MissingPropertyException {
    final var url = String.format("%s/%s", // set login route
        config.baseUrl(), // define base url
        subPath
    );

    AppLogger.LOGGER.info(() -> "Making request to " + url);

    return new Request.Builder()
        .url(url)
        .addHeader("Accept", "application/json") // set accepts
        .addHeader("Connection", "close");
  }

  protected Request.Builder makeAuthorizedRequest(final @NotNull String subPath)
      throws MissingPropertyException {
    return makeRequest(subPath)
        .addHeader("Authorization", "bearer " + values.accessToken());
  }
}
