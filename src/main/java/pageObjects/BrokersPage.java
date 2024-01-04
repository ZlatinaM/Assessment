package pageObjects;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BrokersPage {

    private WebDriver driver;

    @FindBy(xpath = "//a[text() = 'Зареди още']")
    WebElement loadMoreBtn;

    @FindBy(css = ".input-search")
    WebElement searchField;

    public BrokersPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void clickOnLoadMoreBtn() {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", loadMoreBtn);
    }


    public boolean verifyBrokersInformationIsReturned() {
        List<WebElement> names = driver.findElements(By.xpath("//div[@class = 'broker-data' ]//h3/a"));
        List<String> namesForSearch = new ArrayList<>();
        for (int i = 0; i < names.size(); i++) {
            String name = names.get(i).getText();

            namesForSearch.add(name);
        }

        boolean allOperationsSuccessful = true;

        for (int i = 0; i < namesForSearch.size(); i++) {
            try {
                searchField.clear();
                searchField.sendKeys(namesForSearch.get(i));
                Thread.sleep(2000);

                SearchResultPage searchResultPage = new SearchResultPage(driver);
                boolean isNameDisplayed = searchResultPage.isNameDisplayed();
                boolean isNumPropertiesDisplayed = searchResultPage.isNumPropertiesDisplayed();
                boolean isAddressDisplayed = searchResultPage.isAddressDisplayed();
                boolean arePhonesDisplayed = searchResultPage.areThePhoneNumbersDisplayed();
                boolean doesTheSearchedNameMatch = namesForSearch.get(i).equals(searchResultPage.getBrokersName());

                if (!isNameDisplayed || !isNumPropertiesDisplayed || !isAddressDisplayed || !arePhonesDisplayed || !doesTheSearchedNameMatch) {
                    allOperationsSuccessful = false;
                    break;
                }
            } catch (Exception e) {
                allOperationsSuccessful = false;
                System.out.println("Exception occurred: " + e.getMessage());
                break;
            }
        }

        return allOperationsSuccessful;
    }

    public void waitUntilAllBrokersAreLoaded() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//div[@class = 'broker-data' ]//h3/a"), 100));
    }
}





