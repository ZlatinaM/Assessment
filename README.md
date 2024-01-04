Test environment setup note:
I have created a Maven project for managing all dependencies -> TestNg as a testing framework, Selenium WebDriver for interaction with the web elements and ExtentReports as a reporting tool. I am using chromedriver for running the test and the chromedriver.exe file is located in the project.
I am using Page Object Model as a design pattern -> in the page classes I have defined the web elements and the methods for the given page. The web elements are initialized with the Page Factory pattern (@FindBy)
The all complexity related to the test is hidden in the page classes while the test class (End2EndTest) is simple.
In the BasicConfiguration fale are the common methods related to setup the driver and the reporting
An Html report is generated after every execution

On the BrokersPage file, line 52 there is one Thread.sleep method - I know it is not a good practice to use it, but in this case I was not able to find any other way to stop the execution for a while. I tried explicite wait with different conditions, but Thread.sleep was the only one that worked in this case.
