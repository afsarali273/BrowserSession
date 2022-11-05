# BrowserSession
By Pass UI login using api and cookies in selenium

Apply cookies for single cookies json object.

```java

       JSONObject existingSession = new JSONObject();
        existingSession.put("path","/");
        existingSession.put("domain",".www.99.co");
        existingSession.put("name","li_at");
        existingSession.put("isHttpOnly",true);
        existingSession.put("isSecure",true);
        existingSession.put("expiry","Sat Nov 04 00:00:50 SGT 2023");
        existingSession.put("value","xxxxxxxxx");

       // Apply cookies to browser for above cookies
       sessionManager.setCookies(existingSession);
```


```java 
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
```
