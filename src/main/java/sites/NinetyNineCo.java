package sites;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import utils.APIUtils;
import utils.SessionManager;

import java.util.Map;

public class NinetyNineCo {
    public static void main(String[] args) throws InterruptedException {

        System.out.println(" ============ Starting Session Management Tests ===============\n\n");

        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        driver.navigate().to("https://www.99.co/");
//        driver.findElement(By.cssSelector("#email")).sendKeys("XXXXXX@XXX");
//        driver.findElement(By.cssSelector("#pass")).sendKeys("XXXXXX");
//        driver.findElement(By.cssSelector("button[name='login']")).click();

        Thread.sleep(5000);

        JSONObject payload = new JSONObject();
        payload.put("email","XXXX");
        payload.put("password","XXXX");
        String Uri = "https://www.99.co/api/v1/web/accounts/login";

       Map<String, String> cookies =  new APIUtils().postCallGetCookies(payload,Uri);

        SessionManager sessionManager = new SessionManager(driver);

        sessionManager.byPassLoginUsingCookies(cookies,"www.99.co");

    }
}
