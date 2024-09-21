package com.example.adapter.controller;

import com.example.adapter.model.MsgA;
import com.example.adapter.model.MsgB;
import com.example.adapter.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@RestController
public class AdapterController {

    @Autowired
    private WeatherService weatherService;

    private final RestTemplate restTemplate = new RestTemplate();

    @PostMapping("/adapter")
    public void handleMessage(@RequestBody MsgA msgA) {
        if (msgA.getMsg() == null || msgA.getMsg().trim().isEmpty()) {
            throw new IllegalArgumentException("Empty message");
        }

        if (!"ru".equals(msgA.getLng())) {
            return;
        }

        Integer temperature = weatherService.getTemperature(msgA.getCoordinates());
        MsgB msgB = new MsgB();
        msgB.setTxt(msgA.getMsg());
        msgB.setCreatedDt(LocalDateTime.now());
        msgB.setCurrentTemp(temperature);

        sendToServiceB(msgB);
    }

    private void sendToServiceB(MsgB msgB) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<MsgB> requestEntity = new HttpEntity<>(msgB, headers);

        try {
            String serviceBUrl = "http://service-b-url/endpoint";
            ResponseEntity<Void> response = restTemplate.postForEntity(serviceBUrl, requestEntity, Void.class);
            if (response.getStatusCode().is2xxSuccessful()) {
            } else {
                System.out.println("Failed to send message to Service B: " + response.getStatusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
