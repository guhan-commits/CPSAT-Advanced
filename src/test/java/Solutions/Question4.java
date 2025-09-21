package Solutions;

import Utilities.BrowserFactory;
import Utilities.ExcelUtility;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.io.FileHandler;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Question4 {
    WebDriver driver;
    WebDriverWait wait;
    Actions action;

    // Constructor
    //public Solution4(WebDriver driver) {
    //this.driver = driver;
    //this.wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // WebDriverWait
    //this.action = new Actions(driver);
    //  }

    // Open URL
    public void openWebsite() {
        driver.get("https://examaug2021.agiletestingalliance.org/");
    }
    @BeforeClass(alwaysRun = true)
    @Parameters("browser")
    public void setUp(String browser) throws IOException {
        // Initialize WebDriver using BrowserFactory
        driver = BrowserFactory.getBrowser(browser); // Pass browser parameter from testng.xml
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://examaug2021.agiletestingalliance.org/");
        this.action = new Actions(driver);

    }

    @Test
    // Step a: Mouse hover on "Certifications" and click "CP-SAT"
    public void navigateToCPSATGetFAQTextsCapturebackgroundcolors() throws InterruptedException {
        WebElement certifications = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Certifications')]")));
        // action.moveToElement(certifications).perform(); // Hover
        certifications.click();
        WebElement cpsat = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'CP-SAT')]")));
        cpsat.click(); // Click
        WebElement closeButton = driver.findElement(By.cssSelector(".dialog-close-button.dialog-lightbox-close-button"));

