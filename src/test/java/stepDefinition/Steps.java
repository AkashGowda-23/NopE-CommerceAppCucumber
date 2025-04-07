package stepDefinition;

import org.openqa.selenium.WebDriver;

import PageObject.Login;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class Steps {
	
	public WebDriver driver;
	public Login lp;
	

	@Given("user Launch chrome browser")
	public void user_launch_chrome_browser() {
	   
	}

	@When("user opens URL {string}")
	public void user_opens_url(String string) {
	    
	}

	@When("user enters Email as {string} and Password as {string}")
	public void user_enters_email_as_and_password_as(String string, String string2) {
	  
	}


	@When("Click on the login")
	public void click_on_the_login() {
	    
	}

	@Then("the page title should be {string}")
	public void the_page_title_should_be(String string) {
	    
	}

	@When("the user click on the logout link")
	public void the_user_click_on_the_logout_link() {
	    
	}

	@Then("page title should be {string}")
	public void page_title_should_be(String string) {
	    
	}

	@Then("close the browser")
	public void close_the_browser() {
	    
	}
	
}
