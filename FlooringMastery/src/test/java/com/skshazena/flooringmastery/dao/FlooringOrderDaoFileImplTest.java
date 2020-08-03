/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skshazena.flooringmastery.dao;

import com.skshazena.flooringmastery.dto.Order;
import com.skshazena.flooringmastery.dto.ProductType;
import com.skshazena.flooringmastery.dto.StateTaxInfo;
import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Shazena
 */
public class FlooringOrderDaoFileImplTest {

    FlooringOrderDao testDao;
    public static Order order1;
    public static Order order2;
    public static Order order3;
    public static Order order4;

    public FlooringOrderDaoFileImplTest() {
    }

    @BeforeAll
    public static void setUpClass() throws Exception {

        //Order 1 - distinct order
        LocalDate dateOfOrder = LocalDate.now().plusDays(3);
        String customerName = "Scott Lang";
        String stateAbbreviation = "NY";
        BigDecimal stateTaxRate = new BigDecimal("4.50");
        StateTaxInfo stateTaxInfoObject = new StateTaxInfo(stateAbbreviation, stateTaxRate);
        String productTypeName = "Carpet";
        BigDecimal costPerSquareFoot = new BigDecimal("2.45");
        BigDecimal laborCostPerSquareFoot = new BigDecimal("3.98");
        ProductType productTypeObject = new ProductType(productTypeName, costPerSquareFoot, laborCostPerSquareFoot);
        BigDecimal area = new BigDecimal("129");
        BigDecimal materialCostForOrder = new BigDecimal("316.05");
        BigDecimal laborCostForOrder = new BigDecimal("513.42");
        BigDecimal taxForOrder = new BigDecimal("37.33");
        BigDecimal totalForOrder = new BigDecimal("866.80");
        int orderNumber = 1;

        order1 = new Order(dateOfOrder, customerName, stateTaxInfoObject, productTypeObject, area, orderNumber, materialCostForOrder, laborCostForOrder, taxForOrder, totalForOrder);

        //Order 2 - another distinct Order
        LocalDate dateOfOrder2 = LocalDate.now().plusDays(5);
        String customerName2 = "Avengers, Inc.";
        String stateAbbreviation2 = "CA";
        BigDecimal stateTaxRate2 = new BigDecimal("8.75");
        StateTaxInfo stateTaxInfoObject2 = new StateTaxInfo(stateAbbreviation2, stateTaxRate2);
        String productTypeName2 = "Tile";
        BigDecimal costPerSquareFoot2 = new BigDecimal("3.98");
        BigDecimal laborCostPerSquareFoot2 = new BigDecimal("2.56");
        ProductType productTypeObject2 = new ProductType(productTypeName2, costPerSquareFoot2, laborCostPerSquareFoot2);
        BigDecimal area2 = new BigDecimal("500");
        BigDecimal materialCostForOrder2 = new BigDecimal("1990.00");
        BigDecimal laborCostForOrder2 = new BigDecimal("1280.00");
        BigDecimal taxForOrder2 = new BigDecimal("286.13");
        BigDecimal totalForOrder2 = new BigDecimal("3556.13");
        int orderNumber2 = 2;

        order2 = new Order(dateOfOrder2, customerName2, stateTaxInfoObject2, productTypeObject2, area2, orderNumber2, materialCostForOrder2, laborCostForOrder2, taxForOrder2, totalForOrder2);

        //Order 3 - same date as Order 2, everything else distinct
        LocalDate dateOfOrder3 = LocalDate.now().plusDays(5);
        String customerName3 = "Legends Of Tomorrow";
        String stateAbbreviation3 = "TX";
        BigDecimal stateTaxRate3 = new BigDecimal("4.45");
        StateTaxInfo stateTaxInfoObject3 = new StateTaxInfo(stateAbbreviation3, stateTaxRate3);
        String productTypeName3 = "Wood";
        BigDecimal costPerSquareFoot3 = new BigDecimal("5.15");
        BigDecimal laborCostPerSquareFoot3 = new BigDecimal("4.75");
        ProductType productTypeObject3 = new ProductType(productTypeName3, costPerSquareFoot3, laborCostPerSquareFoot3);
        BigDecimal area3 = new BigDecimal("300");
        BigDecimal materialCostForOrder3 = new BigDecimal("1545.00");
        BigDecimal laborCostForOrder3 = new BigDecimal("1425.00");
        BigDecimal taxForOrder3 = new BigDecimal("132.17");
        BigDecimal totalForOrder3 = new BigDecimal("3012.17");
        int orderNumber3 = 3;

        order3 = new Order(dateOfOrder3, customerName3, stateTaxInfoObject3, productTypeObject3, area3, orderNumber3, materialCostForOrder3, laborCostForOrder3, taxForOrder3, totalForOrder3);

        //Order 4 - same date and order number as order 1 (is the edited version of order 1)
        String customerName4 = "Avengers, Inc.";
        String stateAbbreviation4 = "CA";
        BigDecimal stateTaxRate4 = new BigDecimal("8.75");
        StateTaxInfo stateTaxInfoObject4 = new StateTaxInfo(stateAbbreviation4, stateTaxRate4);
        String productTypeName4 = "Tile";
        BigDecimal costPerSquareFoot4 = new BigDecimal("3.98");
        BigDecimal laborCostPerSquareFoot4 = new BigDecimal("2.56");
        ProductType productTypeObject4 = new ProductType(productTypeName4, costPerSquareFoot4, laborCostPerSquareFoot4);
        BigDecimal area4 = new BigDecimal("500");
        BigDecimal materialCostForOrder4 = new BigDecimal("1990.00");
        BigDecimal laborCostForOrder4 = new BigDecimal("1280.00");
        BigDecimal taxForOrder4 = new BigDecimal("286.13");
        BigDecimal totalForOrder4 = new BigDecimal("3556.13");

        order4 = new Order(dateOfOrder, customerName4, stateTaxInfoObject4, productTypeObject4, area4, orderNumber, materialCostForOrder4, laborCostForOrder4, taxForOrder4, totalForOrder4);

    }

