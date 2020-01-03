package com.mocmilo.springdemo.crud.demo.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
public class RentalAgreement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rentalAgreementId;
    private LocalDateTime validFrom;
    private String notes;

    @ManyToOne
    private RentalCar rentalCar;

    @ManyToOne
    private RentalClient rentalClient;


    public Long getRentalAgreementId() {
        return rentalAgreementId;
    }

    public void setRentalAgreementId(Long rentalAgreementId) {
        this.rentalAgreementId = rentalAgreementId;
    }

    public LocalDateTime getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDateTime validFrom) {
        this.validFrom = validFrom;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public RentalCar getRentalCar() {
        return rentalCar;
    }

    public void setRentalCar(RentalCar rentalCar) {
        this.rentalCar = rentalCar;
    }

    public RentalClient getRentalClient() {
        return rentalClient;
    }

    public void setRentalClient(RentalClient rentalClient) {
        this.rentalClient = rentalClient;
    }
}
