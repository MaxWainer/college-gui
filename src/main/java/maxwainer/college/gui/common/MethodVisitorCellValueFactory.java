package maxwainer.college.gui.common;

import java.util.function.Function;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;
import org.jetbrains.annotations.NotNull;

public final class MethodVisitorCellValueFactory<T, R> implements
    Callback<CellDataFeatures<T, R>, ObservableValue<R>> {

  private final Function<T, R> visitor;

  public MethodVisitorCellValueFactory(final @NotNull Function<T, R> visitor) {
    this.visitor = visitor;
  }

  @Override
  public ObservableValue<R> call(CellDataFeatures<T, R> param) {
    return new ReadOnlyObjectWrapper<>(visitor.apply(param.getValue()));
  }
}
