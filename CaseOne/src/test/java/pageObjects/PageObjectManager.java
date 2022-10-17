package pageObjects;

import org.openqa.selenium.WebDriver;


public class PageObjectManager {
	
	public UserEpicPage userEpicPage;
	public WebDriver driver;

	
	public PageObjectManager(WebDriver driver)
	{
		this.driver = driver;
	}

	
	
	public UserEpicPage getUserEpicPage()
	{
	
	 userEpicPage= new UserEpicPage(driver);
	 return userEpicPage;
	}
	

}
