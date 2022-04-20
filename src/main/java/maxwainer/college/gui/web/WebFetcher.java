package maxwainer.college.gui.web;

import java.util.concurrent.CompletableFuture;
import maxwainer.college.gui.web.params.ImmutableWebParameters;
import org.jetbrains.annotations.NotNull;

public interface WebFetcher<T> {

  @NotNull CompletableFuture<T> fetchData(final @NotNull ImmutableWebParameters parameters);

}
