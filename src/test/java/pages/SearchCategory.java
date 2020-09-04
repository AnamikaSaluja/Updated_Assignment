package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import base.BasePage;

public class SearchCategory extends BasePage {

	public SearchCategory(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//i[@class='hm-icon nav-sprite']")
	public WebElement categoryIcon;

	public void launch(String url) {
		driver.get(url);
	}

	public void CategoryIcon() {
		categoryIcon.click();
	}

}
