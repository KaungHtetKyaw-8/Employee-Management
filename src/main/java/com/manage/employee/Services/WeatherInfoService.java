package com.manage.employee.Services;

import com.google.gson.JsonParser;
import com.manage.employee.Entity.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service @Profile("Weather")
public class WeatherInfoService {
    @Value("${app.open.weather.key}")
    private String API_KEY;

    private final String API_URL1 = "https://api.openweathermap.org/data/2.5/weather?lat=%f&lon=%f&exclude=minutely,hourly,daily,alerts&appid=%s&units=metric";
    private final String API_URL2 = "https://api.openweathermap.org/data/2.5/weather?q=%s&exclude=minutely,hourly,daily,alerts&appid=%s&units=metric";

    @Autowired
    private JsonParser jsonParser;

    public Weather getWeatherInfo(double lat, double lon) {
        return buildWeatherInfo(String.format(API_URL1,lat,lon,API_KEY));
    }

    public Weather getWeatherInfo(String city)  {
        return buildWeatherInfo(String.format(API_URL2,city,API_KEY));
    }

    private Weather buildWeatherInfo(String URL){
        try {
            URL url = new URL(String.format(URL));
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return new Weather()
                    .setWeatherInfo(jsonParser
                            .parse(response.toString())
                            .getAsJsonObject());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
