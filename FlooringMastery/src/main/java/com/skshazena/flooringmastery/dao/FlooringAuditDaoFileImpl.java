package com.skshazena.flooringmastery.dao;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

/**
 * Flooring Mastery Audit Dao File Implementation - Writes Strings to log file
 *
 * @author Shazena Khan
 *
 * Date Created: Jun 28, 2020
 */
public class FlooringAuditDaoFileImpl implements FlooringAuditDao {

    public static final String AUDIT_FILE = "FlooringAudit.txt";

    @Override
    public void writeAuditEntry(String entry) throws FlooringPersistenceException {
        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(AUDIT_FILE, true));
        } catch (IOException e) {
            throw new FlooringPersistenceException("Could not persist audit information.", e);
        }
        LocalDateTime timestamp = LocalDateTime.now();
        out.println(timestamp.toString() + " : " + entry);
        out.flush();
    }
}
