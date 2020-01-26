package com.mocmilo.springdemo.crud.utils;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DBQueryMapUtilTest {

    void init(){}

    @Test
    void getSqlQueryVo() throws SQLException {

        DBQueryMapUtil.readQueries();
        String query = DBQueryMapUtil.getSqlQueryVo("select.rentalCars");
        System.out.println(query);
    }
}