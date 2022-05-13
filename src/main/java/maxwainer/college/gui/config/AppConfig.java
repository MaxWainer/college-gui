package maxwainer.college.gui.config;

import java.util.Optional;
import java.util.Properties;
import maxwainer.college.gui.exception.MissingPropertyException;
import org.jetbrains.annotations.NotNull;

public final class AppConfig {

  private final Properties properties;

  public AppConfig(final @NotNull Properties properties) {
    this.properties = properties;
  }

  public <T> @NotNull Optional<T> get(final @NotNull String name, final @NotNull Class<T> as) {
    return Optional.ofNullable((T) properties.get(name));
  }

  public <T> @NotNull T getOrThrow(final @NotNull String name, final @NotNull Class<T> as)
      throws MissingPropertyException {
    return get(name, as).orElseThrow(() -> new MissingPropertyException(name));
  }

  public @NotNull String baseUrl() {
    try {
      return getOrThrow("base-url", String.class);
    } catch (MissingPropertyException e) {
      throw new RuntimeException(e);
    }
  }

  public boolean checkConnectionCertificate() {
    try {
      return getOrThrow("check-connection-certificate", Boolean.class);
    } catch (MissingPropertyException e) {
      return false;
    }
  }

}
