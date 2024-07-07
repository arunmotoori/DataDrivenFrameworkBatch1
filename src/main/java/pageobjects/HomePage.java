package pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
	
	WebDriver driver;
	
	public HomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver,this);	
	}
	
	@FindBy(xpath="//span[text()='My Account']")
	WebElement myAccountDropMenu;
	
	@FindBy(linkText="Login")
	WebElement loginOption;
	
	@FindBy(linkText="Register")
	WebElement registerOption;
	
	@FindBy(name="search")
	WebElement searchBoxField;
	
	@FindBy(xpath="//button[@class='btn btn-default btn-lg']")
	WebElement searchButton;
	
	public void clickOnMyAccountDropMenu() {
		myAccountDropMenu.click();
	}
	
	public LoginPage selectLoginOption() {
		loginOption.click();
		return new LoginPage(driver);
	}
	
	public RegisterPage selectRegisterOption() {
		registerOption.click();
		return new RegisterPage(driver);
	}
	
	public void enterSearchText(String searchText) {
		searchBoxField.sendKeys(searchText);
	}
	
	public SearchResultsPage clickOnSearchButton() {
		searchButton.click();
		return new SearchResultsPage(driver);
	}
		
}
