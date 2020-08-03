package com.skshazena.flooringmastery.dao;

import com.skshazena.flooringmastery.dto.Order;
import java.util.List;

/**
 * Flooring Export Dao - The export dao is reponsible for taking a list of
 * Orders and storing them into a backup file.
 *
 * @author Shazena Khan
 *
 * Date Created: Jun 28, 2020
 */
public interface FlooringExportDao {

    void createBackupOfAllOrders(List<Order> listOfAllOrders) throws FlooringPersistenceException;

}
