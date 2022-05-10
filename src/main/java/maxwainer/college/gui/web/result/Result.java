package maxwainer.college.gui.web.result;

import org.jetbrains.annotations.NotNull;

public interface Result<T> {

  static Result<String> invalidResult(final @NotNull String errorMessage) {
    return new InvalidResult(errorMessage);
  }

  @NotNull T value();

  default boolean invalid() {
    return this instanceof InvalidResult;
  }
}
