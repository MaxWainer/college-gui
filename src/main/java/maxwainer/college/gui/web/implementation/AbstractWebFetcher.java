package maxwainer.college.gui.web.implementation;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.inject.Inject;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import maxwainer.college.gui.common.AppLogger;
import maxwainer.college.gui.config.AppConfig;
import maxwainer.college.gui.exception.MissingPropertyException;
import maxwainer.college.gui.values.AppValues;
import maxwainer.college.gui.web.WebFetcher;
import maxwainer.college.gui.web.params.WebParameters;
import maxwainer.college.gui.web.result.Result;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractWebFetcher<T extends Result> implements WebFetcher<T> {

  @Inject
  protected OkHttpClient client;

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
              final var jsonElement = JsonParser.parseString(rawJson);

              AppLogger.LOGGER.info(() -> "Received body: " + rawJson);

              return convertElement(jsonElement);
            }
          } catch (IOException e) {
            throw new CompletionException(e);
          }
        });
  }

  protected abstract @NotNull Request buildRequest(final @NotNull WebParameters parameters)
      throws MissingPropertyException, IOException;

  protected abstract @NotNull T convertElement(final @NotNull JsonElement element);

  protected Request.Builder routeRequest(final @NotNull String subPath)
      throws MissingPropertyException {
    final var url = String.format("%s/%s", // set login route
        config.getOrThrow("base-url", String.class), // define base url
        subPath
    );

    AppLogger.LOGGER.info(() -> "Making request to " + url);

    return new Request.Builder()
        .url(url)
        .addHeader("Accept", "application/json") // set accepts
        .addHeader("Connection", "close");
  }
}
