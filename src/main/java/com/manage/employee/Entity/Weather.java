package com.manage.employee.Entity;

import com.google.gson.JsonObject;
import jakarta.persistence.Entity;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("Weather")
public class Weather {
    private String status;
    private String description;
    private String icon;

    private String temp;
    private String feelsLike;
    private String pressure;
    private String humidity;

    public String getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }

    public String getTemp() {
        return temp;
    }

    public String getFeelsLike() {
        return feelsLike;
    }

    public String getPressure() {
        return pressure;
    }

    public String getHumidity() {
        return humidity;
    }

    public Weather setWeatherInfo(JsonObject info){
        JsonObject weather = info.get("weather").getAsJsonArray().get(0).getAsJsonObject();
        JsonObject temperature = info.get("main").getAsJsonObject();

        this.status = weather.get("main").getAsString();
        this.description = weather.get("description").getAsString();
        this.icon = weather.get("icon").getAsString();

        this.temp = String.format("%.2f%s",temperature.get("temp").getAsDouble(),"°C");
        this.feelsLike = String.format("%.2f%s",temperature.get("feels_like").getAsDouble(),"°C");
        this.humidity = String.format("%.0f%s",temperature.get("humidity").getAsDouble(),"%");
        this.pressure = String.format("%.0f%s",temperature.get("pressure").getAsDouble(),"mb");

        return this;

    }
}
