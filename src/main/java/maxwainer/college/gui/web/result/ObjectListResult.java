package maxwainer.college.gui.web.result;

import java.util.Iterator;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public record ObjectListResult<T>(List<T> value) implements Result<List<T>>, Iterable<T> {

  @NotNull
  @Override
  public Iterator<T> iterator() {
    return value.iterator();
  }
}
