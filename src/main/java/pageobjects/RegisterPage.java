package pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RegisterPage {
	
	WebDriver driver;

	public RegisterPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver,this);
	}
	
	@FindBy(id="input-firstname")
	WebElement firstNameField;
	
	@FindBy(id="input-lastname")
	WebElement lastNameField;
	
	@FindBy(id="input-email")
	WebElement emailField;
	
	@FindBy(id="input-telephone")
	WebElement telephoneField;
	
	@FindBy(id="input-password")
	WebElement passwordField;
	
	@FindBy(id="input-confirm")
	WebElement passwordConfirmField;
	
	@FindBy(xpath="//input[@name='newsletter'][@value='1']")
	WebElement yesNewsletterOption;
	
	@FindBy(name="agree")
	WebElement privacyPolicyField;
	
	@FindBy(xpath="//input[@value='Continue']")
	WebElement continueButton;
	
	public void enterFirstName(String firstNameText) {
		firstNameField.sendKeys(firstNameText);
	}
	
	public void enterLastName(String lastNameText) {
		lastNameField.sendKeys(lastNameText);
	}
	
	public void enterEmailAddress(String emailText) {
		emailField.sendKeys(emailText);
	}
	
	public void enterTelephoneNumber(String telephoneText) {
		telephoneField.sendKeys(telephoneText);
	}
	
	public void enterPassword(String passwordText) {
		passwordField.sendKeys(passwordText);
	}
	
	public void enterConfirmationPassword(String passwordText) {
		passwordConfirmField.sendKeys(passwordText);
	}
	
	public void optForNewsletter() {
		yesNewsletterOption.click();
	}
	
	public void selectPrivacyPolicy() {
		privacyPolicyField.click();
	}
	
	public void clickOnContinueButton() {
		continueButton.click();
	}

}
