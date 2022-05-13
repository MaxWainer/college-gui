package maxwainer.college.gui.pages.auth;

import javafx.fxml.Initializable;
import maxwainer.college.gui.common.tuple.Tuple2;
import maxwainer.college.gui.object.web.User;
import maxwainer.college.gui.pages.AbstractSubPage;
import maxwainer.college.gui.web.result.ObjectResult;
import maxwainer.college.gui.web.result.Result;
import org.jetbrains.annotations.NotNull;

abstract class AbstractAuthPage extends AbstractSubPage implements Initializable {

  @SuppressWarnings("unchecked")
  protected void handleResult(final @NotNull Result<?> result) {
    if (result instanceof ObjectResult<?> objectResult) {
      final var data = (Tuple2<String, User>) objectResult.value();

      appValues.accessToken(data.key());
      appValues.user(data.value());

      openPage("base-page");
    }
  }

  @Override
  protected String backPageName() {
    return "main-page";
  }
}
