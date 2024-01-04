import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.BrokersPage;

public class End2EndTest extends BasicConfigurations {

    @Test
    public void verifyBrokersInformation(){
        BrokersPage brokersPage = new BrokersPage(driver);

        brokersPage.clickOnLoadMoreBtn();
        brokersPage.waitUntilAllBrokersAreLoaded();

        Assert.assertTrue(brokersPage.verifyBrokersInformationIsReturned(), "No data is found");
    }
}
