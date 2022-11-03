package sites;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import utils.SessionManager;

import java.io.IOException;

public class FBSession {

    public static void main(String[] args) throws InterruptedException, IOException {
        System.out.println(" ============ Starting Session Management Tests ===============\n\n");

        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        driver.navigate().to("https://www.facebook.com/");
//        driver.findElement(By.cssSelector("#email")).sendKeys("XXXXXX@XXX");
//        driver.findElement(By.cssSelector("#pass")).sendKeys("XXXXXX");
//        driver.findElement(By.cssSelector("button[name='login']")).click();

        Thread.sleep(5000);

        SessionManager sessionManager = new SessionManager(driver);

       // sessionManager.storeSessionFile("facebook","Afsar");

        sessionManager.usePreviousLoggedInSession("facebook");
    }
}
