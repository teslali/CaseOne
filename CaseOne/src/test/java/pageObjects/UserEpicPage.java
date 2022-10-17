package pageObjects;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Properties;
import java.util.stream.IntStream;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteKeyboard;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import utils.TestContextSetup;

public class UserEpicPage {
	public WebDriver driver;

	TestContextSetup testContextSetup;
	UserEpicPage userEpicPage;

	public UserEpicPage(WebDriver driver) {
		this.driver = driver;
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

	}

	public UserEpicPage(TestContextSetup testContextSetup) {
		this.testContextSetup = testContextSetup;
		this.userEpicPage = testContextSetup.pageObjectManager.getUserEpicPage();
	}

	// locators
	By popUp = By.xpath("//span[@class='editable ins-element-editable editable-text']");
	By cookie = By.xpath("//button[normalize-space()='ANLADIM']");
	By man = By.xpath("//a[@class='menu-header-item__title'][normalize-space()='ERKEK']");
	By manJacket = By.xpath("//a[contains(@href,'/tr-TR/TR/kategori/erkek/erkek-ceket')]");
	By minFilter = By.xpath("//input[@placeholder='En Az']");
	By maxFilter = By.xpath("//input[@placeholder='En Çok']");
	By prcFilterSearch = By.xpath("//a[normalize-space()='ARA']");
	By selectedJacket = By.xpath("//div[9]//a[1]//div[2]//h5[2]");
	By secondColor = By.xpath("//div[@title='Koyu Haki']");
	By firstColor = By.xpath("//div[@title='Açık Gri']");
	By price = By.xpath("//span[@class='advanced-price']");
	By discountedPrice = By.xpath("//div[@class='col-xs-12 price-area']//div//div//span[@class='price']");
	By productImage = By
			.xpath("//body[1]/div[3]/div[1]/div[17]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/img[1]");
	By firstImg = By.xpath("//div[@class='col-xs-6']//img[@id='OptionImage0']");
	By dummy = By.cssSelector("body");
	By closePic = By.xpath("//a[@id='CloseModalButton']");
	By nextPic = By.xpath("//i[@class='fa fa-angle-down']");
	By scndPic = By.xpath("//img[@data-imageindex='1']");
	By addCart = By.xpath("//a[@id='pd_add_to_cart']");
	By xLarge = By.xpath(
			"//div[@class='col-xs-12 size-area main-size-area pt-5']//div[@class='option-sizes size-area ucOptionSizeSelector mt-0 mb-0']//div[@id='option-size']//a[@href='javascript:;'][normalize-space()='XL']");
	By cart = By.xpath("//span[normalize-space()='Sepetim']");
	By cartPrice = By.xpath("//span[@class='rd-cart-item-price mb-15']");
	// if there is "sepette fiyati" use this locator
	By sepetFiyat = By.xpath("//span[@class='price-regular']");

	public void landingPage() throws InterruptedException {
		String landingTitle = driver.getTitle();
		// I saw two landing page title while checking tests so used if/else
		if (landingTitle.equals("LC Waikiki | İlk Alışverişte Kargo Bedava! - LC Waikiki")) {
			Assert.assertEquals(landingTitle, "LC Waikiki | İlk Alışverişte Kargo Bedava! - LC Waikiki");
		} else {
			Assert.assertEquals(landingTitle, "LC Waikiki - Online Alışveriş Sitesi, Moda Senin Seçimin - LC Waikiki");
		}
		driver.findElement(cookie).click();

	}

	public void manJacket() throws InterruptedException {
		// Hovers over "Erkek" section then clicks on "Ceket"
		Actions actions = new Actions(driver);
		WebElement manTitle = driver.findElement(man);
		actions.moveToElement(manTitle).build().perform();
		// Closes customer service pop-up but it's not popping up every time that why
		// test waits for 5 sec after hovering over "Erkek"
		try {
			driver.findElement(popUp).click();
		} catch (Exception e) {
			Thread.sleep(1);
		}
		driver.findElement(manJacket).click();
	}

	public void jacketPage() throws InterruptedException {
		String landingTitle = driver.getTitle();
		Assert.assertEquals(landingTitle, "Erkek Ceket, Blazer Ceket, Kot Ceket - LC Waikiki");
		// Arranges price filter
		driver.findElement(minFilter).sendKeys("300");
		driver.findElement(maxFilter).sendKeys("500");
		driver.findElement(prcFilterSearch).click();
		Thread.sleep(2000);
	}

	public void inspectJacket() throws InterruptedException {

		Actions actions = new Actions(driver);
		String selectedJacketName = driver.findElement(selectedJacket).getText();
		driver.findElement(selectedJacket).click();
		String landingTitle = driver.getTitle();
		boolean titleCheck = landingTitle.contains(selectedJacketName);
		Assert.assertEquals(titleCheck, true);
		// this is here to close "Senin icin stoga getirelim" message
		driver.findElement(dummy).click();
		// Inspects pics, scroll down not working, hovered over elements to move pics
		driver.findElement(firstImg).click();
		Thread.sleep(2000);
		for (int i = 0; i < 2; i++) {
			WebElement closePicture = driver.findElement(closePic);
			actions.moveToElement(closePicture).build().perform();
			Thread.sleep(2000);
			WebElement nextPicture = driver.findElement(nextPic);
			actions.moveToElement(nextPicture).build().perform();
			Thread.sleep(2000);
			driver.findElement(scndPic).click();
		}
		driver.findElement(closePic).click();
	}

	public void addCart() throws InterruptedException {
		// Tries to add cart before selecting size then selects size and adds to cart
		driver.findElement(addCart).click();
		String error = driver.findElement(addCart).getAttribute("text");
		Assert.assertEquals(error, "LÜTFEN BEDEN SEÇİNİZ");
		Thread.sleep(3000);
		driver.findElement(xLarge).click();
		driver.findElement(addCart).click();
	}

	public void cart() throws IOException, InterruptedException {
		// Compares between product page price and cart price but with a bad code :(
		try {
			String productPrice = driver.findElement(discountedPrice).getAttribute("text");
			driver.findElement(cart).click();
			String cartPrc = driver.findElement(cartPrice).getAttribute("text");
			Assert.assertEquals(productPrice, cartPrc);
		} catch (Exception e) {
			String productPrice = driver.findElement(price).getAttribute("text");
			driver.findElement(cart).click();
			String cartPrc = driver.findElement(cartPrice).getAttribute("text");
			Assert.assertEquals(productPrice, cartPrc);
		} finally {
			Thread.sleep(1);
		}

		try {
			String productPrice = driver.findElement(sepetFiyat).getAttribute("text");
			driver.findElement(cart).click();
			String cartPrc = driver.findElement(cartPrice).getAttribute("text");
			Assert.assertEquals(productPrice, cartPrc);
		} catch (Exception e) {
			Thread.sleep(1);
		}
		String landingTitle = driver.getTitle();
		Assert.assertEquals(landingTitle, "Sepetim - LC Waikiki");
		// Gets url from global.properties
		FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir") + "//src//test//resources//global.properties");
		Properties prop = new Properties();
		prop.load(fis);
		String url = prop.getProperty("QAUrl");
		driver.get(url);
	}
}
