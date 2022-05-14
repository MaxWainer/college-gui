package maxwainer.college.gui.pages;

import javafx.fxml.FXML;

public class BasePageController extends AbstractPage {

  @FXML
  protected void onOrderTicketClick() {
    openPage("order-ticket");
  }
}
