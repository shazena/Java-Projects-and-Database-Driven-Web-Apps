/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skshazena.flooringmastery.dao;

import com.skshazena.flooringmastery.dto.ProductType;
import java.io.File;
import java.io.FileWriter;
import java.math.BigDecimal;
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
public class FlooringProductDaoFileImplTest {

    FlooringProductDao testDao;
    public static ProductType product1;
    public static ProductType product2;
    public static ProductType product3;

    public FlooringProductDaoFileImplTest() {
    }

    @BeforeAll
    public static void setUpClass() throws Exception {

        //Product 1 - Distinct product
        String type1 = "Uno";
        BigDecimal costPerSquareFoot1 = new BigDecimal("12.45");
        BigDecimal laborCostPerSquareFoot1 = new BigDecimal("3.67");
        product1 = new ProductType(type1, costPerSquareFoot1, laborCostPerSquareFoot1);

        //Product 2 - Distinct product
        String type2 = "Dos";
        BigDecimal costPerSquareFoot2 = new BigDecimal("4.29");
        BigDecimal laborCostPerSquareFoot2 = new BigDecimal("9.23");
        product2 = new ProductType(type2, costPerSquareFoot2, laborCostPerSquareFoot2);

        //Product 3 - Same name as product 1, other two fields are distinct (Edited Order1)
        BigDecimal costPerSquareFoot3 = new BigDecimal("4.29");
        BigDecimal laborCostPerSquareFoot3 = new BigDecimal("9.23");
        product3 = new ProductType(type1, costPerSquareFoot3, laborCostPerSquareFoot3);

    }

    @BeforeEach
    public void setUp() throws Exception {
        String testFolder = ".\\Test\\Data";
        String testFile = testFolder + "\\Products.txt";

        File folder = new File(testFolder);

        if (!folder.exists()) {
            folder.mkdirs();
        }

        new FileWriter(testFile);

        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        testDao = ctx.getBean("flooringProductDao", FlooringProductDao.class);
    }

    @Test
    public void testAddProduct() throws Exception {

        ProductType addedProduct = testDao.addProductTypeObject(product1);
        assertNull(addedProduct, "Added Product should be null");

        ProductType retrievedProduct = testDao.getProductTypeObject(product1.getProductType());
        assertEquals(retrievedProduct, product1, "Retrieved Product should match product 1");
    
    }

    @Test
    public void testAdd2GetAllProducts() throws Exception {

        ProductType addedProduct1 = testDao.addProductTypeObject(product1);
        ProductType addedProduct2 = testDao.addProductTypeObject(product2);

        assertNull(addedProduct1, "Added product 1 should be null");
        assertNull(addedProduct2, "Added product 2 should be null");

        ProductType retrievedProduct1 = testDao.getProductTypeObject(product1.getProductType());
        ProductType retrievedProduct2 = testDao.getProductTypeObject(product2.getProductType());

        assertEquals(product1, retrievedProduct1, "Retrieved Product 1 should be the same as product 1");
        assertEquals(product2, retrievedProduct2, "Retrieved Product 2 should be the same as product 2");

        List<ProductType> listOfAllProductTypeObjects = testDao.getAllProductTypeObjects();
        assertEquals(listOfAllProductTypeObjects.size(), 2, "List should contain two products");
        assertTrue(listOfAllProductTypeObjects.contains(product1), "List should contain product 1");
        assertTrue(listOfAllProductTypeObjects.contains(product2), "List should contain product 2");

    }

    @Test
    public void testEditProduct() throws Exception {

        ProductType addedProduct = testDao.addProductTypeObject(product1);
        assertNull(addedProduct, "Added Product should be null");

        ProductType retrievedProduct = testDao.getProductTypeObject(product1.getProductType());
        assertEquals(retrievedProduct, product1, "Retrieved Product should be product 1");

        ProductType editedProduct = testDao.editProductTypeObject(product3);
        assertNull(editedProduct, "Edited product should return null");

        retrievedProduct = testDao.getProductTypeObject(product1.getProductType());
        assertEquals(retrievedProduct, product3, "Retrieved Product should now be product 3");

    }

    @Test
    public void testRemoveProducts() throws Exception {
        ProductType addedProduct1 = testDao.addProductTypeObject(product1);
        ProductType addedProduct2 = testDao.addProductTypeObject(product2);

        assertNull(addedProduct1, "Added product 1 should be null");
        assertNull(addedProduct2, "Added product 2 should be null");

        ProductType retrievedProduct1 = testDao.getProductTypeObject(product1.getProductType());
        ProductType retrievedProduct2 = testDao.getProductTypeObject(product2.getProductType());

        assertEquals(product1, retrievedProduct1, "Retrieved Product 1 should be the same as product 1");
        assertEquals(product2, retrievedProduct2, "Retrieved Product 2 should be the same as product 2");

        List<ProductType> listOfAllProductTypeObjects = testDao.getAllProductTypeObjects();
        assertEquals(listOfAllProductTypeObjects.size(), 2, "List should contain two products");
        assertTrue(listOfAllProductTypeObjects.contains(product1), "List should contain product 1");
        assertTrue(listOfAllProductTypeObjects.contains(product2), "List should contain product 2");

        ProductType removedProduct1 = testDao.removeProductTypeObject(product1.getProductType());
        assertEquals(removedProduct1, product1, "Removed Product 1 should be product 1");

        listOfAllProductTypeObjects = testDao.getAllProductTypeObjects();
        assertEquals(listOfAllProductTypeObjects.size(), 1, "List should only contain one product");
        assertTrue(listOfAllProductTypeObjects.contains(product2), "List should contain product 2");
        assertFalse(listOfAllProductTypeObjects.contains(product1), "List should not contain product 1");

        ProductType removedProduct2 = testDao.removeProductTypeObject(product2.getProductType());
        assertEquals(removedProduct2, product2, "Removed product should be product 2");

        listOfAllProductTypeObjects = testDao.getAllProductTypeObjects();
        assertEquals(listOfAllProductTypeObjects.size(), 0, "List should have zero products");
        assertTrue(listOfAllProductTypeObjects.isEmpty(), "List should be empty");
        assertFalse(listOfAllProductTypeObjects.contains(product1), "List should not contain product 1");
        assertFalse(listOfAllProductTypeObjects.contains(product2), "List should not contain product 2");

    }

}
