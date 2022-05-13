package maxwainer.college.gui.layout;

import javafx.scene.control.TextField;

public final class NumericField extends TextField {

  public NumericField() {
    this.textProperty().addListener((observable, oldValue, newValue) -> {
      if (!newValue.matches("\\d*")) {
        setText(newValue.replaceAll("\\D", ""));
      }
    });
  }

  public int getValue() {
    return Integer.parseInt(getText());
  }
}
