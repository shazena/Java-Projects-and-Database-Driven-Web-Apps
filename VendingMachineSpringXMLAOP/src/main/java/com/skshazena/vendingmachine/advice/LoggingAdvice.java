package com.skshazena.vendingmachine.advice;

import com.skshazena.vendingmachine.dao.VendingMachineAuditDao;
import com.skshazena.vendingmachine.dao.VendingMachinePersistenceException;
import org.aspectj.lang.JoinPoint;

/**
 *
 * @author Shazena Khan
 *
 * Date Created: Jun 22, 2020
 */
public class LoggingAdvice {

    VendingMachineAuditDao auditDao;

    public LoggingAdvice(VendingMachineAuditDao auditDao) {
        this.auditDao = auditDao;
    }

    public void writeAuditEntry(JoinPoint jp) {
        Object[] args = jp.getArgs();
        String auditEntry = jp.getSignature().getName() + ": ";

        for (Object currentArg : args) {
            auditEntry += currentArg;
        }

        try {
            auditDao.writeAuditEntry(auditEntry);
        } catch (VendingMachinePersistenceException e) {
            System.err.println(
                    "ERROR: Could not create audit entry in LoggingAdvice.");
        }
    }
    public void createExceptionAuditEntry(Exception ex){
        String auditEntry = "Exception thrown: "+ ex.getMessage();
        try {
            auditDao.writeAuditEntry(auditEntry);
        } catch (VendingMachinePersistenceException e) {
            System.err.println(
            "ERROR: Could not create audit entry in LoggingAdvice.");
        }
    }
}
