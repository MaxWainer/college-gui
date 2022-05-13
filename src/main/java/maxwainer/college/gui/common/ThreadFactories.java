package maxwainer.college.gui.common;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import org.jetbrains.annotations.NotNull;

public final class ThreadFactories {

  private ThreadFactories() {
    throw new AssertionError();
  }

  public static @NotNull ThreadFactory createFactory(final @NotNull String nameFormat) {
    return new ThreadFactory() {

      private final AtomicInteger counter = new AtomicInteger(0);

      @Override
      public Thread newThread(@NotNull Runnable r) {
        final var thread = new Thread(r, String.format(nameFormat,
            counter.getAndIncrement()));

        thread.setUncaughtExceptionHandler(
            (t, e) -> AppLogger.LOGGER.log(Level.SEVERE, e,
                () -> "Uncaught exception in thread " + t.getName()));

        return thread;
      }
    };
  }

}
