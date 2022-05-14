package maxwainer.college.gui.pages;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class BasePageController extends AbstractPage {

  @FXML
  protected void onOrderTicketClick() {
    openPage("order-ticket");
  }

  @FXML
  protected void onListTicketsList() {
    openPage("list-tickets");
  }
}
