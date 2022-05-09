package maxwainer.college.gui.common;

import java.io.IOException;
import java.util.Objects;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.jetbrains.annotations.NotNull;

public final class FXMLQuickLoader {

  private FXMLQuickLoader() {
    throw new AssertionError();
  }

  public static @NotNull Parent load(final @NotNull String url) throws IOException {
    final ClassLoader classLoader = FXMLQuickLoader.class.getClassLoader();

    return FXMLLoader.load(Objects.requireNonNull(classLoader.getResource("maxwainer/college/gui/" + url)));
  }

}
