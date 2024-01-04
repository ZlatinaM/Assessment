package pageObjects;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SearchResultPage {
    private WebDriver driver;

    @FindBy(xpath = "//h3[@class]/a")
    WebElement brokerName;

    @FindBy(xpath = "//div[@class = \"office\"]")
    WebElement address;

    @FindBy(xpath = "//span[@class = \"tel\"]")
    List<WebElement> telephones;

    @FindBy(xpath = "//div[@class='position']//a")
    WebElement numberOfProperties;

    public SearchResultPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }
    public boolean isNameDisplayed() {
        try {
            return brokerName.isDisplayed();
        } catch (org.openqa.selenium.NoSuchElementException | org.openqa.selenium.StaleElementReferenceException e) {
            return false;
        }
    }

    public String getBrokersName(){
         String displayedName = null;
         displayedName = brokerName.getText();

         return displayedName;
    }

    public boolean isAddressDisplayed() {
        try {
            return address.isDisplayed();
        } catch (org.openqa.selenium.NoSuchElementException | org.openqa.selenium.StaleElementReferenceException e) {
            return false;
        }
    }
    public boolean isNumPropertiesDisplayed() {
        try {
            return numberOfProperties.isDisplayed();
        } catch (org.openqa.selenium.NoSuchElementException | org.openqa.selenium.StaleElementReferenceException e) {
            return false;
        }
    }

    public boolean areThePhoneNumbersDisplayed() {
        boolean allPhonesDisplayed = true;
        for (WebElement telephone : telephones) {
                try {
                    if (!telephone.isDisplayed()) {
                        allPhonesDisplayed = false;
                        break;
                    }
                } catch (org.openqa.selenium.NoSuchElementException | org.openqa.selenium.StaleElementReferenceException e) {
                    allPhonesDisplayed = false;
                    break;
                }
            }
            return allPhonesDisplayed;
    }

}

