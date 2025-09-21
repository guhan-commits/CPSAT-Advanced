package Solutions;

import Utilities.BrowserFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import java.util.List;
import static org.junit.Assert.assertEquals;

public class Question3Test {
    WebDriver driver;
    Question3DataSet1 question3POM;

    @Before
    public void setUp() {
        driver = BrowserFactory.getBrowser("chrome");  // Start WebDriver
        driver.get("https://examaug2021.agiletestingalliance.org/");

        // Initialize POM class
        question3POM = new Question3DataSet1(driver);
    }

    @Test
    public void A_testGetDataSetName() throws InterruptedException {
        String heading = question3POM.getDataSetName();
        System.out.println("Page Heading: " + heading);
        assertEquals("DATA1", heading);
    }

    @Test
    public void B_testSearchData() throws InterruptedException {
        String searchTerm = "Am";
        List<String[]> results = question3POM.searchData(searchTerm);

        System.out.println("Search Results for '" + searchTerm + "':");
        for (String[] row : results) {
            System.out.println(String.join(" | ", row));
        }

        // Validate that results contain expected values
      //  assertEquals("Annex Technologies", results.get(0)[1]);
       // assertEquals("460", results.get(0)[2]);
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
