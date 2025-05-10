package com.mycompany.app;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Task2 {
    public static void getIPAddress() {
        WebDriver webDriver = new ChromeDriver();
        try {
            webDriver.get("https://api.ipify.org/?format=json");
            WebElement element = webDriver.findElement(By.tagName("pre"));

            String jsonStr = element.getText();
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(jsonStr);

            String ipAddress = (String) obj.get("ip");
            System.out.println("Ваш IP-адрес: " + ipAddress);

            Thread.sleep(2000); // Пауза для возможности увидеть результат
        } catch (Exception e) {
            System.out.println("Ошибка при получении IP-адреса:");
            System.out.println(e.toString());
        } finally {
            webDriver.quit();
        }
    }
}