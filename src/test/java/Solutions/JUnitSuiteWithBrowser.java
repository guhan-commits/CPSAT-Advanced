package Solutions;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        Question3Test.class,
})
public class JUnitSuiteWithBrowser {
    // No code needed, annotations handle the suite execution

    static {
        // Set default browser if not passed as a system property
        String browser = System.getProperty("browser", "chrome");
        System.setProperty("browser", browser);
        System.out.println("Running tests on browser: " + browser);
    }
}
