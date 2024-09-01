package com.manage.employee.Services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class WeatherInfoService {
    @Value("${app.open.weather.key}")
    private static String API_KEY;
    private static final String API_URL = "https://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&exclude=minutely,hourly,daily,alerts&appid=" + API_KEY;

    public static void getWeatherInfo(String lat,String lon) {
        try {
            URL url = new URL(String.format(API_URL,lat,lon));
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            System.out.println("Response: " + response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public static void main(String[] args) {
////        getWeatherInfo("16.828885","96.269391");
//        String response = "{\"coord\":{\"lon\":96.2694,\"lat\":16.8289},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04d\"}],\"base\":\"stations\",\"main\":{\"temp\":306.27,\"feels_like\":313.27,\"temp_min\":306.27,\"temp_max\":306.27,\"pressure\":1005,\"humidity\":79,\"sea_level\":1005,\"grnd_level\":1004},\"visibility\":7000,\"wind\":{\"speed\":1.54,\"deg\":310},\"clouds\":{\"all\":75},\"dt\":1723361097,\"sys\":{\"type\":1,\"id\":9322,\"country\":\"MM\",\"sunrise\":1723331862,\"sunset\":1723377761},\"timezone\":23400,\"id\":1295395,\"name\":\"Syriam\",\"cod\":200}";
//
//        JsonParser parser = new JsonParser();
//        JsonObject jsonObject = parser.parse(response).getAsJsonObject();
//
//        JsonObject jsonWeather = jsonObject.get("weather").getAsJsonArray().get(0).getAsJsonObject();
//
//        Double Temperature = jsonObject.get("main").getAsJsonObject().get("temp").getAsDouble();
//
//        System.out.println("Weather : " +jsonWeather
//                .get("main")
//                .getAsString()
//        );
//        System.out.println("Temperature : " + (int)(Temperature - 273.15) +" C");
//    }

}
