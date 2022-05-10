package maxwainer.college.gui.web.implementation.auth;

import java.io.IOException;
import maxwainer.college.gui.exception.MissingPropertyException;
import maxwainer.college.gui.object.model.LoginModel;
import maxwainer.college.gui.web.enums.user.LoginResult;
import maxwainer.college.gui.web.params.WebParameters;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.jetbrains.annotations.NotNull;

public final class LoginWebFetcher extends AbstractAuthWebFetcher<LoginResult> {


  @Override
  protected @NotNull LoginResult resolveOrdinal(int ordinal) {
    return LoginResult.values()[ordinal];
  }

  @Override
  protected @NotNull Request buildRequest(@NotNull WebParameters parameters)
      throws MissingPropertyException, IOException {
    // basic credentials
    final var username = parameters.getOrThrow("username", String.class);
    final var password = parameters.getOrThrow("password", String.class);
    final var passportId = parameters.getOrThrow("passportId", int.class);

    // define request body
    final var requestBody = RequestBody.create(
        gson.toJson(new LoginModel(username, password, passportId)),
        // deserialize model (referenced to C# LoginModel class)
        MediaType.parse("application/json")); // set media type to json

    return new Request.Builder()
        .url(String.format("%s/users/User/loginUser", // set login route
            config.getOrThrow("base-url", String.class) // define base url
        ))
        .addHeader("Accept", "application/json") // set accepts
        .addHeader("Connection", "close")
        .post(requestBody) // set to post request body
        .build();
  }
}
