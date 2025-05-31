package com.mycompany.app;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class App {
    private static final String DRIVER_PATH = "D:\\WORK ARTEM\\QA_UNN\\chromedriver-win64\\chromedriver.exe";
    
    public static void main(String[] args) {
        // Инициализация WebDriver
        System.setProperty("webdriver.chrome.driver", DRIVER_PATH);
        
        if (!new java.io.File(DRIVER_PATH).exists()) {
            System.err.println("Ошибка: ChromeDriver не найден по пути: " + DRIVER_PATH);
            return;
        }

        try {
            System.out.println("Проверка работы Selenium...");
            testSelenium();

            System.out.println("\nПолучение IP-адреса...");
            Task2.getIPAddress();

            System.out.println("\nПолучение прогноза погоды...");
            Task3.fetchWeatherForecast();
        } catch (Exception e) {
            System.err.println("Произошла критическая ошибка:");
            e.printStackTrace();
        }
    }

    public static void testSelenium() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--start-maximized");
        
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