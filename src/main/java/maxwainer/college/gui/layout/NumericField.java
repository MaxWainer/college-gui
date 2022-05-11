package maxwainer.college.gui.layout;

import java.util.concurrent.atomic.AtomicInteger;
import javafx.scene.control.TextField;
import maxwainer.college.gui.common.Checks;

public final class NumericField extends TextField {

  private final AtomicInteger value = new AtomicInteger(0);

  public NumericField() {

    textProperty()
        .addListener((observable, oldValue, newValue) -> {
          if (Checks.isNumeric(newValue)) {
            setText(newValue);
            this.value.set(Integer.parseInt(newValue));
          }
        });
  }

  public int getValue() {
    return value.get();
  }
}
