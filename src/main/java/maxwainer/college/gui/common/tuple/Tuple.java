package maxwainer.college.gui.common.tuple;

import java.util.List;
import org.jetbrains.annotations.NotNull;

public sealed interface Tuple permits Tuple2, Tuple3 {

  @NotNull List<?> asList();

}
