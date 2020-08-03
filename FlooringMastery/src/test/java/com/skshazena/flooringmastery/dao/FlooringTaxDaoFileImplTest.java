/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skshazena.flooringmastery.dao;

import com.skshazena.flooringmastery.dto.StateTaxInfo;
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
public class FlooringTaxDaoFileImplTest {

    FlooringTaxDao testDao;
    public static StateTaxInfo tax1;
    public static StateTaxInfo tax2;
    public static StateTaxInfo tax3;

    public FlooringTaxDaoFileImplTest() {
    }

    @BeforeAll
    public static void setUpClass() throws Exception {
        //State Tax Object 1 - Distinct tax
        String stateName1 = "West Virginia";
        String stateAbbreviation1 = "WV";
        BigDecimal taxRate1 = new BigDecimal("3.45");
        tax1 = new StateTaxInfo(stateAbbreviation1, stateName1, taxRate1);

        //State Tax Object 2 - Distinct tax
        String stateName2 = "New York";
        String stateAbbreviation2 = "NY";
        BigDecimal taxRate2 = new BigDecimal("8.75");
        tax2 = new StateTaxInfo(stateAbbreviation2, stateName2, taxRate2);

        //State Tax Object 3 - Same name as tax 1, other two fields are distinct (Edited Order1)
        String stateName3 = "Oklahoma";
        BigDecimal taxRate3 = new BigDecimal("4.59");
        tax3 = new StateTaxInfo(stateAbbreviation1, stateName3, taxRate3);

    }

    @BeforeEach
    public void setUp() throws Exception {

        String testFolder = ".\\Test\\Data";
        String testFile = testFolder + "\\Taxes.txt";

        File folder = new File(testFolder);

        if (!folder.exists()) {
            folder.mkdirs();
        }

        new FileWriter(testFile);
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        testDao = ctx.getBean("flooringTaxDao", FlooringTaxDao.class);
    }

    @Test
    public void testAddStateTaxInfo() throws Exception {
        StateTaxInfo addedTax = testDao.addStateTaxInfoObject(tax1);
        assertNull(addedTax, "Added tax should be null");

        StateTaxInfo retrievedStateTaxInfoObjectFromAbbreviation = testDao.getStateTaxInfoObjectFromAbbreviation(tax1.getStateAbbreviation());
        assertEquals(retrievedStateTaxInfoObjectFromAbbreviation, tax1, "Retrieved tax should match tax 1");

        StateTaxInfo retrievedStateTaxInfoObjectFromName = testDao.getStateTaxInfoObjectFromName(tax1.getStateName());
        assertEquals(retrievedStateTaxInfoObjectFromName, tax1, "Retrieved tax should match tax 1");

        assertEquals(retrievedStateTaxInfoObjectFromName, retrievedStateTaxInfoObjectFromAbbreviation, "Both retrieved taxes should be the same");
    }

    @Test
    public void testAdd2GetAllStateTaxInfos() throws Exception {

        StateTaxInfo addedTax1 = testDao.addStateTaxInfoObject(tax1);
        StateTaxInfo addedTax2 = testDao.addStateTaxInfoObject(tax2);

        assertNull(addedTax1, "Added Tax should be null");
        assertNull(addedTax2, "Added Tax should be null");

        StateTaxInfo retrievedStateTaxInfoObjectFromAbbreviation1 = testDao.getStateTaxInfoObjectFromAbbreviation(tax1.getStateAbbreviation());
        StateTaxInfo retrievedStateTaxInfoObjectFromAbbreviation2 = testDao.getStateTaxInfoObjectFromAbbreviation(tax2.getStateAbbreviation());

        assertEquals(tax1, retrievedStateTaxInfoObjectFromAbbreviation1, "Retrieved Tax should be Tax 1");
        assertEquals(tax2, retrievedStateTaxInfoObjectFromAbbreviation2, "Retrieved Tax should be Tax 2");

        List<StateTaxInfo> listOfAllTaxObjects = testDao.getAllStateTaxInfoObjects();
        assertEquals(listOfAllTaxObjects.size(), 2, "List should contain two taxes");
        assertTrue(listOfAllTaxObjects.contains(tax1), "List should contain tax 1");
        assertTrue(listOfAllTaxObjects.contains(tax2), "List should contain tax 2");

    }

