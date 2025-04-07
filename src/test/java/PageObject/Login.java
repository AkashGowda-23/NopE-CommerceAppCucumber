package PageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Login {
	public WebDriver driver;
	
	public Login(WebDriver rdriver) {
		driver=rdriver;
		PageFactory.initElements(rdriver, this);
	}
	
	
	@FindBy(id = "Email")
	WebElement email;
	
	
	@FindBy(id = "Password")
	WebElement passwd;
	
	@FindBy(xpath = "//button[@class='button-1 login-button']")
	WebElement logibn;
	
	@FindBy(xpath = "//a[normalize-space()='Logout']")
	WebElement logout;
	
	
	public void enteremail(String uname) {
		email.clear();
		email.sendKeys(uname);
	}
	public void password(String uname1) {
		passwd.clear();
		passwd.sendKeys(uname1);
	}
	
	public void submit() {
		logibn.click();
	}
	public void logout() {
		logout.click();
	}
	
	}


