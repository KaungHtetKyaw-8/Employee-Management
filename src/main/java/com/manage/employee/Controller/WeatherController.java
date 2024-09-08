package com.manage.employee.Controller;

import com.manage.employee.Entity.Weather;
import com.manage.employee.Services.WeatherInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/weather")
@Profile("Weather")
public class WeatherController {
    private WeatherInfoService weatherInfoService;

    @Autowired
    public WeatherController(WeatherInfoService weatherInfoService) {
        this.weatherInfoService = weatherInfoService;
    }

    @GetMapping
    public ResponseEntity getWeather(
            @RequestParam(required = false) Double lat,
            @RequestParam(required = false) Double lon,
            @RequestParam(required = false) String city){

        System.out.println("Weather API called");
        if (!(city==null || city.isEmpty())){
            System.out.println("City get : " + city);
            return ResponseEntity.ok(weatherInfoService.getWeatherInfo(city));
        } else if (!(lat==null || lon==null)){
            System.out.println("lat lon get : " + lat + "," + lon);
            return ResponseEntity.ok(weatherInfoService.getWeatherInfo(lat, lon));
        } else {
            return ResponseEntity.badRequest().header("Request Parameter Error.","lat,lon,city").eTag("Value Not Found").build();
        }
    }

}
