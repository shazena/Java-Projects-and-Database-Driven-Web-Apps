package com.skshazena.flooringmastery.dao;

import com.skshazena.flooringmastery.dto.Order;
import java.time.LocalDate;
import java.util.List;

/**
 * Flooring Order Dao - The Order Dao is responsible for managing the order
 * files and reading and writing to them.
 *
 * @author Shazena Khan
 *
 * Date Created: Jun 28, 2020
 */
public interface FlooringOrderDao {

    Order addOrder(Order newOrder) throws FlooringPersistenceException;

    Order getOneOrder(LocalDate date, int orderNumber) throws FlooringPersistenceException;

    List<Order> getAllOrders() throws FlooringPersistenceException;

    List<Order> getAllOrdersForSpecifiedDate(LocalDate date) throws FlooringPersistenceException;

    Order editOrder(Order editedOrder) throws FlooringPersistenceException;

    Order removeOrder(LocalDate date, int orderNumber) throws FlooringPersistenceException;

}
