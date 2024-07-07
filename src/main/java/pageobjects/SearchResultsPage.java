package pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SearchResultsPage {
	
	WebDriver driver;
	
	public SearchResultsPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver,this);
	}
	
	@FindBy(xpath="//h4/a")
	WebElement resultedProduct;
	
	@FindBy(xpath="//h2/following-sibling::p")
	WebElement noProductMessage;
	
	public String getResultedProductName() {
		String resultedProductName = resultedProduct.getText();
		return resultedProductName;
	}
	
	public String getResultedMessage() {
		String resultedMessage = noProductMessage.getText();
		return resultedMessage;
	}

}
