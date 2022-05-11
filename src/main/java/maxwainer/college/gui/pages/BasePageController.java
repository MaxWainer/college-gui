package maxwainer.college.gui.pages;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class BasePageController extends AbstractPage {

  @FXML
  public void onOrderTicketClick() {
    openPage("order-ticket");
  }
}
