package com.mocmilo.springdemo.jms;

import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

/**
 * Base abstract class for all MDBs
 * - validates incoming JMS message
 * - tries to fetch user form message
 * - triggers message processing (abstract to implement in extending MDB)
 */
@Component
public abstract class BaseMDB implements MessageListener {

    private static final String USER_NAME_PROPERTY = "userName";

    /**
     * process current received message
     * should be extended by current MDB JMS message processing logic
     * @param message
     * @param userName
     */
    public abstract void processMessage(Message message, String userName);

    @Override
    public void onMessage(Message message) {
        ObjectMessage objectMessage = validateJMSMessage(message);
        final String userName = getUserName(objectMessage);
        processMessage(objectMessage, userName);
    }

    private ObjectMessage validateJMSMessage(final Message message) {
        if (null == message) {
            throw new IllegalArgumentException("Received message is null!");
        }
        if (!(message instanceof ObjectMessage)) {
            throw new IllegalArgumentException("Bad type of JMS message (Not ObjectMessage).");
        }
        return (ObjectMessage) message;
    }

    private String getUserName(ObjectMessage objectMessage) {
        return getStringProperty(objectMessage, USER_NAME_PROPERTY);
    }

    private String getStringProperty(final Message message, final String propertyName) {
        try {
            return message.getStringProperty(propertyName);
        } catch (JMSException e) {
            System.out.println("Failed to get user name from message " + e.getMessage());
        }
        throw new IllegalStateException("Internal JMS error");
    }
}
