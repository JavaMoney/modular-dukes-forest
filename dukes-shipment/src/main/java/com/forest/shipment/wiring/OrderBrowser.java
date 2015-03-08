/**
 * Copyright (c) 2014 Oracle and/or its affiliates. All rights reserved.
 *
 * You may not modify, use, reproduce, or distribute this software except in
 * compliance with  the terms of the License at:
 * http://java.net/projects/javaeetutorial/pages/BerkeleyLicense
 */
package com.forest.shipment.wiring;

import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueBrowser;

import com.forest.model.CustomerOrder;

@Stateless
public class OrderBrowser {

    private static final Logger logger = Logger.getLogger(OrderBrowser.class.getCanonicalName());
    @Inject
    private JMSContext context;
    @Resource(mappedName = "java:global/jms/OrderQueue")
    private Queue queue;
    private QueueBrowser browser;

    public Map<String, CustomerOrder> getOrders() {
        browser = context.createBrowser(queue);
        Enumeration msgs;
        try {
            msgs = browser.getEnumeration();

            if (!msgs.hasMoreElements()) {
                logger.log(Level.INFO, "No messages on the queue!");
            } else {

                Map<String, CustomerOrder> result = new LinkedHashMap<>();
                while (msgs.hasMoreElements()) {
                    Message msg = (Message) msgs.nextElement();

                    logger.log(Level.INFO, "Message ID: {0}", msg.getJMSMessageID());
                    CustomerOrder order = msg.getBody(CustomerOrder.class);
                    result.put(msg.getJMSMessageID(), order);
                }
                return result;
            }
        } catch (JMSException ex) {
            Logger.getLogger(OrderBrowser.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public CustomerOrder processOrder(String OrderMessageID) {

        logger.log(Level.INFO, "Processing Order {0}", OrderMessageID);
        JMSConsumer consumer = context.createConsumer(queue, "JMSMessageID='" + OrderMessageID + "'");

        CustomerOrder order = consumer.receiveBody(CustomerOrder.class, 1);
        return order;
    }
}
