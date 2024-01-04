import java.lang.reflect.Method;
import java.nio.file.FileSystems;
import java.time.Duration;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import utils.Constants;

public class BasicConfigurations {

    public static WebDriver driver;
    public static ExtentSparkReporter sparkReporter;
    public static ExtentReports extentReports;
    public static ExtentTest logger;

    @BeforeTest
    public static void beforeTest(){
        sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir") +FileSystems.getDefault().getSeparator()+"reports" + FileSystems.getDefault().getSeparator()+"ExecutionReport.html");
        sparkReporter.config().setEncoding("utf-8");
        sparkReporter.config().setDocumentTitle("Automation Report");
        sparkReporter.config().setReportName("Results");
        sparkReporter.config().setTheme(Theme.DARK);
        extentReports = new ExtentReports();
        extentReports.attachReporter(sparkReporter);
        extentReports.setSystemInfo("Test run", "New feature tests");
    }

    @BeforeMethod
    public void beforeMethod(Method testMethod){
        logger = extentReports.createTest(testMethod.getName());
        setUpDriver();
        driver.manage().window().maximize();
        driver.get(Constants.URL);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @AfterMethod
    public void afterMethod(ITestResult result) {
        if (result.getStatus() == ITestResult.SUCCESS){
            String methodName = result.getMethod().getMethodName();
            String logText = "Test Case: " + methodName + " Passed";
            Markup m = MarkupHelper.createLabel(logText, ExtentColor.GREEN);
            logger.log(Status.PASS,m);
        }else if (result.getStatus() == ITestResult.FAILURE) {
            String methodName = result.getMethod().getMethodName();
            String logText = "Test Case: " + methodName + " Failed";
            Markup m = MarkupHelper.createLabel(logText, ExtentColor.RED);
            logger.log(Status.FAIL, m);
        }
        driver.quit();
    }

    @AfterTest
    public void afterTest(){
        extentReports.flush();
    }

    public void setUpDriver(){
        String dir = System.getProperty("user.dir");
        String separator = FileSystems.getDefault().getSeparator();
        String path = dir + separator + "driver" + separator + "chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", path);

        driver = new ChromeDriver();
    }
}
