package maxwainer.college.gui.pages;

import javafx.fxml.FXML;

public abstract class AbstractSubPage extends AbstractPage {

  @FXML
  protected void onBackButtonClick() {
    openPage(backPageName());
  }

  protected abstract String backPageName();

}
