package maxwainer.college.gui.web.result;

import org.jetbrains.annotations.NotNull;

public sealed interface Result<T> permits StringResult, ObjectResult, ObjectListResult,
    InvalidResult, EnumResult {

  static Result<String> invalidResult(final @NotNull String errorMessage) {
    return new InvalidResult(errorMessage);
  }

  @NotNull T value();

  default boolean invalid() {
    return this instanceof InvalidResult;
  }
}
