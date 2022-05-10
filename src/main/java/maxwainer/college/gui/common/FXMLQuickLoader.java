package maxwainer.college.gui.common;

import java.io.InputStream;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;

public final class FXMLQuickLoader {

  private FXMLQuickLoader() {
    throw new AssertionError();
  }

  public static @NotNull InputStream load(final @NotNull String url) {
    final ClassLoader classLoader = FXMLQuickLoader.class.getClassLoader();

    return Objects.requireNonNull(classLoader.getResourceAsStream("maxwainer/college/gui/" + url + ".fxml"));
  }

}
