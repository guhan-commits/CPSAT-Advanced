package Solutions;

import Utilities.BrowserFactory;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Set;

public class Question1 {
    WebDriver driver;
    WebDriverWait wait;

    //@BeforeClass
    //public void setup() {
       // WebDriverManager.chromedriver().setup();
       // driver = new ChromeDriver();
        //driver.manage().window().maximize();
       // wait = new WebDriverWait(driver, 10);
    //}


    @BeforeClass(alwaysRun = true)
    @Parameters("browser")
    public void setup(String browser) {
        driver = BrowserFactory.getBrowser(browser); // Pass the browser parameter from testng.xml
        //aboutpage=new AboutPage(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://examaug2021.agiletestingalliance.org/");
    }

    @Test
    public void FindRespectiveURL() throws InterruptedException, IOException {
        // Step 1: Navigate to the website
        //driver.get("https://mockexam2cpsat.agiletestingalliance.org/");

        WebElement quicklinksupdated = driver.findElement(By.xpath("//h2[normalize-space()='Quick Links']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", quicklinksupdated);

        // Step 2: Scroll to Footer
      //  JavascriptExecutor js = (JavascriptExecutor) driver;
       // js.executeScript("window.scrollTo(0, document.body.scrollHeight);");


        Thread.sleep(1000);  // Wait to load footer elements

        //Step 2.1: Take screenshot PART A
        File QuickLinksScreenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(QuickLinksScreenshot, new File("./src/test/resources/Screenshots/"+"Quick links"+ ".png"));

        //Step 2.2: get all the hrefs of the quick link section PART B
        List<WebElement> quickLinks = driver.findElements(By.cssSelector("div.elementor-image-box-content a"));

        for (WebElement link : quickLinks) {
            System.out.println(link.getAttribute("href"));
        }



        //Step 2.3: Scroll down to the place where Vision is mentioned, extract the integer from 24+ Countries and assert that the number extracted is 24 (2 marks) PART C
        // Scroll down to the "Vision" section
       WebElement visionElement = driver.findElement(By.xpath("//h3[normalize-space()='OUR VISION IS:']"));
       ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", visionElement);
        //Thread.sleep(1000);




      // Extract the text
        WebElement visionText=driver.findElement(By.xpath("//strong[normalize-space()='24+']"));
       String visionTextvalue = visionText.getText();
       // System.out.println("Extracted Text: " + visionText);

      // Extract the integer from "24+ Countries"
        String numberOnly = visionTextvalue.replaceAll("[^0-9]", "");  // Remove non-numeric characters
       int extractedNumber = Integer.parseInt(numberOnly);

      // Assert that the extracted number is 24
       Assert.assertEquals(extractedNumber, 24, "Extracted number is not 24!");

       System.out.println("Assertion passed: Extracted number is 24.");



       // Thread.sleep(3000);





        // Step 3: Find all social media icons and print their URLs PART D
            List<WebElement> socialMediaIcons = driver.findElements(By.cssSelector(".elementor-social-icons-wrapper a"));
        System.out.println("Social Media Links:");





        for (WebElement icon : socialMediaIcons) {
            System.out.println(icon.getAttribute("href"));
        }
        Thread.sleep(5000);
        //driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        try {
            WebElement closeButton = driver.findElement(By.xpath("//i[@class='eicon-close']"));
            if (closeButton.isDisplayed()) {
                closeButton.click();
                System.out.println("Popup closed successfully.");
            }
        } catch (NoSuchElementException e) {
            System.out.println("Popup not found, proceeding with execution.");
        }

        // Step 4: Open each social media icon in a new tab and print its title PART D
        String originalWindow = driver.getWindowHandle();
        Actions action = new Actions(driver);

        System.out.println("\nSocial Media Page Titles:");
        for (WebElement icon : socialMediaIcons) {

            action.contextClick(icon).perform();  // Right-click
           // Thread.sleep(1000);
            action.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).perform();  // Open in new window
           Thread.sleep(2000);



            // Switch to new tab
            Set<String> allWindows = driver.getWindowHandles();
            for (String window : allWindows) {
                if (!window.equals(originalWindow)) {
                    driver.switchTo().window(window);
                    String Title = driver.getTitle();
                    //System.out.println(driver.getTitle());  // Print Title


                    System.out.println(Title);

                    String fileName;
                    if (Title.toLowerCase().contains("linkedin".toLowerCase())) {
                        fileName = "LinkedIn";
                    } else if(Title.toLowerCase().contains("instagram".toLowerCase())) {
                        fileName = "instagram";
                    }
                     else if(Title.toLowerCase().contains("YouTube".toLowerCase())) {
                    fileName = "YouTube";
                    }
                    else if(Title.toLowerCase().contains("Facebook".toLowerCase())) {
                        fileName = "Facebook";
                    }
                    else if(Title.toLowerCase().contains("Telegram".toLowerCase())) {
                        fileName = "Telegram";
                    }
                   else {
                        fileName = "X";
                    }
                    Thread.sleep(3000);
                    File TitleScreenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                    FileUtils.copyFile(TitleScreenshot, new File("./src/test/resources/Screenshots/"+fileName+".png"));
                    //FileUtils.copyFile(QuickLinksScreenshot, new File("./src/test/resources/Screenshots/"+"Quick links"+ ".png"));
                    driver.close();
                    driver.switchTo().window(originalWindow);
                    break;
                }
            }
        }

    }

    @AfterClass
    public void teardown() {
        driver.quit();
    }
}
