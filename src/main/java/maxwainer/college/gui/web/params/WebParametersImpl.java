package maxwainer.college.gui.web.params;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.UnaryOperator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

final class WebParametersImpl implements WebParameters {

  private final Map<String, Object> rawMap;

  WebParametersImpl(final @NotNull Map<String, Object> rawMap) {
    this.rawMap = rawMap;
  }

  @Override
  public @NotNull @Unmodifiable Map<String, Object> rawMap() {
    return Collections.unmodifiableMap(rawMap);
  }

  static final class BuilderImpl implements Builder {

    private final Map<String, Object> rawMap = new ConcurrentHashMap<>();

    @Override
    public @NotNull WebParameters build() {
      return new WebParametersImpl(rawMap);
    }

    @Override
    public Builder appendParam(@NotNull String name, @NotNull Object data) {
      this.rawMap.put(name, data);
      return this;
    }

    @Override
    public Builder modifyMap(@NotNull UnaryOperator<Map<String, Object>> modifier) {
      modifier.apply(rawMap);
      return this;
    }
  }

}
