package com.skshazena.flooringmastery.service;

import com.skshazena.flooringmastery.dao.FlooringPersistenceException;
import com.skshazena.flooringmastery.dto.Order;
import com.skshazena.flooringmastery.dto.ProductType;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Shazena Khan
 *
 * Date Created: Jun 28, 2020
 */
public interface FlooringService {

    List<ProductType> getAllProducts() throws FlooringPersistenceException;

    List<Order> getAllOrdersForSpecifiedDate(LocalDate date) throws FlooringPersistenceException, FlooringNoMatchingOrdersException;

    Order getAnOrder(LocalDate date, int orderNumber) throws FlooringPersistenceException, FlooringNoSuchOrderException;

    Order editValidateAndCalculateOrder(Order originalOrder, Order editedOrder) throws FlooringPersistenceException, FlooringOrderValidationException;

    Order validateAndCalculateOrder(Order theOrderSoFar, int orderNumber) throws FlooringPersistenceException, FlooringOrderValidationException;

    boolean addAnOrder(Order theOrder, String userApprovalOfOrder) throws FlooringPersistenceException;

    boolean editAnOrder(Order theOrder, String userApprovalOfOrder) throws FlooringPersistenceException;

    boolean removeAnOrder(LocalDate date, int orderNumber, String userApprovalOfOrder) throws FlooringPersistenceException;

    void exportAllData() throws FlooringPersistenceException;

}
