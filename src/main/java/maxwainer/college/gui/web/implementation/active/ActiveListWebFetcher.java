package maxwainer.college.gui.web.implementation.active;

import com.google.gson.JsonElement;
import java.io.IOException;
import java.util.ArrayList;
import maxwainer.college.gui.exception.MissingPropertyException;
import maxwainer.college.gui.object.web.Active;
import maxwainer.college.gui.web.implementation.AbstractWebFetcher;
import maxwainer.college.gui.web.params.WebParameters;
import maxwainer.college.gui.web.result.ObjectListResult;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;

public final class ActiveListWebFetcher extends AbstractWebFetcher<ObjectListResult<Active>> {

  @Override
  protected @NotNull Request buildRequest(@NotNull WebParameters parameters)
      throws MissingPropertyException, IOException {
    return makeAuthorizedRequest("active/Active/list")
        .get()
        .build();
  }

  @Override
  protected @NotNull ObjectListResult<Active> convertElement(@NotNull JsonElement element) {
    final var array = element.getAsJsonArray();

    final var actives = new ArrayList<Active>();
    for (final JsonElement jsonElement : array) {
      final var object = jsonElement.getAsJsonObject();

      actives.add(gson.fromJson(object, Active.class));
    }

    return new ObjectListResult<>(actives);
  }
}
