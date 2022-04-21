package maxwainer.college.gui.web;

import java.util.Optional;
import org.jetbrains.annotations.NotNull;

public final class WebFetcherRegistry {

  public <V extends WebFetcher> @NotNull Optional<V> findFetcher(
      final @NotNull Class<? extends V> fetcherClazz) {
    throw new UnsupportedOperationException();
  }

}