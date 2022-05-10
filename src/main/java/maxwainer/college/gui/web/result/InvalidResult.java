package maxwainer.college.gui.web.result;

import org.jetbrains.annotations.NotNull;

final class InvalidResult implements Result<String> {

  private final String errorMessage;

  public InvalidResult(final @NotNull String errorMessage) {
    this.errorMessage = errorMessage;
  }

  @NotNull
  @Override
  public String value() {
    return errorMessage;
  }
}
