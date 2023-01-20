package org.example;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FlightData {
    private Integer fk_id_flight;
    private LocalDateTime time_stamp;
    private Double co2;
    private Double temperature;
    private String gps;
    private Double humidity;
    private Double air_pressure;

    public FlightData(Integer fk_id_flight, LocalDateTime time_stamp, Double co2, Double temperature, String gps, Double humidity, Double air_pressure) {
        setFk_id_flight(fk_id_flight);
        setTime_stamp(time_stamp);
        setCo2(co2);
        setTemperature(temperature);
        setGps(gps);
        setHumidity(humidity);
        setAir_pressure(air_pressure);
    }

    public void setFk_id_flight(Integer fk_id_flight) {
        if (fk_id_flight == null) {
            throw new IllegalArgumentException("fk_id_flight cannot be null");
        }
        this.fk_id_flight = fk_id_flight;
    }

    public void setTime_stamp(LocalDateTime time_stamp) {
        if (time_stamp == null) {
            throw new IllegalArgumentException("time_stamp cannot be null");
        }
        this.time_stamp = time_stamp;
    }

    public void setCo2(Double co2) {
        this.co2 = co2;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public void setGps(String gps) {
        this.gps = gps;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public void setAir_pressure(Double air_pressure) {
        this.air_pressure = air_pressure;
    }

    public String getFk_id_flight() {
        return fk_id_flight.toString();
    }

    public String getTime_stamp() {

        return time_stamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public String getCo2() {
        return co2.toString();
    }

    public String getTemperature() {
        return temperature.toString();
    }

    public String getGps() {
        return gps;
    }

    public String getHumidity() {
        return humidity.toString();
    }

    public String getAir_pressure() {
        return air_pressure.toString();
    }
}
