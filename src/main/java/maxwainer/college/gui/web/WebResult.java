package maxwainer.college.gui.web;

import org.jetbrains.annotations.NotNull;

public record WebResult<T>(int codeFlags, @NotNull T data) {

  public interface InternalCodes {

    int AUTH_INVALID_PASSWORD = 0;
    int AUTH_INVALID_LOGIN = 2;
    int VALID = 4;
    int INVALID = 6;

  }

  public boolean flagsPresent(final int flag) {
    return (codeFlags & flag) == flag;
  }

}
