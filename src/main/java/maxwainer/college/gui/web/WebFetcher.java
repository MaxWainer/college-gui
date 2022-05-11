package maxwainer.college.gui.web;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import maxwainer.college.gui.exception.MissingPropertyException;
import maxwainer.college.gui.web.params.WebParameters;
import maxwainer.college.gui.web.result.Result;
import org.jetbrains.annotations.NotNull;

public interface WebFetcher<T extends Result> {

  @NotNull CompletableFuture<T> fetchData(final @NotNull WebParameters parameters)
      throws MissingPropertyException, IOException;

  default @NotNull CompletableFuture<T> fetchData()
      throws MissingPropertyException, IOException {
    return fetchData(WebParameters.empty());
  }

}
