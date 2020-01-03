package com.mocmilo.springdemo.crud.services;

import com.mocmilo.springdemo.crud.conf.AppConfig;
import com.mocmilo.springdemo.crud.demo.model.RentalAgreement;
import com.mocmilo.springdemo.crud.demo.model.RentalCar;
import com.mocmilo.springdemo.crud.demo.model.RentalClient;
import com.mocmilo.springdemo.crud.demo.repository.RentalCarCRUDService;
import com.mocmilo.springdemo.crud.demo.repository.RentalClientCRUDService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
@EnableTransactionManagement
@Rollback
@SpringBootTest
public class CrudServiceTest {

    @Autowired
    private RentalCarCRUDService rentalCarCRUDService;

    @Autowired
    private RentalClientCRUDService rentalClientCRUDService;

    @Test
    public void shouldCreateRentalCarAndRentalClientAgreementRecordsInDatabase() {

        // sequence: create car, create client, create mapping record

        // 1) create car
        RentalCar rentalCar = new RentalCar();
        rentalCar.setBrand("Tesla");
        rentalCar.setPayloadKG(350);
        rentalCar.setPricePerHour(new BigDecimal("37.0"));

        // 2) create client
        RentalClient rentalClient = new RentalClient();
        rentalClient.setClientBirthDate(LocalDateTime.now().minusYears(25));
        rentalClient.setName("John");
        rentalClient.setSurname("Doe");

        // 3) persist create car and client
        rentalCarCRUDService.save(rentalCar);
        rentalClientCRUDService.save(rentalClient);

        // exception impact on persistence (experiments)
/*        if (rentalCar != null) {
            throw new RuntimeException("transaction check");
        }*/

        // 4) create rental agreement
        RentalAgreement rentalAgreement = new RentalAgreement();
        rentalAgreement.setRentalCar(rentalCar);
        rentalAgreement.setRentalClient(rentalClient);
        rentalAgreement.setValidFrom(LocalDateTime.now());
        rentalAgreement.setNotes("some notes");

        List<RentalAgreement> rentalAgreements = new ArrayList<>();
        rentalAgreements.add(rentalAgreement);

        // 5) add rental agreements by update of Car (new Hibernate session)
        Long id = rentalCar.getCarId();
        RentalCar carFromDb = rentalCarCRUDService.findById(id).get();
        carFromDb.setRentalAgreements(rentalAgreements);
        rentalCarCRUDService.update(carFromDb);

        // get persisted rental Car
        System.out.println("Check persited car, client, agreement (mapping).");
        System.out.println("rental car from db id" + carFromDb.getCarId());
        System.out.println("rental car agreements from db size:" + carFromDb.getRentalAgreements().size());
        System.out.println("TEST END");
    }

    @Test
    public void shouldDeleteRentalCarAndRentalClientAgreementRecordsInDatabase() {

        // if mapping (agreement exists) should throw exception

        Optional<RentalCar> carFromDb = rentalCarCRUDService.findById(new Long("1"));

        carFromDb.ifPresent(rentalCar -> rentalCarCRUDService.delete(rentalCar));

        Optional<RentalCar> carFromDb2 = rentalCarCRUDService.findById(new Long("1"));

        System.out.println("After delete, object is present in DB: " + carFromDb2.isPresent());

    }
}






