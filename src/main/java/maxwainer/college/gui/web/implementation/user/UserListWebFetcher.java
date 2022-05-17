package maxwainer.college.gui.web.implementation.user;

import com.google.gson.JsonElement;
import java.io.IOException;
import java.util.ArrayList;
import maxwainer.college.gui.exception.MissingPropertyException;
import maxwainer.college.gui.object.web.User;
import maxwainer.college.gui.web.implementation.AbstractWebFetcher;
import maxwainer.college.gui.web.params.WebParameters;
import maxwainer.college.gui.web.result.ObjectListResult;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;

public final class UserListWebFetcher extends AbstractWebFetcher<ObjectListResult<User>> {

  @Override
  protected @NotNull Request buildRequest(@NotNull WebParameters parameters)
      throws MissingPropertyException, IOException {
    return makeAuthorizedRequest("user/User/list")
        .get()
        .build();
  }

  @Override
  protected @NotNull ObjectListResult<User> convertElement(@NotNull JsonElement element) {
    final var array = element.getAsJsonArray();

    final var list = new ArrayList<User>();
    for (final var jsonElement : array) {
      list.add(gson.fromJson(jsonElement, User.class));
    }

    return new ObjectListResult<>(list);
  }
}
