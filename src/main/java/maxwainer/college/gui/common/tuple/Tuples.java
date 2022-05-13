package maxwainer.college.gui.common.tuple;

import org.jetbrains.annotations.NotNull;

public final class Tuples {

  private Tuples() {
    throw new AssertionError();
  }

  public static <K, V> @NotNull Tuple2<K, V> of(final K key, final V value) {
    return new Tuple2Impl<>(key, value);
  }

}
