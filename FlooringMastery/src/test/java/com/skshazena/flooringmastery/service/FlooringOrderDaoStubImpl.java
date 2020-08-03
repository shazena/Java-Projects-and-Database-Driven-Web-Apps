package com.skshazena.flooringmastery.service;

import com.skshazena.flooringmastery.dao.FlooringOrderDao;
import com.skshazena.flooringmastery.dao.FlooringPersistenceException;
import com.skshazena.flooringmastery.dto.Order;
import com.skshazena.flooringmastery.dto.ProductType;
import com.skshazena.flooringmastery.dto.StateTaxInfo;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Shazena Khan
 *
 * Date Created: Jul 5, 2020
 */
public class FlooringOrderDaoStubImpl implements FlooringOrderDao {

    public Order firstOrder;

    public FlooringOrderDaoStubImpl() {
        firstOrder = new Order(LocalDate.now().plusDays(3),
                "TheFirstCustomer",
                new StateTaxInfo("TX", new BigDecimal("4.45")),
                new ProductType("Laminate", new BigDecimal("1.75"), new BigDecimal("2.10")),
                new BigDecimal("123"),
                3,
                new BigDecimal("215.25"),
                new BigDecimal("258.30"),
                new BigDecimal("18.94"),
                new BigDecimal("492.49"));
    }

    @Override
    public Order addOrder(Order newOrder) throws FlooringPersistenceException {
        if (newOrder.getDateOfOrder().compareTo(firstOrder.getDateOfOrder()) == 0
                && newOrder.getOrderNumber() == firstOrder.getOrderNumber()) {
            return firstOrder;
        //if you tried to add a new order that had the same keys as the order 
        //that is already in here, it will just return the order that is in here
        } else {
            return null;
        //this indicates a successful add
        }
    }

    @Override
    public Order getOneOrder(LocalDate date, int orderNumber) throws FlooringPersistenceException {
        if (date.compareTo(firstOrder.getDateOfOrder()) == 0
                && orderNumber == firstOrder.getOrderNumber()) {
            return firstOrder;
        //if you enter the correct order keys, you will get the order
        } else {
            return null;
        //if there is no order matching those keys, return null
        }
    }

    @Override
    public List<Order> getAllOrders() throws FlooringPersistenceException {
        ArrayList<Order> listOfOrders = new ArrayList<>();
        listOfOrders.add(firstOrder);
        return listOfOrders;
    }

    @Override
    public List<Order> getAllOrdersForSpecifiedDate(LocalDate date) throws FlooringPersistenceException {
        if (date.compareTo(firstOrder.getDateOfOrder()) == 0) {
            ArrayList<Order> listOfOrders = new ArrayList<>();
            listOfOrders.add(firstOrder);
            return listOfOrders;
        } else {
            return null;
        }
    }

    @Override
    public Order editOrder(Order editedOrder) throws FlooringPersistenceException {
        //returns previous mapping if there was one. 
        if (editedOrder.getDateOfOrder().compareTo(firstOrder.getDateOfOrder()) == 0
                && editedOrder.getOrderNumber() == firstOrder.getOrderNumber()) {

            return firstOrder;
        } else {
            return null;
        }

    }

    @Override
    public Order removeOrder(LocalDate date, int orderNumber) throws FlooringPersistenceException {
        if (date.compareTo(firstOrder.getDateOfOrder()) == 0
                && orderNumber == firstOrder.getOrderNumber()) {
            return firstOrder;
        } else {
            return null;
        }
    }

}
