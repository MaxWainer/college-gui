package maxwainer.college.gui.common.tuple;

import java.util.List;
import org.jetbrains.annotations.NotNull;

public sealed interface Tuple3<F, S, T> extends Tuple permits Tuple3Impl {

  F first();

  S second();

  T third();

  @Override
  default @NotNull List<?> asList() {
    return List.of(first(), second(), third());
  }
}
