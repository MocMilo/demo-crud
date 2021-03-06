package com.mocmilo.springdemo.crud.demo;

import javax.persistence.*;

@Entity
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long countryId;

    private String countryName;

    @Enumerated(EnumType.STRING)
    @Column(length = 15)
    private MarketType marketType;




}
