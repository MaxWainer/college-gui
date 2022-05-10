package maxwainer.college.gui.web;

import java.util.Optional;
import org.jetbrains.annotations.NotNull;

public interface WebFetcherRegistry {

  <V extends WebFetcher> @NotNull Optional<V> findFetcher(
      final @NotNull Class<? extends V> fetcherClazz);

  <V extends WebFetcher> void registerFetcher(final @NotNull V webFetcher);

}
