package com.mocmilo.springdemo.crud.demo.jdbc.model;

public class RentalCarVO extends BaseVO {

    private String brand;
    private String color;
    private String payloadKg;
    private String pricePerHour;

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPayloadKg() {
        return payloadKg;
    }

    public void setPayloadKg(String payloadKg) {
        this.payloadKg = payloadKg;
    }

    public String getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(String pricePerHour) {
        this.pricePerHour = pricePerHour;
    }
}
