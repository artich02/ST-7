package com.mycompany.app;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileWriter;
import java.io.PrintWriter;

public class Task3 {
    public static void getWeatherForecast() {
        WebDriver webDriver = new ChromeDriver();
        try {
            String url = "https://api.open-meteo.com/v1/forecast?latitude=56&longitude=44&hourly=temperature_2m,rain&current=cloud_cover&timezone=Europe%2FMoscow&forecast_days=1&wind_speed_unit=ms";
            webDriver.get(url);

            // Получаем элемент с JSON-данными
            WebElement element = webDriver.findElement(By.tagName("pre"));
            String jsonStr = element.getText();

            // Парсим JSON
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(jsonStr);

            // Извлекаем необходимые данные
            JSONObject hourly = (JSONObject) obj.get("hourly");
            JSONArray timeArray = (JSONArray) hourly.get("time");
            JSONArray temperatureArray = (JSONArray) hourly.get("temperature_2m");
            JSONArray rainArray = (JSONArray) hourly.get("rain");

            // Открываем файл для записи результатов
            PrintWriter writer = new PrintWriter(new FileWriter("result/forecast.txt"));

            // Выводим заголовок таблицы
            String header = String.format("| %-3s | %-18s | %-11s | %-12s |", "№", "Дата/время", "Температура", "Осадки (мм)");
            String separator = "|-----|--------------------|-------------|--------------|";

            System.out.println(header);
            System.out.println(separator);

            writer.println(header);
            writer.println(separator);

            // Заполняем таблицу данными
            for (int i = 0; i < timeArray.size(); i++) {
                String time = (String) timeArray.get(i);
                Double temperature = Double.parseDouble(temperatureArray.get(i).toString());
                Double rain = Double.parseDouble(rainArray.get(i).toString());

                String row = String.format("| %-3d | %-18s | %-11.1f | %-12.2f |",
                        (i + 1), time, temperature, rain);

                System.out.println(row);
                writer.println(row);
            }

            writer.close();
            System.out.println("\nРезультаты сохранены в файл result/forecast.txt");

            Thread.sleep(3000); // Пауза для возможности увидеть результат

        } catch (Exception e) {
            System.out.println("Ошибка при получении прогноза погоды:");
            System.out.println(e.toString());
        } finally {
            webDriver.quit();
        }
    }
}