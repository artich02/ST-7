package com.mycompany.app;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class App {
    public static void main(String[] args) {
        String driverPath = "D:\\WORK ARTEM\\QA_UNN\\chromedriver-win64\\chromedriver.exe";
        
        System.setProperty("webdriver.chrome.driver", driverPath);
        
        if (!new java.io.File(driverPath).exists()) {
            System.err.println("Ошибка: ChromeDriver не найден по пути: " + driverPath);
            return;
        }

        System.out.println("Проверка работы Selenium...");
        testSelenium();

        System.out.println("\nПолучение IP-адреса...");
        Task2.getIPAddress();

        System.out.println("\nПолучение прогноза погоды...");
        Task3.getWeatherForecast();
    }

    public static void testSelenium() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        
        WebDriver driver = new ChromeDriver(options);
        try {
            driver.get("https://www.calculator.net/password-generator.html");
            System.out.println("Успешно загружена страница: " + driver.getTitle());
            Thread.sleep(4000);
        } catch (Exception e) {
            System.out.println("Ошибка в тесте Selenium:");
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}