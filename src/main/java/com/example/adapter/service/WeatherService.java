package com.example.adapter.service;

import com.example.adapter.model.MsgA;
import com.example.adapter.model.WeatherResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    private final RestTemplate restTemplate = new RestTemplate();

    // метод довольно сильно упрощен, так как это тестовое
    public Integer getTemperature(MsgA.Coordinates coordinates) {
        String url = String.format("https://api.gismeteo.ru/v1/weather?lat=%s&lon=%s",
                coordinates.getLatitude(), coordinates.getLongitude());

        try {
            WeatherResponse response = restTemplate.getForObject(url, WeatherResponse.class);
            if (response != null && response.getMain() != null) {
                return (int) Math.round(response.getMain().getTemp());
            }
        } catch (RestClientException e) {
            e.printStackTrace();
        }
        return null;
    }
}
