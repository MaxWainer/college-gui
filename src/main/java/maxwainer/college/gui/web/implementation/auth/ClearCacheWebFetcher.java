package maxwainer.college.gui.web.implementation.auth;

import java.io.IOException;
import maxwainer.college.gui.exception.MissingPropertyException;
import maxwainer.college.gui.object.model.ClearCacheModel;
import maxwainer.college.gui.web.enums.user.ClearCacheResult;
import maxwainer.college.gui.web.implementation.MediaTypes;
import maxwainer.college.gui.web.params.WebParameters;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.jetbrains.annotations.NotNull;

public final class ClearCacheWebFetcher extends AbstractAuthWebFetcher<ClearCacheResult> {

  @Override
  protected @NotNull Request buildRequest(@NotNull WebParameters parameters)
      throws MissingPropertyException, IOException {
    final var body = RequestBody.create(
        gson.toJson(
            new ClearCacheModel(values.accessToken())),
        MediaTypes.JSON);

    return makeRequest("users/User/clearCache")
        .post(body)
        .addHeader("Authorization", "bearer " + values.accessToken())
        .build();
  }

  @Override
  protected @NotNull ClearCacheResult resolveOrdinal(int ordinal) {
    return ClearCacheResult.values()[ordinal];
  }

}
