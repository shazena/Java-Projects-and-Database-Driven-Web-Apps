package com.skshazena.flooringmastery.advice;

import com.skshazena.flooringmastery.dao.FlooringAuditDao;
import com.skshazena.flooringmastery.dao.FlooringPersistenceException;
import org.aspectj.lang.JoinPoint;

/**
 *
 * @author Shazena Khan
 *
 * Date Created: Jun 28, 2020
 */
public class LoggingAdvice {

    FlooringAuditDao auditDao;

    public LoggingAdvice(FlooringAuditDao auditDao) {
        this.auditDao = auditDao;
    }

    public void writeAuditEntry(JoinPoint jp) {
        Object[] args = jp.getArgs();
        String auditEntry = jp.getSignature().getName() + ": ";

        for (Object currentArg : args) {
            auditEntry += currentArg + " :: ";
        }

        try {
            auditDao.writeAuditEntry(auditEntry);
        } catch (FlooringPersistenceException e) {
            System.err.println(
                    "ERROR: Could not create audit entry in LoggingAdvice.");
        }
    }

    public void writeExceptionAuditEntry(JoinPoint jp, Exception ex) {

        String auditEntry;

        String allErrorsAsString = "";
        String argumentsAsString = "";

        if (jp.getArgs() != null) {
            Object[] args = jp.getArgs();
            for (Object arg : args) {
                argumentsAsString += arg;
            }
            argumentsAsString = ": Arguments: " + argumentsAsString;
            auditEntry = "Exception thrown in method: " + jp.getSignature().getName() + argumentsAsString;
        } else {
            auditEntry = "Exception thrown in method: " + jp.getSignature().getName() + ": " + ex.getMessage();
        }

        Throwable[] allErrors = ex.getSuppressed();
        if (allErrors.length > 0) {
            for (Throwable error : allErrors) {
                allErrorsAsString += error.getMessage() + ", ";
            }
            allErrorsAsString = " Errors: " + allErrorsAsString;
            auditEntry = "Exception thrown in method: " + jp.getSignature().getName() + argumentsAsString + allErrorsAsString;
        } else {
            auditEntry = "Exception thrown in method: " + jp.getSignature().getName() + argumentsAsString + " : " + ex.getMessage();
        }

        try {
            auditDao.writeAuditEntry(auditEntry);
        } catch (FlooringPersistenceException e) {
            System.err.println(
                    "ERROR: Could not create audit entry in LoggingAdvice.");
        }
    }

}
