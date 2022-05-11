package maxwainer.college.gui.values;

import org.jetbrains.annotations.NotNull;

public final class AppValues {

  private String accessToken;

  public void accessToken(final @NotNull String accessToken) {
    this.accessToken = accessToken;
  }

  public boolean accessTokenPresent() {
    return accessToken != null;
  }

  public boolean accessTokenNotPresent() {
    return !accessTokenPresent();
  }

  @NotNull
  public String accessToken() {
    if (accessToken == null)
      throw new UnsupportedOperationException("Token is not defined!");

    return accessToken;
  }
}
