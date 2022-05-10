package maxwainer.college.gui.web.result;

import org.jetbrains.annotations.NotNull;

public final class ObjectResult<T> implements Result<T> {

  private final T object;

  public ObjectResult(final @NotNull T object) {
    this.object = object;
  }

  @Override
  public @NotNull T value() {
    return this.object;
  }
}
