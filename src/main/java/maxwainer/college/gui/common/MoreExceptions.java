package maxwainer.college.gui.common;

import java.util.function.Function;
import java.util.function.Predicate;
import org.jetbrains.annotations.NotNull;

public final class MoreExceptions {

  private MoreExceptions() {
    instantiationError();
  }

  public static void instantiationError() {
    throw new AssertionError("Utility class cannot be instantiated!");
  }

  public static void nagAuthor(final @NotNull String detailedMessage) {
    throw new NagAuthorException(detailedMessage);
  }

  public static <T, X extends Throwable> T checkObject(
      final T object,
      final @NotNull Predicate<T> predicate,
      final @NotNull Function<T, X> exceptionFactory) throws X {
    if (!predicate.test(object)) {
      throw exceptionFactory.apply(object);
    }

    return object;
  }

  // we're going to keep it private, to avoid ignoring them
  private static final class NagAuthorException extends RuntimeException {

    NagAuthorException(final @NotNull String message) {
      super(message);
    }
  }

}