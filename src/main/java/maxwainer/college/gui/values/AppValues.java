package maxwainer.college.gui.values;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class AppValues {

  private String accessToken;

  public void accessToken(final @NotNull String accessToken) {
    this.accessToken = accessToken;
  }

  @Nullable
  public String accessToken() {
    return accessToken;
  }
}
