package maxwainer.college.gui.web.params;

import java.util.Map;
import java.util.Optional;
import java.util.function.UnaryOperator;
import maxwainer.college.gui.common.Buildable;
import maxwainer.college.gui.exception.MissingPropertyException;
import maxwainer.college.gui.web.params.WebParametersImpl.BuilderImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

public sealed interface WebParameters permits WebParametersImpl {

  static WebParameters.Builder builder() {
    return new BuilderImpl();
  }

  static WebParameters empty() {
    return WebParametersImpl.EMPTY;
  }

  @NotNull @Unmodifiable Map<String, Object> rawMap();

  default <T> @NotNull Optional<T> get(final @NotNull String name, final @NotNull Class<T> as) {
    return Optional.ofNullable((T) rawMap().get(name));
  }

  default <T> @NotNull T getOrThrow(final @NotNull String name, final @NotNull Class<T> as)
      throws MissingPropertyException {
    return get(name, as).orElseThrow(() -> new MissingPropertyException(name));
  }

  interface Builder extends Buildable<WebParameters> {

    Builder appendParam(final @NotNull String name, final @NotNull Object data);

    Builder modifyMap(final @NotNull UnaryOperator<Map<String, Object>> modifier);

  }

}
