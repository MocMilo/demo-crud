package com.mocmilo.springdemo.jms;

import com.mocmilo.springdemo.conf.AppConfig;
import com.mocmilo.springdemo.crud.jdbc.RentalCarDAO;
import com.mocmilo.springdemo.crud.jdbc.datasource.DataSourceBuilder;
import com.mocmilo.springdemo.crud.jdbc.model.RentalCarVO;
import com.mocmilo.springdemo.utils.DBQueryMapUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.support.converter.MessagingMessageConverter;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
@EnableTransactionManagement
@Rollback
@SpringBootTest
public class CarNotificationMDBTest {


    @Autowired
    JmsTemplate jmsTemplate;

    //@Autowired


    @Autowired
    private RentalCarDAO rentalCarDAO;


    @Test
    public void shouldReceiveMessage() throws SQLException {

        DBQueryMapUtil.readQueries();
        DataSource dataSource = DataSourceBuilder.buildDataSource();
        Connection connection = dataSource.getConnection();

        List<String> carIds = Arrays.asList("Ford", "Tesla");
        List<RentalCarVO> cars = rentalCarDAO.getRentalCarList(connection, carIds);

        RentalCarVO rentalCarVO = cars.get(0);

        // Send a message with a POJO - the template reuse the message converter
        System.out.println("Sending an RentalCarVO to CarNotificationMDB  message.");


        jmsTemplate.send("carNotificationProcessorQueue", session -> {
            ObjectMessage objectMessage = session.createObjectMessage();
            objectMessage.setObject(rentalCarVO);
            objectMessage.setStringProperty("userName", "TestUser1");
            return objectMessage;
        });

        // Dedicated Receiver will print message with content
    }

}