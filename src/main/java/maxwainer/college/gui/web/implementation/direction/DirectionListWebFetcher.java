package maxwainer.college.gui.web.implementation.direction;

import com.google.gson.JsonElement;
import java.io.IOException;
import java.util.ArrayList;
import maxwainer.college.gui.exception.MissingPropertyException;
import maxwainer.college.gui.object.web.Direction;
import maxwainer.college.gui.web.implementation.AbstractWebFetcher;
import maxwainer.college.gui.web.params.WebParameters;
import maxwainer.college.gui.web.result.ObjectListResult;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;

public final class DirectionListWebFetcher extends AbstractWebFetcher<ObjectListResult<Direction>> {

  @Override
  protected @NotNull Request buildRequest(@NotNull WebParameters parameters)
      throws MissingPropertyException, IOException {
    return makeAuthorizedRequest("direction/Direction/list")
        .build();
  }

  @Override
  protected @NotNull ObjectListResult<Direction> convertElement(
      @NotNull JsonElement element) {
    final var array = element.getAsJsonArray();

    final var directions = new ArrayList<Direction>();

    for (final var jsonElement : array) {
      directions.add(gson.fromJson(jsonElement, Direction.class));
    }

    return new ObjectListResult<>(directions);
  }
}
