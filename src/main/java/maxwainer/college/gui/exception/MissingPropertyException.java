package maxwainer.college.gui.exception;

import org.jetbrains.annotations.NotNull;

public final class MissingPropertyException extends Exception {

  public MissingPropertyException(final @NotNull String paramName) {
    super("Missing property: " + paramName);
  }

}
