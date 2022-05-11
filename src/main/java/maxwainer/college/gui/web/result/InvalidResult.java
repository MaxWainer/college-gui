package maxwainer.college.gui.web.result;

import org.jetbrains.annotations.NotNull;

record InvalidResult(String errorMessage) implements Result<String> {

  @NotNull
  @Override
  public String value() {
    return errorMessage;
  }
}
