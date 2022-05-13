package maxwainer.college.gui.common.tuple;

import java.util.List;
import org.jetbrains.annotations.NotNull;

public sealed interface Tuple2<K, V> extends Tuple permits Tuple2Impl {

  @Override
  default @NotNull List<?> asList() {
    return List.of(key(), value());
  }

  K key();

  V value();
}
