package maxwainer.college.gui.common;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import org.jetbrains.annotations.NotNull;

public final class Alerts {

  private Alerts() {
    throw new AssertionError();
  }

  public static void showAlert(final @NotNull Throwable throwable) {
    final Alert alert = new Alert(AlertType.ERROR);

    alert.setTitle("Error in application!");
    alert.setContentText(buildError(throwable));

    alert.show();
  }

  private static @NotNull String buildError(final @NotNull Throwable throwable) {
    final StringBuilder builder = new StringBuilder();

    builder
        .append(throwable.getClass().getName()).append(": ")
        .append(throwable.getMessage()).append('\n');

    for (final StackTraceElement stackTraceElement : sanitizeStackTrace(
        throwable.getStackTrace())) {
      builder.append(stackTraceElement.toString()).append('\n');
    }

    return builder.toString();
  }

  private static List<StackTraceElement> sanitizeStackTrace(
      final @NotNull StackTraceElement[] elements) {
    final List<StackTraceElement> output = new ArrayList<>();

    for (final StackTraceElement element : elements) {
      if (element.getClassName().contains("maxwainer.college.gui")) {
        output.add(element);
      }
    }

    return output;
  }

}
