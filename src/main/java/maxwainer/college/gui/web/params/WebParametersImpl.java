package maxwainer.college.gui.web.params;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.UnaryOperator;
import maxwainer.college.gui.common.Types;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

final class WebParametersImpl implements WebParameters {

  static final WebParametersImpl EMPTY = new WebParametersImpl(Collections.emptyMap());

  private final Map<String, Object> rawMap;

  WebParametersImpl(final @NotNull Map<String, Object> rawMap) {
    this.rawMap = rawMap;
  }

  @Override
  public @NotNull @Unmodifiable Map<String, Object> rawMap() {
    return Collections.unmodifiableMap(rawMap);
  }

  @Override
  public <T extends Record> @NotNull T toModel(@NotNull Class<? extends T> possibleRecord, final boolean unboxPrimitives) {
    try {
      final var constructor = possibleRecord
          .getConstructor(rawMap
              .values()
              .stream()
              .map(obj -> Types.unboxed(obj.getClass()))
              .toArray(Class[]::new));

      return constructor.newInstance(rawMap.values().toArray());
    } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
             IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

  static final class BuilderImpl implements Builder {

    private final Map<String, Object> rawMap = new LinkedHashMap<>();

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