// Click the close button
        closeButton.click();

        //how-is-it-useful
        WebElement useful = driver.findElement(By.xpath("//div[@id='how-is-it-useful']"));
        action.moveToElement(useful).perform();
        String initialColor = useful.getCssValue("background-color");
        System.out.println("Initial Background RGB: " + initialColor);

        useful.click();
        Thread.sleep(1000);
        String changedColor = useful.getCssValue("background-color");
        System.out.println("Changed Background RGB: " + changedColor);

        if (!initialColor.equals(changedColor)) {
            System.out.println("✅ Background colors changed successfully! for how is it useful");
        } else {
            System.out.println("❌ Background colors did NOT change!for how is it useful");
        }

        WebElement faqContent = driver.findElement(By.xpath("//div[contains(@class,'eael-accordion-content')]"));
        action.moveToElement(faqContent).perform();

        System.out.println(faqContent.getAttribute("textContent"));
        useful.click();
        Thread.sleep(1000);

        //AmIEligible


        WebElement AmIEligible = driver.findElement(By.xpath("//div[@id='am-i-eligible']"));
        action.moveToElement(AmIEligible).perform();
        String initialColor1 = AmIEligible.getCssValue("background-color");
        System.out.println("Initial Background RGB: " + initialColor1);

        AmIEligible.click();
        Thread.sleep(1000);
        String changedColor1 = AmIEligible.getCssValue("background-color");
        System.out.println("Changed Background RGB: " + changedColor1);

        if (!initialColor1.equals(changedColor1)) {
            System.out.println("✅ Background colors changed successfully! for AmIEligible");
        } else {
            System.out.println("❌ Background colors did NOT change!for AmIEligible");
        }

        WebElement faqContentAmIEligible = driver.findElement(By.xpath("//div[@id='elementor-tab-content-1002']"));
        //action.moveToElement(faqContentAmIEligible).perform();
        System.out.println(faqContentAmIEligible.getAttribute("textContent"));
        AmIEligible.click();

        //duration

        WebElement duration = driver.findElement(By.xpath("//div[@id='duration']"));
        action.moveToElement(duration).perform();
        String initialColor2 = duration.getCssValue("background-color");
        System.out.println("Initial Background RGB: " + initialColor2);

        duration.click();
        Thread.sleep(1000);
        String changedColor2 = duration.getCssValue("background-color");
        System.out.println("Changed Background RGB: " + changedColor2);

        if (!initialColor2.equals(changedColor2)) {
            System.out.println("✅ Background colors changed successfully! for duration");
        } else {
            System.out.println("❌ Background colors did NOT change!for duration");
        }

        WebElement faqContentduration = driver.findElement(By.xpath("//div[@id='elementor-tab-content-1003']"));
        // action.moveToElement(faqContentduration).perform();
        System.out.println(faqContentduration.getAttribute("textContent"));
        duration.click();




    }
    @Test

    // Step b: Scroll to "News!" ticker section
    public void scrollToNewsTicker() {
        //WebElement newsTicker = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("news-ticker")));
        //WebElement newsTicker = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("news-ticker")));
       // ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", newsTicker);

        // Wait for the "News!" element to be visible using the relative XPath
        WebElement newsTicker = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[normalize-space()='News!']")));

// Perform actions on the "News!" element (e.g., scroll, click, etc.)
        Actions action = new Actions(driver);
        action.moveToElement(newsTicker).perform();
    }
    @Test
    // Step c: Take a screenshot showing the "Upcoming ticker"
    public void takeNewsTickerScreenshot() throws IOException {
        //File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
       // FileHandler.copy(screenshot, new File("News_Ticker.png"));
        File News_TickerScreenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(News_TickerScreenshot, new File("./src/test/resources/Screenshots/"+"News_Ticker"+ ".png"));

        System.out.println("Screenshot saved: News_Ticker.png");
    }
    //@Test
    // Step d: Get all messages from the "News!" ticker
    public Set<String> getTickerMessages() {
        List<WebElement> tickerElements = driver.findElements(By.cssSelector("#news-ticker span"));
        Set<String> messages = new HashSet<>();
        for (WebElement element : tickerElements) {
            messages.add(element.getText().trim());
        }
        return messages;
    }
    //@Test
    // Step e: Check if all ticker messages are the same
    public void compareTickerMessages(Set<String> messages) {
        if (messages.size() > 1) {
            System.out.println("The ticker messages are not the same!");
        } else {
            System.out.println("All ticker messages are the same.");
        }
    }
   // @Test
    // Step f: Get and print `href` links from the ticker messages
    public void printTickerHrefs() {
        List<WebElement> tickerLinks = driver.findElements(By.cssSelector("#news-ticker a"));
        for (WebElement link : tickerLinks) {
            String href = link.getAttribute("href");
            if (href != null && !href.isEmpty()) {
                System.out.println("Ticker Message HREF: " + href);
            }
        }
    }
    //@Test
    // Step g: Click `+` for "How is it useful?", "Am I eligible?", "Duration?" and get the text
    public void getFAQTexts() throws InterruptedException {
        // String[] faqItems = {"How is it useful?", "Am I Eligible?", "Duration?"};

        //for (String item : faqItems) {
        // try {
        // Find the FAQ accordion header by matching the text
        //WebElement faq = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(@class,'eael-accordion-header')][span[contains(text(),'" + item + "')]]")));
        //  WebElement faq = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(@class,'eael-accordion-header')][span[contains(text(),'How is it useful?')]]")));
        // Scroll into view before clicking
        //Actions action = new Actions(driver);
        // action.moveToElement(faq).perform();
        // ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", faq);
        // ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", faqElement);
        //Thread.sleep(1000); // Allow UI animation to settle

        // Click the accordion to expand
        //faq.click();
        // Thread.sleep(500); // Allow expansion animation

        // Locate the corresponding expanded content
        //WebElement faqText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'eael-accordion-content')][@aria-labelledby='" + faq.getAttribute("id") + "']")));

        // Print extracted FAQ text
        // System.out.println("FAQ: " + item + " → " + faqText.getText());

        //} catch (Exception e) {
        // System.err.println("Error processing FAQ: " + item + " - " + e.getMessage());


        // }
        // }
        Thread.sleep(5000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='how-is-it-useful']")));
        WebElement useful = driver.findElement(By.xpath("//div[@id='how-is-it-useful']"));
        action.moveToElement(useful).perform();
        useful.click();
        Thread.sleep(2000);
        WebElement faqContent = driver.findElement(By.xpath("//div[contains(@class,'eael-accordion-content')]"));
        action.moveToElement(faqContent).perform();
        System.out.println(faqContent.getAttribute("textContent"));
        useful.click();
        Thread.sleep(2000);

        WebElement AmIEligible = driver.findElement(By.xpath("//div[@id='am-i-eligible']"));
        action.moveToElement(AmIEligible).perform();
        AmIEligible.click();
        Thread.sleep(2000);
        WebElement faqContentAmIEligible = driver.findElement(By.xpath("//div[@id='elementor-tab-content-1002']"));
        //action.moveToElement(faqContentAmIEligible).perform();
        System.out.println(faqContentAmIEligible.getAttribute("textContent"));
        AmIEligible.click();

        WebElement duration = driver.findElement(By.xpath("//div[@id='duration']"));
        action.moveToElement(duration).perform();
        duration.click();
        Thread.sleep(2000);
        WebElement faqContentduration = driver.findElement(By.xpath("//div[@id='elementor-tab-content-1003']"));
        // action.moveToElement(faqContentduration).perform();
        System.out.println(faqContentduration.getAttribute("textContent"));
        duration.click();


    }


    // Step h: Capture Background RGB colors before & after clicking

    public void captureBackgroundColors() {
        try {

            WebElement faqButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(@class,'eael-accordion-header')][span[contains(text(),'How is it useful?')]]")));

            // Scroll into view before clicking
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", faqButton);
            Thread.sleep(1000); // Wait for UI to stabilize

            // Get initial background color
            String initialColor = faqButton.getCssValue("background-color");
            System.out.println("Initial Background RGB: " + initialColor);

            // Click to expand
            faqButton.click();
            Thread.sleep(500); // Allow animation

            // Wait for color change
            wait.until(ExpectedConditions.attributeToBeNotEmpty(faqButton, "background-color"));

            // Get changed color
            String changedColor = faqButton.getCssValue("background-color");
            System.out.println("Changed Background RGB: " + changedColor);

            // Validate that colors have changed
            if (!initialColor.equals(changedColor)) {
                System.out.println("✅ Background colors changed successfully!");
            } else {
                System.out.println("❌ Background colors did NOT change!");
            }

        } catch (Exception e) {
            System.err.println("Error capturing background colors: " + e.getMessage());
        }
    }

    // Run all steps
    public void executeTest() {
        try {
            //openWebsite();
            //navigateToCPSAT();
            scrollToNewsTicker();
            takeNewsTickerScreenshot();
            Set<String> messages = getTickerMessages();
            compareTickerMessages(messages);
            printTickerHrefs();
            getFAQTexts();
            captureBackgroundColors();
            System.out.println("✅ All steps completed successfully. Bonus marks earned!");
        } catch (Exception e) {
            System.out.println("❌ Test failed: " + e.getMessage());
        }
    }
}