    @BeforeEach
    public void setUp() {

        File folder = new File(".\\Test\\Orders");
        if (!folder.exists()) {
            folder.mkdirs();
        }
        
        File[] files = folder.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                file.delete();
            }
        }

        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        testDao = ctx.getBean("flooringOrderDao", FlooringOrderDao.class);
    }

    @Test
    public void testAddOrder() throws Exception {

        Order addedOrder = testDao.addOrder(order1);
        Order retrievedOrder = testDao.getOneOrder(order1.getDateOfOrder(), order1.getOrderNumber());

        assertEquals(order1, retrievedOrder, "Added order should match retrieved order");
        assertNull(addedOrder, "Successfully added order should be null");
        
    }

    @Test
    public void testAddGet2GetAllOrders() throws Exception {

        Order addedOrder1 = testDao.addOrder(order1);
        Order addedOrder2 = testDao.addOrder(order2);

        Order retrievedOrder1 = testDao.getOneOrder(order1.getDateOfOrder(), order1.getOrderNumber());
        Order retrievedOrder2 = testDao.getOneOrder(order2.getDateOfOrder(), order2.getOrderNumber());

        assertEquals(order1, retrievedOrder1, "Retrieved Order 1 should match order 1");
        assertEquals(order2, retrievedOrder2, "Retrieved Order 2 should match order 2");

        assertNull(addedOrder1, "Added order1 should be null");
        assertNull(addedOrder2, "Added order2 should be null");

        List<Order> listOfAllOrders = testDao.getAllOrders();
        assertNotNull(listOfAllOrders, "List of orders should not be null");
        assertEquals(listOfAllOrders.size(), 2, "List should have two items");

    }

    @Test
    public void testAddGetAllOrdersForSpecifiedDate() throws Exception {

        Order addedOrder1 = testDao.addOrder(order1);
        assertNull(addedOrder1, "Added order should be null");

        Order addedOrder2 = testDao.addOrder(order2);
        assertNull(addedOrder2, "Added order should be null");

        Order addedOrder3 = testDao.addOrder(order3);
        assertNull(addedOrder3, "Added order should be null");

        Order retrievedOrder1 = testDao.getOneOrder(order1.getDateOfOrder(), order1.getOrderNumber());
        assertEquals(order1, retrievedOrder1, "Order 1 should be the same as retrieved order 1");
        Order retrievedOrder2 = testDao.getOneOrder(order2.getDateOfOrder(), order2.getOrderNumber());
        assertEquals(order2, retrievedOrder2, "Order 2 should be the same as retrieved order 2");
        Order retrievedOrder3 = testDao.getOneOrder(order3.getDateOfOrder(), order3.getOrderNumber());
        assertEquals(order3, retrievedOrder3, "Order 3 should be the same as retrieved order 3");

        List<Order> allOrdersForDate = testDao.getAllOrdersForSpecifiedDate(order1.getDateOfOrder());
        assertEquals(allOrdersForDate.size(), 1, "List should contain one item");
        assertTrue(allOrdersForDate.contains(order1), "List should contain order 1");

        List<Order> allOrdersForDate2 = testDao.getAllOrdersForSpecifiedDate(order2.getDateOfOrder());
        assertEquals(allOrdersForDate2.size(), 2, "List should have two orders");
        assertTrue(allOrdersForDate2.contains(order2), "List should have order 2");
        assertTrue(allOrdersForDate2.contains(order3), "List should have order 3");

        List<Order> allOrdersForDate3 = testDao.getAllOrdersForSpecifiedDate(order3.getDateOfOrder());
        assertEquals(allOrdersForDate3.size(), 2, "List should have two orders");
        assertTrue(allOrdersForDate3.contains(order2), "List should have order 2");
        assertTrue(allOrdersForDate3.contains(order3), "List should have order 3");
    }

    @Test
    public void testEditOrder() throws Exception {

        Order addedOrder1 = testDao.addOrder(order1);
        assertNull(addedOrder1, "Added order should be null");
        Order retrievedOrder1 = testDao.getOneOrder(order1.getDateOfOrder(), order1.getOrderNumber());
        assertEquals(order1, retrievedOrder1, "Retrieved order 1 should match order 1");

        Order editedOrder = testDao.editOrder(order4);
        assertNotNull(editedOrder, "Edited Order should not be null");
        assertEquals(editedOrder, order1, "Result of editing order should be original order that was there");

        Order retrievedOrder2 = testDao.getOneOrder(order1.getDateOfOrder(), order1.getOrderNumber());
        assertEquals(order4, retrievedOrder2, "Retrieved order 2 should match order 4");

    }

    @Test
    public void testRemoveOrders() throws Exception {

        Order addedOrder1 = testDao.addOrder(order1);
        assertNull(addedOrder1, "Added order should be null");
        Order addedOrder2 = testDao.addOrder(order2);
        assertNull(addedOrder2, "Added order should be null");

        Order retrievedOrder1 = testDao.getOneOrder(order1.getDateOfOrder(), order1.getOrderNumber());
        Order retrievedOrder2 = testDao.getOneOrder(order2.getDateOfOrder(), order2.getOrderNumber());

        assertEquals(retrievedOrder1, order1, "Retrieved Order should be the same as order 1");
        assertEquals(retrievedOrder2, order2, "Retrieved Order should be the same as order 1");

        List<Order> listOfAllOrders = testDao.getAllOrders();
        assertEquals(listOfAllOrders.size(), 2, "List of all orders should contain two orders");
        assertTrue(listOfAllOrders.contains(order1), "List should contain order 1");
        assertTrue(listOfAllOrders.contains(order2), "List should contain order 2");

        Order removedOrder = testDao.removeOrder(order1.getDateOfOrder(), order1.getOrderNumber());

        assertEquals(removedOrder, order1, "Removed Order should be order 1");

        listOfAllOrders = testDao.getAllOrders();
        assertEquals(listOfAllOrders.size(), 1, "List should only contain one order");
        assertFalse(listOfAllOrders.contains(order1), "List should not contain order 1");
        assertTrue(listOfAllOrders.contains(order2), "List should contain order 2");

        Order removedOrder2 = testDao.removeOrder(order2.getDateOfOrder(), order2.getOrderNumber());
        assertEquals(removedOrder2, order2, "Removed Order should be order 2");

        listOfAllOrders = testDao.getAllOrders();
        assertEquals(listOfAllOrders.size(), 0, "List should have zero items");
        assertTrue(listOfAllOrders.isEmpty(), "List should be empty");
        assertFalse(listOfAllOrders.contains(order1), "List should not contain order 1");
        assertFalse(listOfAllOrders.contains(order2), "List should not contain order 2");

    }

}
