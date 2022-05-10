package maxwainer.college.gui.common;

import java.io.IOException;
import java.util.Properties;
import org.jetbrains.annotations.NotNull;

public final class MoreResources {

  private MoreResources() {
    throw new AssertionError();
  }

  public static @NotNull Properties properties(final @NotNull String name) {
    try (final var stream = MoreResources.class
        .getClassLoader()
        .getResourceAsStream(name + ".properties")) {
      final Properties properties = new Properties();

      properties.load(stream);

      return properties;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
