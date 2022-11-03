package sites;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import utils.SessionManager;

public class FlipKart {

    public static void main(String[] args) throws InterruptedException {
        System.out.println(" ============ Starting Session Management Tests ===============\n\n");

        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        driver.navigate().to("https://www.flipkart.com/");
//        driver.findElement(By.cssSelector("#email")).sendKeys("XXXXXX@XXX");
//        driver.findElement(By.cssSelector("#pass")).sendKeys("XXXXXX");
//        driver.findElement(By.cssSelector("button[name='login']")).click();

        Thread.sleep(5000);

        SessionManager sessionManager = new SessionManager(driver);

        // sessionManager.storeSessionFile("facebook","Afsar");

        //SN=

        sessionManager.usePreviousLoggedInSession("flipkart");
    }
}
