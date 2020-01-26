package com.mocmilo.springdemo.crud.demo.jdbc;

import com.mocmilo.springdemo.crud.conf.AppConfig;
import com.mocmilo.springdemo.crud.demo.jdbc.datasource.DataSourceBuilder;
import com.mocmilo.springdemo.crud.demo.jdbc.model.RentalCarVO;
import com.mocmilo.springdemo.crud.utils.DBQueryMapUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
@EnableTransactionManagement
@Rollback
@SpringBootTest
public class RentalCarDAOTest {

    @Autowired
    private RentalCarDAO rentalCarDAO;

    @Before
    public void init() throws SQLException{
        DBQueryMapUtil.readQueries();
    }


    @Test
    public void shouldReturnResult() throws SQLException {

        DBQueryMapUtil.readQueries();
        DataSource dataSource = DataSourceBuilder.buildDataSource();
        Connection connection = dataSource.getConnection();

        List<String> carIds = Arrays.asList("Ford", "Tesla");
        List<RentalCarVO> cars = rentalCarDAO.getRentalCarList(connection, carIds);

        System.out.println("cars size:" + cars.size());

    }


}