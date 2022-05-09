package maxwainer.college.gui.web.implementation;

import com.google.inject.Inject;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import maxwainer.college.gui.config.AppConfig;
import maxwainer.college.gui.values.AppValues;
import maxwainer.college.gui.web.WebResult;
import maxwainer.college.gui.web.WebFetcher;
import maxwainer.college.gui.exception.MissingPropertyException;
import maxwainer.college.gui.web.WebResult.InternalCodes;
import maxwainer.college.gui.web.params.WebParameters;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

public final class AuthWebFetcher implements WebFetcher<WebResult<String>> {


  @Inject
  private OkHttpClient client;

  @Inject
  private AppConfig config;

  @Inject
  private AppValues values;

  @Override
  public @NotNull CompletableFuture<WebResult<String>> fetchData(
      @NotNull WebParameters parameters) throws MissingPropertyException, IOException {
    final String username = parameters.getOrThrow("username", String.class);
    final String password = parameters.getOrThrow("password", String.class);
    final Request request = new Request.Builder()
        .url(String.format("%s/auth",
            config.getOrThrow("base-url", String.class)
        ))
        .addHeader("Authorization", "")
        .addHeader("Content-type", "")
        .addHeader("Accept", "application/json")
        .build();

    return CompletableFuture.supplyAsync(() -> {
      try {
        final Call call = client.newCall(request);

        try (final Response response = call.execute()) {
          final String rawMessage = response.message();

          if (rawMessage.equals("success")) {

            return new WebResult<>(InternalCodes.VALID,
                Objects.requireNonNull(response.header("Token")));
          }

          return new WebResult<>(InternalCodes.INVALID, "Invalid!");
        }
      } catch (IOException e) {
        throw new CompletionException(e);
      }
    });
  }
}
