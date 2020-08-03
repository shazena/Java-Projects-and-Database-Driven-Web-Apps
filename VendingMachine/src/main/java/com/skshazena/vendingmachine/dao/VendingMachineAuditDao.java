/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skshazena.vendingmachine.dao;

/**
 * Vending Machine Audit Dao Interface
 *
 * @author Shazena
 */
public interface VendingMachineAuditDao {

    /**
     * writeAuditEntry - this method writes a message into the audit file to
     * keep track of when items are purchased, added, updated, or removed.
     *
     * @param entry {String} The message
     * @throws VendingMachinePersistenceException
     */
    public void writeAuditEntry(String entry) throws VendingMachinePersistenceException;

}
