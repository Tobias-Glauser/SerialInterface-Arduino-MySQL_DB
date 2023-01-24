package org.example;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class FlightData {
    private final Integer flightId;
    private final LocalDateTime timestamp;
    private final Double altitude;
    private Double co2;
    private Double temperature;
    private String gps;
    private Double humidity;
    private Double airPressure;

    public FlightData(String data) {
        String[] splitData = data.split("\\|");
        this.flightId = splitData[splitData.length - 7].equals("null") ? null : Integer.parseInt(splitData[splitData.length - 7]);
        this.altitude = splitData[splitData.length - 6].equals("null") ? null : Double.parseDouble(splitData[splitData.length - 6]);
        this.co2 = splitData[splitData.length - 5].equals("null") ? null : Double.parseDouble(splitData[splitData.length - 5]);
        this.temperature = splitData[splitData.length - 4].equals("null") ? null : Double.parseDouble(splitData[splitData.length - 4]);
        this.gps = splitData[splitData.length - 3].equals("null") ? null : splitData[splitData.length - 3];
        this.humidity = splitData[splitData.length - 2].equals("null") ? null : Double.parseDouble(splitData[splitData.length - 2]);
        this.airPressure = splitData[splitData.length - 1].equals("null") ? null : Double.parseDouble(splitData[splitData.length - 1]);
        this.timestamp = LocalDateTime.now();
    }
}
