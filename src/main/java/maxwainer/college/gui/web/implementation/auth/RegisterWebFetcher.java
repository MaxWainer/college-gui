package maxwainer.college.gui.web.implementation.auth;

import java.io.IOException;
import maxwainer.college.gui.exception.MissingPropertyException;
import maxwainer.college.gui.object.model.RegisterModel;
import maxwainer.college.gui.web.enums.user.RegisterResult;
import maxwainer.college.gui.web.implementation.MediaTypes;
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

    // define request body
    final var requestBody = RequestBody.create(
        gson.toJson(parameters.toModel(RegisterModel.class)),
        // deserialize model (referenced to C# LoginModel class)
        MediaTypes.JSON); // set media type to json

    return routeRequest("users/User/registerUser")
        .post(requestBody) // set to post request body
        .build();
  }
}
