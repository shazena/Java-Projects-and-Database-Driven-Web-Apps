package com.skshazena.flooringmastery.service;

import com.skshazena.flooringmastery.dao.FlooringPersistenceException;
import com.skshazena.flooringmastery.dto.Order;
import com.skshazena.flooringmastery.dto.ProductType;
import com.skshazena.flooringmastery.dto.StateTaxInfo;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
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
public class FlooringServiceImplTest {

    private FlooringService service;
    public static ProductType firstProduct;
    public static StateTaxInfo firstState;
    public static Order order0ValidDate0;
    public static Order order1PreValidation;
    public static Order order1PostValidation;
    public static Order order1InvalidDate;
    public static Order order1InvalidCustomerName;
    public static Order order1InvalidStateName;
    public static Order order1InvalidProductName;
    public static Order order1InvalidArea;
    public static Order order1InvalidDateArea;
    public static Order order1InvalidDateStateArea;
    public static Order order1InvalidAll;
    public static Order order1PreEdit;
    public static Order order1PreEditNoNewInfo;

    public FlooringServiceImplTest() {
        ApplicationContext ctx
                = new ClassPathXmlApplicationContext("applicationContext.xml");
        service
                = ctx.getBean("service", FlooringService.class);
    }

    @BeforeAll
    public static void setUpClass() throws Exception {
        firstProduct = new ProductType("Laminate", new BigDecimal("1.75"), new BigDecimal("2.10"));
        firstState = new StateTaxInfo("TX", "Texas", new BigDecimal("4.45"));
        order0ValidDate0 = new Order(LocalDate.now().plusDays(3),
                "TheFirstCustomer",
                new StateTaxInfo("TX", new BigDecimal("4.45")),
                new ProductType("Laminate", new BigDecimal("1.75"), new BigDecimal("2.10")),
                new BigDecimal("123"),
                3,
                new BigDecimal("215.25"),
                new BigDecimal("258.30"),
                new BigDecimal("18.94"),
                new BigDecimal("492.49"));

        LocalDate validDate = LocalDate.now().plusDays(4);
        String validCustomerName = "TheSecondCustomer";
        String validStateName = "Texas";
        String validProductName = "Laminate";
        BigDecimal validArea = new BigDecimal("123");

        LocalDate invalidDate = LocalDate.now().plusDays(-3);
        String invalidCustomerName = "The Big $ick";
        String invalidStateName = "Oklahoma";
        String invalidProductName = "Drywall";
        BigDecimal invalidArea = new BigDecimal("12");

        order1PreValidation = new Order(validDate, validCustomerName,
                validStateName,
                validProductName,
                validArea);
        order1PostValidation = new Order(validDate, validCustomerName,
                new StateTaxInfo("TX", validStateName, new BigDecimal("4.45")),
                new ProductType(validProductName, new BigDecimal("1.75"), new BigDecimal("2.10")),
                validArea,
                4,
                new BigDecimal("215.25"),
                new BigDecimal("258.30"),
                new BigDecimal("18.94"),
                new BigDecimal("492.49"));
        order1InvalidDate = new Order(invalidDate, validCustomerName, validStateName, validProductName, validArea);
        order1InvalidCustomerName = new Order(validDate, invalidCustomerName, validStateName, validProductName, validArea);
        order1InvalidStateName = new Order(validDate, validCustomerName, invalidStateName, validProductName, validArea);
        order1InvalidProductName = new Order(validDate, validCustomerName, validStateName, invalidProductName, validArea);
        order1InvalidArea = new Order(validDate, validCustomerName, validStateName, validProductName, invalidArea);
        order1InvalidDateArea = new Order(invalidDate, validCustomerName, validStateName, validProductName, invalidArea);
        order1InvalidDateStateArea = new Order(invalidDate, validCustomerName, invalidStateName, validProductName, invalidArea);
        order1InvalidAll = new Order(invalidDate, invalidCustomerName, invalidStateName, invalidProductName, invalidArea);
        order1PreEdit = new Order(validDate, "TheNameToBeEdited", "New York", "Tile", new BigDecimal("567"));
        order1PreEditNoNewInfo = new Order(validDate, "", "", "", null);
    }

