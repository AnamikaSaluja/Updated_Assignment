package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import base.BasePage;

public class AddedToCartPage extends BasePage {

	public AddedToCartPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//h1[@class='a-size-medium a-text-bold']")
	public WebElement addedTocart;

	@FindBy(xpath = "//a[@id='hlb-view-cart-announce']")
	public WebElement cartButton;

	public boolean verifyAddedToCart() {
		Assert.assertEquals(addedTocart.getText(), "Added To Cart");
		return true;
	}
}