    @Test
    public void testEditStateTaxInfo() throws Exception {

        StateTaxInfo addedTax1 = testDao.addStateTaxInfoObject(tax1);
        assertNull(addedTax1, "Added Tax should be null");

        StateTaxInfo retrievedStateTaxInfoObjectFromAbbreviation1 = testDao.getStateTaxInfoObjectFromAbbreviation(tax1.getStateAbbreviation());
        assertEquals(retrievedStateTaxInfoObjectFromAbbreviation1, tax1, "Retrieved Tax should be tax 1");

        StateTaxInfo editedStateTaxInfoObject = testDao.editStateTaxInfoObject(tax3);
        assertNull(editedStateTaxInfoObject, "Edited Tax should  be null");

        StateTaxInfo retrievedStateTaxInfoObjectFromAbbreviation2 = testDao.getStateTaxInfoObjectFromAbbreviation(tax1.getStateAbbreviation());
        assertEquals(retrievedStateTaxInfoObjectFromAbbreviation2, tax3, "Retrieved tax should now be tax 3");

    }

    @Test
    public void testRemoveStateTaxInfos() throws Exception {
        StateTaxInfo addedTax1 = testDao.addStateTaxInfoObject(tax1);
        StateTaxInfo addedTax2 = testDao.addStateTaxInfoObject(tax2);

        assertNull(addedTax1, "Added Tax should be null");
        assertNull(addedTax2, "Added Tax should be null");

        StateTaxInfo retrievedStateTaxInfoObjectFromAbbreviation1 = testDao.getStateTaxInfoObjectFromAbbreviation(tax1.getStateAbbreviation());
        StateTaxInfo retrievedStateTaxInfoObjectFromAbbreviation2 = testDao.getStateTaxInfoObjectFromAbbreviation(tax2.getStateAbbreviation());

        assertEquals(tax1, retrievedStateTaxInfoObjectFromAbbreviation1, "Retrieved Tax should be Tax 1");
        assertEquals(tax2, retrievedStateTaxInfoObjectFromAbbreviation2, "Retrieved Tax should be Tax 2");

        List<StateTaxInfo> listOfAllTaxObjects = testDao.getAllStateTaxInfoObjects();
        assertEquals(listOfAllTaxObjects.size(), 2, "List should contain two taxes");
        assertTrue(listOfAllTaxObjects.contains(tax1), "List should contain tax 1");
        assertTrue(listOfAllTaxObjects.contains(tax2), "List should contain tax 2");

        StateTaxInfo removedStateTaxInfoObject1 = testDao.removeStateTaxInfoObject(tax1.getStateAbbreviation());
        assertEquals(removedStateTaxInfoObject1, tax1, "Removed Tax should match tax 1");

        listOfAllTaxObjects = testDao.getAllStateTaxInfoObjects();
        assertEquals(listOfAllTaxObjects.size(), 1, "List should contain one Tax object");
        assertTrue(listOfAllTaxObjects.contains(tax2), "List should contain tax 2");
        assertFalse(listOfAllTaxObjects.contains(tax1), "List should not contain tax 1");

        StateTaxInfo removedStateTaxInfoObject2 = testDao.removeStateTaxInfoObject(tax2.getStateAbbreviation());
        assertEquals(removedStateTaxInfoObject2, tax2, "Removed tax should be tax 2");

        listOfAllTaxObjects = testDao.getAllStateTaxInfoObjects();
        assertEquals(listOfAllTaxObjects.size(), 0, "List should contain zero taxes");
        assertTrue(listOfAllTaxObjects.isEmpty(), "List should be empty");
        assertFalse(listOfAllTaxObjects.contains(tax1), "List should not contain tax 1");
        assertFalse(listOfAllTaxObjects.contains(tax2), "List should not contain tax 2");

    }

}
