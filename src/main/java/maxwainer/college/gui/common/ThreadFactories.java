package maxwainer.college.gui.common;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinPool.ForkJoinWorkerThreadFactory;
import java.util.concurrent.ForkJoinWorkerThread;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import org.jetbrains.annotations.NotNull;

public final class ThreadFactories {

  public static final UncaughtExceptionHandler UNCAUGHT_EXCEPTION_HANDLER =
      (t, e) -> AppLogger.LOGGER.log(Level.SEVERE, e,
          () -> "Uncaught exception in thread " + t.getName());

  private ThreadFactories() {
    MoreExceptions.instantiationError();
  }

  public static @NotNull ThreadFactory createFactory(final @NotNull String nameFormat) {
    return new ThreadFactory() {

      private final AtomicInteger counter = new AtomicInteger(0);

      @Override
      public Thread newThread(@NotNull Runnable r) {
        final var thread = new Thread(r, String.format(nameFormat,
            counter.getAndIncrement()));

        thread.setUncaughtExceptionHandler(UNCAUGHT_EXCEPTION_HANDLER);

        return thread;
      }
    };
  }

  public static @NotNull ForkJoinPool.ForkJoinWorkerThreadFactory createForkJoinFactory(
      final @NotNull String nameFormat) {
    return new ForkJoinWorkerThreadFactory() {

      private final AtomicInteger counter = new AtomicInteger(0);

      @Override
      public ForkJoinWorkerThread newThread(ForkJoinPool pool) {
        return new ApplicationForkJoinWorkerThread(pool,
            String.format(nameFormat, counter.getAndIncrement()));
      }
    };
  }

  private static final class ApplicationForkJoinWorkerThread extends ForkJoinWorkerThread {

    /**
     * Creates a ForkJoinWorkerThread operating in the given pool.
     *
     * @param pool the pool this thread works in
     * @throws NullPointerException if pool is null
     */
    private ApplicationForkJoinWorkerThread(final @NotNull ForkJoinPool pool,
        final @NotNull String name) {
      super(pool);

      this.setName(name);
    }
  }

}
