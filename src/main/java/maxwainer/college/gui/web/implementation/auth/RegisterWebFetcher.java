package maxwainer.college.gui.web.implementation.auth;

import java.io.IOException;
import maxwainer.college.gui.exception.MissingPropertyException;
import maxwainer.college.gui.object.model.RegisterModel;
import maxwainer.college.gui.web.enums.user.RegisterResult;
import maxwainer.college.gui.web.params.WebParameters;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.jetbrains.annotations.NotNull;

public final class RegisterWebFetcher extends AbstractAuthWebFetcher<RegisterResult> {


  @Override
  protected @NotNull RegisterResult resolveOrdinal(int ordinal) {
    return RegisterResult.values()[ordinal];
  }

  @Override
  protected @NotNull Request buildRequest(@NotNull WebParameters parameters)
      throws MissingPropertyException, IOException {
    // basic credentials
    final var firstName = parameters.getOrThrow("firstName", String.class);
    final var secondName = parameters.getOrThrow("secondName", String.class);
    final var patronymic = parameters.getOrThrow("patronymic", String.class);
    final var username = parameters.getOrThrow("username", String.class);
    final var password = parameters.getOrThrow("password", String.class);
    final var passportId = parameters.getOrThrow("passportId", int.class);

    // define request body
    final var requestBody = RequestBody.create(
        gson.toJson(
            new RegisterModel(firstName, secondName, patronymic, username, password, passportId)),
        // deserialize model (referenced to C# LoginModel class)
        MediaType.parse("application/json")); // set media type to json

    return routeRequest("users/User/registerUser")
        .post(requestBody) // set to post request body
        .build();
  }
}
