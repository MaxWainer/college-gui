package maxwainer.college.gui.web.implementation.auth;

import java.io.IOException;
import maxwainer.college.gui.exception.MissingPropertyException;
import maxwainer.college.gui.object.model.LoginModel;
import maxwainer.college.gui.web.enums.user.TokenResult;
import maxwainer.college.gui.web.implementation.MediaTypes;
import maxwainer.college.gui.web.params.WebParameters;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.jetbrains.annotations.NotNull;

public final class LoginWebFetcher extends AbstractAuthWebFetcher<TokenResult> {


  @Override
  protected @NotNull TokenResult resolveOrdinal(int ordinal) {
    return TokenResult.values()[ordinal];
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
        MediaTypes.JSON); // set media type to json

    return routeRequest("users/User/loginUser")
        .post(requestBody) // set to post request body
        .build();
  }
}