    @Test
    public void testGetAllProductTypeNames() throws Exception {
        List<ProductType> listOfAllProducts = service.getAllProducts();
        assertNotNull(listOfAllProducts, "List of products should not be null");
        assertEquals(listOfAllProducts.size(), 1, "List should contain one product");
        assertTrue(listOfAllProducts.contains(firstProduct), "List should contain first product");
    }

    @Test
    public void testGetAllOrdersForSpecifiedDateValid() throws Exception {

        try {
            List<Order> allOrdersForSpecifiedDate = service.getAllOrdersForSpecifiedDate(LocalDate.now().plusDays(3));
            assertEquals(allOrdersForSpecifiedDate.size(), 1, "List should have one order");
            assertTrue(allOrdersForSpecifiedDate.contains(order0ValidDate0), "List should contain order 0");
        } catch (FlooringNoMatchingOrdersException | FlooringPersistenceException e) {
            fail("No Exceptions should be caught");
        }
    }

    @Test
    public void testGetAllOrdersForSpecifiedDateInvalid() throws Exception {
        try {
            service.getAllOrdersForSpecifiedDate(LocalDate.now().plusDays(5));
        } catch (FlooringNoMatchingOrdersException e) {
            System.out.println("Right Exception caught!");
        } catch (FlooringPersistenceException e) {
            fail("Incorrect Exception caught");
        }
    }

    @Test
    public void testGetAnOrderValidDateAndValidOrderNumber() throws Exception {
        try {
            service.getAnOrder(LocalDate.now().plusDays(3), 3);
        } catch (FlooringPersistenceException | FlooringNoSuchOrderException e) {
            fail("No Exceptions should be caught");
        }
    }

    @Test
    public void testGetAnOrderValidDateAndInvalidOrderNumber() throws Exception {
        try {
            service.getAnOrder(LocalDate.now().plusDays(3), 4);
        } catch (FlooringNoSuchOrderException e) {
            System.out.println("Right Exception caught!");
        } catch (FlooringPersistenceException e) {
            fail("Incorrect Exception caught");
        }
    }

    @Test
    public void testGetAnOrderInvalidDateAndValidOrderNumber() throws Exception {
        try {
            service.getAnOrder(LocalDate.now().plusDays(5), 3);
        } catch (FlooringNoSuchOrderException e) {
            System.out.println("Right Exception caught!");
        } catch (FlooringPersistenceException e) {
            fail("Incorrect Exception caught");
        }
    }

    @Test
    public void testValidateAndCalculateOrderValid() throws Exception {
        try {
            Order finalizedOrder = service.validateAndCalculateOrder(order1PreValidation, order1PreValidation.getOrderNumber());
            assertEquals(finalizedOrder, order1PostValidation, "Orders should match");
        } catch (FlooringPersistenceException | FlooringOrderValidationException e) {
            fail("No Exceptions should be caught");
        }
    }

    @Test
    public void testValidateAndCalculateOrderInvalidDate() throws Exception {
        try {
            service.validateAndCalculateOrder(order1InvalidDate, order1InvalidDate.getOrderNumber());
        } catch (FlooringOrderValidationException e) {
            System.out.println("Right Exception caught!");
        } catch (FlooringPersistenceException e) {
            fail("Incorrect Exception caught");
        }
    }

    @Test
    public void testValidateAndCalculateOrderInvalidCustomerName() throws Exception {
        try {
            service.validateAndCalculateOrder(order1InvalidCustomerName, order1InvalidCustomerName.getOrderNumber());
        } catch (FlooringOrderValidationException e) {
            System.out.println("Right Exception caught!");
        } catch (FlooringPersistenceException e) {
            fail("Incorrect Exception caught");
        }
    }

    @Test
    public void testValidateAndCalculateOrderInvalidStateName() throws Exception {
        try {
            service.validateAndCalculateOrder(order1InvalidStateName, order1InvalidStateName.getOrderNumber());
        } catch (FlooringOrderValidationException e) {
            System.out.println("Right Exception caught!");
        } catch (FlooringPersistenceException e) {
            fail("Incorrect Exception caught");
        }
    }

    @Test
    public void testValidateAndCalculateOrderInvalidProductName() throws Exception {
        try {
            service.validateAndCalculateOrder(order1InvalidProductName, order1InvalidProductName.getOrderNumber());
        } catch (FlooringOrderValidationException e) {
            System.out.println("Right Exception caught!");
        } catch (FlooringPersistenceException e) {
            fail("Incorrect Exception caught");
        }
    }

