import io.github.bonigarcia.wdm.WebDriverManager;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import utils.SessionManager;

import java.io.IOException;

public class DriverClass {
    public static void main(String[] args) throws InterruptedException, IOException {

        System.out.println(" ============ Starting Session Management Tests ===============\n\n");


        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

       driver.navigate().to("https://www.linkedin.com/");
//       driver.findElement(By.cssSelector("#session_key")).sendKeys("XXXXXX@gmail.com");
//       driver.findElement(By.cssSelector("#session_password")).sendKeys("XXXXX");
//       driver.findElement(By.cssSelector(".sign-in-form__submit-button")).click();

       Thread.sleep(5000);

       SessionManager sessionManager = new SessionManager(driver);

       //sessionManager.storeSessionFile("Afsar");

        JSONObject existingSession = new JSONObject();
        existingSession.put("path","/");
        existingSession.put("domain",".www.linkedin.com");
        existingSession.put("name","li_at");
        existingSession.put("isHttpOnly",true);
        existingSession.put("isSecure",true);
        existingSession.put("expiry","Sat Nov 04 00:00:50 SGT 2023");
        existingSession.put("value","AQEDAQvVaaYAonuGAAABhD45Ps8AAAGEYkXCz04AyC11fgkYE_Ha3oR1uje_B4SWK48Sdz3j1_gFNP9nxmqylDgibeoIj4ddjRfBBRRVQXgej2Rw0mx7u6Cf2rZiGWvZjzdGGVOSbZPhYtgPhs7QdNNv");

       // sessionManager.setCookies(existingSession);

        sessionManager.usePreviousLoggedInSession("linkedin");



    }
}
