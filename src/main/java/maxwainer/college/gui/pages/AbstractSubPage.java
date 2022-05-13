package maxwainer.college.gui.pages;

public abstract class AbstractSubPage extends AbstractPage {

  protected void onBackButtonClick() {
    openPage(backPageName());
  }

  protected abstract String backPageName();

}
