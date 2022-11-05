package utils;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.html5.SessionStorage;
import org.openqa.selenium.html5.WebStorage;
import org.openqa.selenium.remote.Augmenter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

public class SessionManager {

    private WebDriver driver;
    private WebStorage webStorage;

    public SessionManager(WebDriver driver){
        this.driver= driver;
        webStorage = (WebStorage) new Augmenter().augment(driver);
    }

    // Get Cookies
    private JSONArray getCookiesData() {
        JSONArray cookies = new JSONArray();
        driver.manage().getCookies().stream()
                .forEach(
                        x -> {
                            JSONObject json = new JSONObject();
                            json.put("name", x.getName());
                            json.put("value", x.getValue());
                            json.put("path", x.getPath());
                            json.put("domain", x.getDomain());
                            json.put("expiry", x.getExpiry());
                            json.put("isSecure", x.isSecure());
                            json.put("isHttpOnly", x.isHttpOnly());
                            cookies.put(json);
                        });
        return cookies;
    }

    // Get Local storage
    private JSONObject getLocalStorageData() {
        LocalStorage localStorage = webStorage.getLocalStorage();
        JSONObject localStorageJsonObj = new JSONObject();
        localStorage.keySet().stream()
                .forEach(x -> localStorageJsonObj.put(x, localStorage.getItem(x)));
        return localStorageJsonObj;
    }

    // Get Session storage
    private JSONObject getSessionStorageData() {
        SessionStorage sessionStorage = webStorage.getSessionStorage();
        JSONObject sessionStorageJsonObj = new JSONObject();
        sessionStorage.keySet().stream()
                .forEach(x -> sessionStorageJsonObj.put(x, sessionStorage.getItem(x)));
        return sessionStorageJsonObj;
    }

    // Get Session Data
    private JSONObject getSessionData() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("session_storage", getSessionStorageData());
        jsonObject.put("local_storage", getLocalStorageData());
        jsonObject.put("cookies", getCookiesData());
        return jsonObject;
    }

    // Store Session Data in a File If login success for each time
    public void storeSessionFile(String fileName,String userName) throws IOException {
        // TODO: Need to handle for multiple users
        if (Files.exists(Paths.get(System.getProperty("user.dir") + "/"+fileName+".json"))) {
            Files.deleteIfExists(Paths.get(System.getProperty("user.dir") + "/"+fileName+".json"));
        }

        JSONObject sessionObj = new JSONObject();
        sessionObj.put("username", userName);// Optional
        sessionObj.put("createdAt", LocalDateTime.now()); // Optional
        sessionObj.put("session_data", getSessionData());

        System.out.println(" JSON Obj : " + sessionObj);
        JSONUtils.writeJSONObjectToFile(sessionObj, "./"+fileName+".json");
    }

    private void applyCookiesToCurrentSession(JSONObject jsonObject) {

        JSONArray cookiesArray = jsonObject.getJSONArray("cookies");
        for (int i = 0; i < cookiesArray.length(); i++) {
            JSONObject cookies = cookiesArray.getJSONObject(i);
            Cookie ck =
                    new Cookie.Builder(cookies.get("name").toString(), cookies.get("value").toString())
                            .path(cookies.get("path").toString())
                            .domain(cookies.get("domain").toString())
                            .expiresOn(
                                    !cookies.has("expiry") ? null : new Date(new Date().getTime() + 3600 * 1000))
                            .isSecure((Boolean) cookies.get("isSecure"))
                            .isHttpOnly((Boolean) cookies.get("isHttpOnly"))
                            .build();
            driver.manage().addCookie(ck);
        }
    }

    private void applyLocalStorage(JSONObject sessionData) {
        JSONObject localStorageObj = sessionData.getJSONObject("local_storage");
        localStorageObj.keySet().stream()
                .forEach(
                        k -> {
                            webStorage.getLocalStorage().setItem(k, localStorageObj.get(k).toString());
                        });
    }

    private void applySessionStorage(JSONObject sessionData) {
        JSONObject sessionStorageObj = sessionData.getJSONObject("session_storage");
        sessionStorageObj.keySet().stream()
                .forEach(
                        k -> {
                            webStorage.getSessionStorage().setItem(k, sessionStorageObj.get(k).toString());
                        });
    }

    public void usePreviousLoggedInSession(String fileName) {

        driver.manage().getCookies().clear();
        // Read Json file
        JSONObject jsonObj = null;
        jsonObj = JSONUtils.parseJsonFile(System.getProperty("user.dir") + "/"+fileName+".json");
        // jsonObj = (JSONObject) TestDataHelper.getDataInSuiteScope("user_session");

        JSONObject sessionData = jsonObj.getJSONObject("session_data");

        // Apply Cookies
        applyCookiesToCurrentSession(sessionData);

        // Apply Local storage
        applyLocalStorage(sessionData);

        // Apply Session storage
        applySessionStorage(sessionData);

        // Page Refresh
        driver.navigate().refresh();
    }

    public void setCookies(JSONObject cookies){
        System.out.println(" ======= Deleting all existing cookies ======== ");

        driver.manage().deleteAllCookies();

        Cookie ck =
                new Cookie.Builder(cookies.get("name").toString(), cookies.get("value").toString())
                        .path(cookies.get("path").toString())
                        .domain(cookies.get("domain").toString())
                        .expiresOn(
                                !cookies.has("expiry") ? null : new Date(new Date().getTime() + 3600 * 1000))
                        .isSecure((Boolean) cookies.get("isSecure"))
                        .isHttpOnly((Boolean) cookies.get("isHttpOnly"))
                        .build();
        driver.manage().addCookie(ck);

        System.out.println(" Cookies added success !! ");

        driver.navigate().refresh();
    }

    public void byPassLoginUsingCookies(Map<String,String> cookies,String domain) throws InterruptedException {
        System.out.println(" ======= Deleting all existing cookies ======== ");

        driver.manage().deleteAllCookies();

        cookies.keySet().forEach( k -> {

           String value =  cookies.get(k);

            Cookie ck =
                    new Cookie.Builder(k, value) // cookies name , value
                            .path("/")
                            .domain(domain)
                            .expiresOn(new Date(new Date().getTime() + 3600 * 1000))
                            .isSecure(false)
                            .isHttpOnly(false)
                            .build();
            driver.manage().addCookie(ck);

        });

        System.out.println(" Cookies added success !! ");

        driver.navigate().refresh();

        Thread.sleep(10000);
    }
}
