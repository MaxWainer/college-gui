package maxwainer.college.gui.values;

import maxwainer.college.gui.object.web.User;
import org.jetbrains.annotations.NotNull;

public final class AppValues {

  private String accessToken;
  private User user;

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
    if (accessToken == null) {
      throw new UnsupportedOperationException("Token is not defined!");
    }

    return accessToken;
  }

  public void user(final @NotNull User user) {
    this.user = user;
  }

  @NotNull
  public User user() {
    if (user == null) {
      throw new UnsupportedOperationException("User is not defined!");
    }

    return user;
  }
}
