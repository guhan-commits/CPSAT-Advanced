package Solutions;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import Utilities.BrowserFactory;
import Utilities.ExcelUtility;
import Utilities.ScreenshotUtility;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class Question2 {
    WebDriver driver;
    WebDriverWait wait;
    ExcelUtility excelUtility;
    String filePath = ".\\src\\test\\resources\\data\\TestData.xlsx";
    String sheetName = "Sheet1";
    FileWriter writer;

    @BeforeClass(alwaysRun = true)
    @Parameters("browser")
    public void setUp(String browser) throws IOException {
        // Initialize WebDriver using BrowserFactory
        driver = BrowserFactory.getBrowser(browser); // Pass browser parameter from testng.xml
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://examaug2021.agiletestingalliance.org/");

        // Initialize Excel Utility
        excelUtility = new ExcelUtility(filePath, sheetName);

        // Initialize FileWriter to log results
        writer = new FileWriter(".\\src\\test\\resources\\data\\test_results.txt");
    }

    @DataProvider(name = "organizationData")
    public Object[][] getOrganizationData() {
        // Fetch data from Excel using the ExcelUtility
        return excelUtility.getExcelData();
    }

    @Test(dataProvider = "organizationData")
    public void verifyOrganizationDetails(String srNo, String organizationName, String totalScore) throws IOException, InterruptedException {
        // Navigate to "Data" menu using mouse hover
        WebElement dataMenu = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Data')]")));
        Actions action = new Actions(driver);
        action.moveToElement(dataMenu).perform();

        // Click on "Data1"
        WebElement data1Option = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Data1')]")));
        data1Option.click();
        Thread.sleep(5000);

        boolean matchFound = false;

        // Loop through up to 3 pages
        for (int page = 1; page <= 3; page++) {
            // Fetch all rows from the web table
            WebElement table = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("table")));
            List<WebElement> rows = table.findElements(By.xpath("//tbody/tr"));

            for (WebElement row : rows) {
                List<WebElement> columns = row.findElements(By.tagName("td"));

                if (columns.size() > 0) {
                    String webSrNo = columns.get(0).getText();
                    String webOrganization = columns.get(1).getText();
                    String webTotalScore = columns.get(2).getText();

                    // Check if row matches the Excel data
                    if (webSrNo.equals(srNo) && webOrganization.equals(organizationName) && webTotalScore.equals(totalScore)) {
                        matchFound = true;
                        break;
                    }
                }
            }

            // If match is found, break out of the loop
            if (matchFound) {
                break;
            }

            // If not on the last page, navigate to the next page
            if (page < 3) {
                // Locate the pagination dropdown

                WebElement pageselectiondropdown = driver.findElement(By.xpath("//div[@class='ea-advanced-data-table-pagination ea-advanced-data-table-pagination-select clearfix']//select"));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", pageselectiondropdown);
                Thread.sleep(1000);
                pageselectiondropdown.click();

                // Send Down Arrow and Enter keys
                pageselectiondropdown.sendKeys(Keys.ARROW_DOWN); // Move to option 2
                Thread.sleep(500);
                pageselectiondropdown.sendKeys(Keys.ENTER); // S

            }
        }

        // Log the result to a file
        if (matchFound) {
            writer.write(srNo + " | " + organizationName + " | " + totalScore + " | Passed\n");
        } else {
            writer.write(srNo + " | " + organizationName + " | " + totalScore + " | Failed\n");
            ScreenshotUtility.takeScreenshot(driver, srNo + "_Failed");
        }

        // Assert match found
        Assert.assertTrue(matchFound, "Matching row not found for Organization: " + organizationName);
    }

    private void waitForTableToReload(WebElement oldTable) {
        // Wait for the old table to become stale (i.e., it is removed from the DOM)
        wait.until(ExpectedConditions.stalenessOf(oldTable));

        // Wait for the new table to be present in the DOM
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("table")));
    }

    @AfterMethod
    public void captureFailure(ITestResult result) {
        // If the test fails, take a screenshot
        if (ITestResult.FAILURE == result.getStatus()) {
            ScreenshotUtility.takeScreenshot(driver, result.getName() + "_Failure");
        }
    }

    @AfterClass
    public void tearDown() throws IOException {
        // Close WebDriver
        BrowserFactory.quitBrowser();

        // Close Excel Utility
        excelUtility.close();

        // Close FileWriter
        if (writer != null) {
            writer.close();
        }
    }
}