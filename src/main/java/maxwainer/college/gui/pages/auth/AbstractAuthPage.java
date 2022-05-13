package maxwainer.college.gui.pages.auth;

import javafx.fxml.Initializable;
import maxwainer.college.gui.pages.AbstractSubPage;
import maxwainer.college.gui.web.result.Result;
import maxwainer.college.gui.web.result.StringResult;
import org.jetbrains.annotations.NotNull;

abstract class AbstractAuthPage extends AbstractSubPage implements Initializable {

  protected void handleResult(final @NotNull Result<?> result) {
    if (result instanceof StringResult stringResult) {
      final String token = stringResult.value();
      appValues.accessToken(token);

      openPage("base-page");
    }
  }

  @Override
  protected String backPageName() {
    return "main-page";
  }
}
