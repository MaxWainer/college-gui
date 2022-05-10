package maxwainer.college.gui.web.result;

import org.jetbrains.annotations.NotNull;

public final class EnumResult<T extends Enum<T>> implements Result<T> {

  private final T value;

  public EnumResult(final @NotNull T value) {
    this.value = value;
  }

  @Override
  public @NotNull T value() {
    return value;
  }
}
