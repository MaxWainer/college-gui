package maxwainer.college.gui.web.implementation.auth;

import com.google.gson.JsonElement;
import maxwainer.college.gui.common.tuple.Tuples;
import maxwainer.college.gui.object.web.User;
import maxwainer.college.gui.web.implementation.AbstractWebFetcher;
import maxwainer.college.gui.web.result.EnumResult;
import maxwainer.college.gui.web.result.ObjectResult;
import maxwainer.college.gui.web.result.Result;
import org.jetbrains.annotations.NotNull;

public abstract sealed class AbstractAuthWebFetcher<T extends Enum<T>> extends
    AbstractWebFetcher<Result<?>>
    permits
    ClearCacheWebFetcher,
    LoginWebFetcher,
    RegisterWebFetcher {

  protected abstract @NotNull T resolveOrdinal(final int ordinal);

  @Override
  protected @NotNull Result<?> convertElement(@NotNull JsonElement element) {
    final var result = element
        .getAsJsonObject()
        .get("result"); // get result as primitive

    if (result.isJsonPrimitive()) { // if it's number we have some login result (not token)
      return new EnumResult<>(resolveOrdinal(result.getAsInt()));
    }

    final var object = result.getAsJsonObject();

    // else we get our auth token!
    return new ObjectResult<>(
        Tuples.of(
            object.getAsJsonPrimitive("token").getAsString(),
            gson.fromJson(object.getAsJsonObject("user"), User.class))
    );
  }
}
