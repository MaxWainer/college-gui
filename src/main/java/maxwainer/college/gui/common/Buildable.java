package maxwainer.college.gui.common;

import org.jetbrains.annotations.NotNull;

public interface Buildable<T> {

  @NotNull T build();

}
