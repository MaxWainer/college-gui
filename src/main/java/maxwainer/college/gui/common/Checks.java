package maxwainer.college.gui.common;

import org.jetbrains.annotations.NotNull;

public final class Checks {

  private Checks() {
    throw new AssertionError();
  }

  public static boolean isNumeric(final @NotNull String text) {
    try {
      Integer.parseInt(text);
      return true;
    } catch (final NumberFormatException ignored) {
      // empty block
    }

    return false;
  }

}
