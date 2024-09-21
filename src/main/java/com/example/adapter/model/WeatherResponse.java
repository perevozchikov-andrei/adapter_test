package com.example.adapter.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WeatherResponse {

    @JsonProperty("main")
    private Main main;

    public Main getMain() {
        return main;
    }

    public static class Main {
        @JsonProperty("temp")
        private Double temp;

        public Double getTemp() {
            return temp;
        }
    }
}
