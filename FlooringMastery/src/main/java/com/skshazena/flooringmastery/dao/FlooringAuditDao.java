package com.skshazena.flooringmastery.dao;

/**
 * Flooring Mastery AuditDao - Conatains method that is used for writing log
 * messages
 *
 * @author Shazena Khan
 *
 * Date Created: Jun 28, 2020
 */
public interface FlooringAuditDao {

    void writeAuditEntry(String entry) throws FlooringPersistenceException;
}
