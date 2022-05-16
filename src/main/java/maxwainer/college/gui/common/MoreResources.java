package maxwainer.college.gui.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;
import org.jetbrains.annotations.NotNull;

public final class MoreResources {

  private MoreResources() {
    MoreExceptions.instantiationError();
  }

  public static @NotNull InputStream loadFxmlFile(final @NotNull String url) {
    final var classLoader = MoreResources.class.getClassLoader();

    return Objects.requireNonNull(
        classLoader.getResourceAsStream("maxwainer/college/gui/" + url + ".fxml"));
  }

  public static @NotNull Properties properties(final @NotNull String name) {
    try (final var stream = MoreResources.class
        .getClassLoader()
        .getResourceAsStream(name + ".properties")) {
      final var properties = new Properties();

      properties.load(stream);

      return properties;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
