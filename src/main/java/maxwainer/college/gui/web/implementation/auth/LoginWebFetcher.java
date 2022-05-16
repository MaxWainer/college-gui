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

    // define request body
    final var requestBody = RequestBody.create(
        gson.toJson(parameters.toModel(LoginModel.class)),
        // deserialize model (referenced to C# LoginModel class)
        MediaTypes.JSON); // set media type to json

    return makeRequest("users/User/loginUser")
        .post(requestBody) // set to post request body
        .build();
  }
}
