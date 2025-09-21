package PageObjectModels;


import Utilities.BrowserFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class AboutPage {

    private WebDriver driver;

    public AboutPage(WebDriver driver){
        this.driver = driver;
    }

    public By about = By.linkText("ABOUT");

   public By advisoryBoardSection=By.xpath("//h2[contains(text(), 'CP-SAT Advisory Board')]");
}
