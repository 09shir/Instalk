package com.instalk.backend.controller;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.time.Duration;

@RestController
@CrossOrigin(origins="*")
public class ScraperController {

    private String os = System.getProperty("os.name").toLowerCase();
    
    // @GetMapping("/ins")
    // public Object scrape(@RequestBody String username, @RequestBody String password, @RequestBody String targetUsername){

    @GetMapping("/ins")
    public Object scrape(@RequestBody Map<String, String> request){

        String username = request.get("username");
        String password = request.get("password");
        String targetUsername = request.get("targetUsername");

        if(os.contains("windows"))
            System.setProperty("webdriver.chrome.driver", "develop/chrome/chromedriver.exe");
        else if (os.contains("linux"))
            System.setProperty("webdriver.chrome.driver", "target/classes/develops/chromedriver.exe");

        WebDriver driver = new ChromeDriver();

        driver.get("https://www.instagram.com/");

        // Wait for the elements to load
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));

        // Enter username and password
        driver.findElement(By.name("username")).sendKeys(username);
        driver.findElement(By.name("password")).sendKeys(password);

        // Click the login button
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // Wait for and click the "Not Now" button
        String notNowButtonXPath = "//div[contains(text(),'Not now')]";
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(notNowButtonXPath)));
        driver.findElement(By.xpath(notNowButtonXPath)).click();

        String script = 
            "return new Promise(async (resolve, reject) => {" +

                "let followers = [];" +
                "let followings = [];" +

                "try {" +
                    "const userQueryRes = await fetch(" +
                        " 'https://www.instagram.com/web/search/topsearch/?query=" + targetUsername + "'" +
                    ");" +
                    "const userQueryJson = await userQueryRes.json();" +
                    "const userId = userQueryJson.users[0].user.pk;" +

                    "let after = null;" +
                    "let has_next = true;" +

                    "while (has_next) {" +
                        "const res = await fetch(" +
                            " 'https://www.instagram.com/graphql/query/?query_hash=c76146de99bb02f6415203be841dd25a&variables=' + " +
                                "encodeURIComponent(" +
                                    "JSON.stringify({" +
                                        "id: userId," +
                                        "include_reel: true," +
                                        "fetch_mutual: true," +
                                        "first: 50," +
                                        "after: after," +
                                    "})" +
                                ")" +
                        ");" +
                        "const data = await res.json();" +
                        "has_next = data.data.user.edge_followed_by.page_info.has_next_page;" +
                        "after = data.data.user.edge_followed_by.page_info.end_cursor;" +
                        "followers = followers.concat(" +
                            "data.data.user.edge_followed_by.edges.map(({ node }) => ({" +
                                "username: node.username," +
                                "full_name: node.full_name," +
                            "}))" +
                        ");" +
                        "if (followers.length > 1000){" +
                            "followers = 'limits reached';" +
                            "break;" +
                        "}" +
                    "}" +

                    "after = null;" +
                    "has_next = true;" +

                    "while (has_next) {" +
                        "const res = await fetch(" +
                            " 'https://www.instagram.com/graphql/query/?query_hash=d04b0a864b4b54837c0d870b0e77e076&variables= ' + " +
                                "encodeURIComponent(" +
                                    "JSON.stringify({" +
                                        "id: userId," +
                                        "include_reel: true," +
                                        "fetch_mutual: true," +
                                        "first: 50," +
                                        "after: after," +
                                    "})" +
                                ")" +
                        ");" +
                        "const data = await res.json();" +
                        "has_next = data.data.user.edge_follow.page_info.has_next_page;" +
                        "after = data.data.user.edge_follow.page_info.end_cursor;" +
                        "followings = followings.concat(" +
                            "data.data.user.edge_follow.edges.map(({ node }) => ({" +
                                "username: node.username," +
                                "full_name: node.full_name," +
                            "}))" +
                        ");" +
                        "if (followings.length > 1000){" +
                            "followings = 'limits reached';" +
                            "break;" +
                        "}" +
                    "}" +

                    "resolve({ followers, followings });" +
                "} catch (err) {" +
                    "reject(err);" +
                "}" +
            "});";

        JavascriptExecutor js = (JavascriptExecutor) driver;
        Object data = js.executeScript(script);
        driver.quit();
        return data;
        
    }
}
