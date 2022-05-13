package maxwainer.college.gui.layout;

import java.util.concurrent.atomic.AtomicInteger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import maxwainer.college.gui.common.Checks;

public final class NumericField extends TextField {

  private final AtomicInteger value = new AtomicInteger(Integer.MIN_VALUE);

  public NumericField() {
    this.textProperty().addListener((observable, oldValue, newValue) -> {
      if (!newValue.matches("\\d*")) {
        setText(newValue.replaceAll("\\D", ""));
      }
    });
  }

  public int getValue() {
    return value.get();
  }
}
