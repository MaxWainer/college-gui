package maxwainer.college.gui.common.tuple;

import maxwainer.college.gui.common.MoreExceptions;
import org.jetbrains.annotations.NotNull;

public final class Tuples {

  private Tuples() {
    MoreExceptions.instantiationError();
  }

  public static <K, V> @NotNull Tuple2<K, V> of(final K key, final V value) {
    return new Tuple2Impl<>(key, value);
  }

  public static <F, S, T> @NotNull Tuple3<F, S, T> of(final F first, final S second,
      final T third) {
    return new Tuple3Impl<>(first, second, third);
  }
}
