package com.mycompany.app;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Task2 {
    public static void getIPAddress() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.setHeadless(true); // Работает в фоновом режиме
        
        WebDriver driver = new ChromeDriver(options);
        try {
            driver.get("https://api.ipify.org/?format=json");
            WebElement element = driver.findElement(By.tagName("pre"));

            String jsonStr = element.getText();
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(jsonStr);

            String ipAddress = (String) obj.get("ip");
            System.out.println("Ваш IP-адрес: " + ipAddress);

            Thread.sleep(2000);
        } catch (Exception e) {
            System.err.println("Ошибка при получении IP-адреса:");
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}