    @Test
    public void testValidateAndCalculateOrderInvalidArea() throws Exception {
        try {
            service.validateAndCalculateOrder(order1InvalidArea, order1InvalidArea.getOrderNumber());
        } catch (FlooringOrderValidationException e) {
            System.out.println("Right Exception caught!");
        } catch (FlooringPersistenceException e) {
            fail("Incorrect Exception caught");
        }
    }

    @Test
    public void testValidateAndCalculateOrder2Exceptions() throws Exception {
        try {
            service.validateAndCalculateOrder(order1InvalidDateArea, order1InvalidDateArea.getOrderNumber());
        } catch (FlooringOrderValidationException e) {
            System.out.println("Right Exception caught!");
            Throwable[] allErrors = e.getSuppressed();
            assertEquals(allErrors.length, 2, "There should be two errors");
        } catch (FlooringPersistenceException e) {
            fail("Incorrect Exception caught");
        }
    }

    @Test
    public void testValidateAndCalculateOrder3Exceptions() throws Exception {
        try {
            service.validateAndCalculateOrder(order1InvalidDateStateArea, order1InvalidDateStateArea.getOrderNumber());
        } catch (FlooringOrderValidationException e) {
            System.out.println("Right Exception caught!");
            Throwable[] allErrors = e.getSuppressed();
            assertEquals(allErrors.length, 3, "There should be three errors");
        } catch (FlooringPersistenceException e) {
            fail("Incorrect Exception caught");
        }
    }

    @Test
    public void testValidateAndCalculateOrderAll5Exceptions() throws Exception {
        try {
            service.validateAndCalculateOrder(order1InvalidAll, order1InvalidAll.getOrderNumber());
        } catch (FlooringOrderValidationException e) {
            System.out.println("Right Exception caught!");
            Throwable[] allErrors = e.getSuppressed();
            assertEquals(allErrors.length, 5, "There should be five errors");
        } catch (FlooringPersistenceException e) {
            fail("Incorrect Exception caught");
        }
    }

    @Test
    public void testEditAnOrderFullyValid() throws Exception {
        try {
            Order editedOrder = service.editValidateAndCalculateOrder(order1PreEdit, order1PostValidation);
            assertEquals(editedOrder, order1PostValidation, "Finally Edited Order should be the same as the post validation order");
        } catch (FlooringPersistenceException | FlooringOrderValidationException e) {
            fail("No Exceptions should be caught");
        }
    }

    @Test
    public void testEditAnOrderNoNewInfo() throws Exception {
        try {
            Order editedOrder = service.editValidateAndCalculateOrder(order1PreEditNoNewInfo, order1PostValidation);
            assertEquals(editedOrder, order1PostValidation, "Edited Order should be the same as the post validation order");
        } catch (FlooringPersistenceException | FlooringOrderValidationException e) {
            fail("No Exceptions should be caught");
        }
    }

    @Test
    public void testAddAnOrderYes() throws Exception {
        boolean orderAdded = service.addAnOrder(order1PostValidation, "Y");
        assertTrue(orderAdded, "Order should have been added");
    }

    @Test
    public void testAddAnOrderNo() throws Exception {
        boolean orderAdded = service.addAnOrder(order1PostValidation, "N");
        assertFalse(orderAdded, "Order should not have been added");
    }

    @Test
    public void testEditAnOrderYes() throws Exception {
        boolean orderEdited = service.editAnOrder(order1PostValidation, "Y");
        assertTrue(orderEdited, "Order should have been edited");
    }

    @Test
    public void testEditAnOrderNo() throws Exception {
        boolean orderEdited = service.editAnOrder(order1PostValidation, "N");
        assertFalse(orderEdited, "Order should not have been edited");
    }

    @Test
    public void testRemoveAnOrderYes() throws Exception {
        boolean orderRemoved = service.removeAnOrder(order1PostValidation.getDateOfOrder(), order1PostValidation.getOrderNumber(), "Y");
        assertTrue(orderRemoved, "Order should have been removed");
    }

    @Test
    public void testRemoveAnOrderNo() throws Exception {
        boolean orderRemoved = service.removeAnOrder(order1PostValidation.getDateOfOrder(), order1PostValidation.getOrderNumber(), "N");
        assertFalse(orderRemoved, "Order should not have been removed");
    }

}
