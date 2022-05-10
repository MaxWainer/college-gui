package maxwainer.college.gui.web.result;

import org.jetbrains.annotations.NotNull;

public final class StringResult implements Result<String> {

  private final String value;

  public StringResult(final @NotNull String value) {
    this.value = value;
  }

  @Override
  public @NotNull String value() {
    return value;
  }
}
