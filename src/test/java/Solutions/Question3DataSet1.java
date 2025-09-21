package Solutions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.interactions.Actions;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Question3DataSet1 {
    WebDriver driver;
    WebDriverWait wait;

    // Constructor
    public Question3DataSet1(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Initialize WebDriverWait
    }

    // Locators
    private By pageHeading = By.xpath("//div[@class='elementor-slide-heading']");


    private By searchBox = By.xpath("//input[@type='search']");
    private By tableRows = By.xpath("//table/tbody/tr");
    private By dataMenu = By.xpath("//a[contains(text(),'Data')]");
    private By data1Option = By.xpath("//a[contains(text(),'Data1')]");

    // Function to navigate to "Data1" and get dataset name
    public String getDataSetName() throws InterruptedException {
        // Navigate to "Data" menu using mouse hover
        WebElement menu = wait.until(ExpectedConditions.elementToBeClickable(dataMenu));
        Actions action = new Actions(driver);
        action.moveToElement(menu).perform();

        // Click on "Data1"
        WebElement data1 = wait.until(ExpectedConditions.elementToBeClickable(data1Option));
        data1.click();

        Thread.sleep(4000);

        // Wait for the page to load
       wait.until(ExpectedConditions.visibilityOfElementLocated(pageHeading));

        return driver.findElement(pageHeading).getText();
    }

    // Function to search data in the table
    public List<String[]> searchData(String searchString) throws InterruptedException {
        List<String[]> matchingRows = new ArrayList<>();
        //Actions action = new Actions(driver);
        /*
        action.moveToElement(searchBox).perform();
        driver.findElement(searchBox).perform();
         Enter search text
        */
        WebElement menu = wait.until(ExpectedConditions.elementToBeClickable(dataMenu));
        Actions action = new Actions(driver);
        action.moveToElement(menu).perform();

        // Click on "Data1"
       WebElement data1 = wait.until(ExpectedConditions.elementToBeClickable(data1Option));
        data1.click();

        Thread.sleep(4000);
        driver.findElement(searchBox).clear();
        driver.findElement(searchBox).sendKeys(searchString);

        // Wait for search results to update
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(tableRows, 0));

        // Get the table rows after search
        List<WebElement> rows = driver.findElements(tableRows);

        // Iterate over each row and collect data
        for (WebElement row : rows) {
            List<WebElement> columns = row.findElements(By.tagName("td"));
            String[] rowData = new String[columns.size()];
            for (int i = 0; i < columns.size(); i++) {
                rowData[i] = columns.get(i).getText();
            }
            matchingRows.add(rowData);
        }
        return matchingRows;
    }
}
