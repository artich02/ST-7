package com.mycompany.app;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Task3 {
    
    private static final String WEATHER_API_URL = 
        "https://api.open-meteo.com/v1/forecast?latitude=56&longitude=44" +
        "&hourly=temperature_2m,rain&current=cloud_cover" +
        "&timezone=Europe%2FMoscow&forecast_days=1&wind_speed_unit=ms";
    
    private static final String OUTPUT_FILE = "result/forecast.txt";
    
    public static void fetchWeatherForecast() {
        WebDriver driver = initializeDriver();
        try {
            processWeatherData(driver);
        } catch (Exception e) {
            handleError(e);
        } finally {
            driver.quit();
        }
    }
    
    private static WebDriver initializeDriver() {
        return new ChromeDriver();
    }
    
    private static void processWeatherData(WebDriver driver) throws Exception {
        driver.get(WEATHER_API_URL);
        String jsonData = extractJsonData(driver);
        JSONObject parsedData = parseJsonData(jsonData);
        writeWeatherReport(parsedData);
        Thread.sleep(3000);
    }
    
    private static String extractJsonData(WebDriver driver) {
        WebElement jsonElement = driver.findElement(By.tagName("pre"));
        return jsonElement.getText();
    }
    
    private static JSONObject parseJsonData(String jsonStr) throws Exception {
        JSONParser jsonParser = new JSONParser();
        return (JSONObject) jsonParser.parse(jsonStr);
    }
    
    private static void writeWeatherReport(JSONObject weatherData) throws IOException {
        JSONObject hourlyData = (JSONObject) weatherData.get("hourly");
        JSONArray times = (JSONArray) hourlyData.get("time");
        JSONArray temperatures = (JSONArray) hourlyData.get("temperature_2m");
        JSONArray rainfall = (JSONArray) hourlyData.get("rain");
        
        try (PrintWriter outputWriter = new PrintWriter(new FileWriter(OUTPUT_FILE))) {
            printWeatherTable(times, temperatures, rainfall, outputWriter);
        }
    }
    
    private static void printWeatherTable(JSONArray times, JSONArray temperatures, 
                                        JSONArray rainfall, PrintWriter writer) {
        String tableHeader = String.format("| %-3s | %-18s | %-11s | %-12s |", 
                                         "№", "Дата/время", "Температура", "Осадки (мм)");
        String tableDivider = "|-----|--------------------|-------------|--------------|";
        
        System.out.println(tableHeader);
        System.out.println(tableDivider);
        writer.println(tableHeader);
        writer.println(tableDivider);
        
        for (int index = 0; index < times.size(); index++) {
            String timestamp = (String) times.get(index);
            double temp = Double.parseDouble(temperatures.get(index).toString());
            double precip = Double.parseDouble(rainfall.get(index).toString());
            
            String tableRow = String.format("| %-3d | %-18s | %-11.1f | %-12.2f |",
                                          (index + 1), timestamp, temp, precip);
            
            System.out.println(tableRow);
            writer.println(tableRow);
        }
        
        System.out.println("\nДанные сохранены в файл " + OUTPUT_FILE);
    }
    
    private static void handleError(Exception error) {
        System.err.println("Произошла ошибка при получении данных о погоде:");
        error.printStackTrace();
    }
}