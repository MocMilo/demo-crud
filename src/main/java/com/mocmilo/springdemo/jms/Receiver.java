package com.mocmilo.springdemo.jms;


import com.mocmilo.springdemo.crud.jdbc.model.RentalCarVO;
import com.mocmilo.springdemo.crud.model.RentalCar;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Receiver {



    @JmsListener(destination = "carProcessor", containerFactory = "customFactory")
    public void receiveMessage(RentalCarVO rentalCarVO) {
        System.out.println("Received jms message. RentalCarVO: brand: " + rentalCarVO.getBrand());
    }



}
