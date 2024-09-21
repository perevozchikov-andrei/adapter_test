package com.example.adapter.model;

import lombok.Data;

@Data
public class MsgA {
    private String msg;
    private String lng;
    private Coordinates coordinates;

    @Data
    public static class Coordinates {
        private String latitude;
        private String longitude;
    }
}
