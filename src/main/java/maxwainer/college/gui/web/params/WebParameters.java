package maxwainer.college.gui.web.params;

import java.util.Map;
import java.util.function.UnaryOperator;
import maxwainer.college.gui.common.Buildable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

public interface WebParameters {

  @NotNull @Unmodifiable Map<String, Object> rawMap();

  interface Builder<V extends WebParameters> extends Buildable<V> {

    Builder<V> modifyMap(final @NotNull UnaryOperator<Map<String, Object>> modifier);

  }

}
