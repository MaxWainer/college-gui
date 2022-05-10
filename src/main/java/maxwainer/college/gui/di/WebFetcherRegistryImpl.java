package maxwainer.college.gui.di;

import com.google.inject.Binder;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import maxwainer.college.gui.web.WebFetcher;
import maxwainer.college.gui.web.WebFetcherRegistry;
import maxwainer.college.gui.web.implementation.auth.LoginWebFetcher;
import maxwainer.college.gui.web.implementation.auth.RegisterWebFetcher;
import org.jetbrains.annotations.NotNull;

final class WebFetcherRegistryImpl implements WebFetcherRegistry {

  private final Map<Class<? extends WebFetcher>, WebFetcher> registry = new ConcurrentHashMap<>();

  WebFetcherRegistryImpl() {
    registerFetcher(new RegisterWebFetcher());
    registerFetcher(new LoginWebFetcher());
  }

  @Override
  public <V extends WebFetcher> @NotNull Optional<V> findFetcher(
      final @NotNull Class<? extends V> fetcherClazz) {
    return (Optional<V>) Optional.ofNullable(registry.get(fetcherClazz));
  }

  @Override
  public <V extends WebFetcher> void registerFetcher(@NotNull V webFetcher) {
    this.registry.put(webFetcher.getClass(), webFetcher);
  }

  void configure(final @NotNull Binder binder) {
    for (Entry<Class<? extends WebFetcher>, WebFetcher> entry : registry.entrySet()) {
      final Class clazz = entry.getKey();
      final var fetcher = entry.getValue();

      binder.bind(clazz).toInstance(fetcher);
    }
  }

}