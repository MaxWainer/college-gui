package maxwainer.college.gui.web.implementation.auth;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.inject.Inject;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import maxwainer.college.gui.config.AppConfig;
import maxwainer.college.gui.exception.MissingPropertyException;
import maxwainer.college.gui.web.WebFetcher;
import maxwainer.college.gui.web.params.WebParameters;
import maxwainer.college.gui.web.result.EnumResult;
import maxwainer.college.gui.web.result.Result;
import maxwainer.college.gui.web.result.StringResult;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;

abstract class AbstractAuthWebFetcher<T extends Enum<T>> implements WebFetcher<Result<?>> {

  @Inject
  protected OkHttpClient client;

  @Inject
  protected Gson gson;

  @Inject
  protected AppConfig config;

  @Override
  public @NotNull CompletableFuture<Result<?>> fetchData(@NotNull WebParameters parameters)
      throws MissingPropertyException, IOException {
    final var request = buildRequest(parameters);

    return CompletableFuture.supplyAsync(() -> {
      try {
        final var call = client.newCall(request); // create new call

        try (final var response = call.execute()) { // execute it
          final var body = response.body(); //get body

          if (body == null) { // check body
            return Result.invalidResult("Body is null!");
          }

          final var rawJson = body.string(); // get raw json
          final var jsonObject = JsonParser.parseString(rawJson)
              .getAsJsonObject(); // serialize to json object
          final var result = jsonObject.getAsJsonPrimitive("result"); // get result as primitive

          if (result.isNumber()) { // if it's number we have some login result (not token)
            return new EnumResult<>(resolveOrdinal(result.getAsInt()));
          }

          // else we get our auth token!
          return new StringResult(result.getAsString());
        }
      } catch (IOException e) {
        throw new CompletionException(e);
      }
    });
  }

  protected abstract @NotNull T resolveOrdinal(final int ordinal);

  protected abstract @NotNull Request buildRequest(final @NotNull WebParameters parameters)
      throws MissingPropertyException, IOException;
}
