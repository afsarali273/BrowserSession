package sites;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import utils.APIUtils;
import utils.SessionManager;

import java.time.Duration;
import java.util.Map;

public class NinetyNineCo {
    public static void main(String[] args) throws InterruptedException {

        System.out.println(" ============ Starting Session Management Tests ===============\n\n");

        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

        driver.navigate().to("https://www.99.co/");

//        driver.findElement(By.cssSelector("a[href='/signin']")).click(); // Click on Log In button on Top
//
//        driver.findElement(By.cssSelector("input[name='email_or_phone']")).sendKeys("XXXXXX@gmail.com"); // Enter Username
//        driver.findElement(By.cssSelector("[name='password']")).sendKeys("XXXXXX"); // Enter Password
//        driver.findElement(By.xpath("//button/div[contains(text(),'Log In')]")).click(); // Click Login Button

        System.out.println(" Login Successful ...");

        // Get Cookies

        SessionManager sessionManager = new SessionManager(driver);

        // Method 1:
        //sessionManager.storeSessionFile("ninetynineco","afsarali273@gmail.com");

        //sessionManager.usePreviousLoggedInSession("ninetynineco");

        // Method 2:

//  JSONObject existingSession = new JSONObject();
//        existingSession.put("path","/");
//        existingSession.put("domain",".www.99.co");
//        existingSession.put("name","li_at");
//        existingSession.put("isHttpOnly",true);
//        existingSession.put("isSecure",true);
//        existingSession.put("expiry","Sat Nov 04 00:00:50 SGT 2023");
//        existingSession.put("value","xxxxxxxxx");
//
//         sessionManager.setCookies(existingSession);


        // Method 3:

        // Completely bypass UI

        //  POST - https://www.99.co/api/v1/web/accounts/login

        JSONObject payload = new JSONObject();
        payload.put("email","xxxxx@gmail.com");
        payload.put("password","xxxx");
        String Uri = "https://www.99.co/api/v1/web/accounts/login";

        Map<String, String> cookies =  new APIUtils().postCallGetCookies(payload,Uri);

        sessionManager.byPassLoginUsingCookies(cookies,"www.99.co");


    }
}
