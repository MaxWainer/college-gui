package maxwainer.college.gui.pages;

public abstract class AbstractSubPage extends AbstractPage {

  public void onBackButtonClick() {
    openPage("base-page");
  }

}
