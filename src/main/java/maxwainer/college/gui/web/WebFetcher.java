package maxwainer.college.gui.web;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import maxwainer.college.gui.exception.MissingPropertyException;
import maxwainer.college.gui.web.params.WebParameters;
import org.jetbrains.annotations.NotNull;

public interface WebFetcher<T> {

  @NotNull CompletableFuture<T> fetchData(final @NotNull WebParameters parameters)
      throws MissingPropertyException, IOException;

}
