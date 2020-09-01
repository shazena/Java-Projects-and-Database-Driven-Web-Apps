package com.skshazena.bullsandcows.controller;

import com.skshazena.bullsandcows.service.BullsAndCowsGameDoesNotExistException;
import com.skshazena.bullsandcows.service.BullsAndCowsGameIsAlreadyFinishedException;
import com.skshazena.bullsandcows.service.BullsAndCowsInvalidGuessException;
import com.skshazena.bullsandcows.service.BullsAndCowsNoGamesException;
import com.skshazena.bullsandcows.service.BullsAndCowsNoRoundsForGameException;
import java.sql.SQLIntegrityConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 *
 * @author Shazena Khan
 *
 * Date Created: Aug 24, 2020
 */
@ControllerAdvice
@RestController
public class BullsAndCowsExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public final ResponseEntity<Error> handleSQLException(
            SQLIntegrityConstraintViolationException ex,
            WebRequest request) {
        Error err = new Error();
        err.setMessage(ex.getMessage());
        return new ResponseEntity<>(err, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(BullsAndCowsNoRoundsForGameException.class)
    public final ResponseEntity<Error> handleNoRoundsException(
            BullsAndCowsNoRoundsForGameException ex,
            WebRequest request) {
        Error err = new Error();
        err.setMessage(ex.getMessage());
        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BullsAndCowsInvalidGuessException.class)
    public final ResponseEntity<Error> handleInvalidGuessException(
            BullsAndCowsInvalidGuessException ex,
            WebRequest request) {
        Error err = new Error();
        err.setMessage(ex.getMessage());
        return new ResponseEntity<>(err, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(BullsAndCowsGameDoesNotExistException.class)
    public final ResponseEntity<Error> handleGameDoesNotExistException(
            BullsAndCowsGameDoesNotExistException ex,
            WebRequest request) {
        Error err = new Error();
        err.setMessage(ex.getMessage());
        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BullsAndCowsNoGamesException.class)
    public final ResponseEntity<Error> handleNoGamesException(
            BullsAndCowsNoGamesException ex,
            WebRequest request) {
        Error err = new Error();
        err.setMessage(ex.getMessage());
        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BullsAndCowsGameIsAlreadyFinishedException.class)
    public final ResponseEntity<Error> handleGameAlreadyFinishedException(
            BullsAndCowsGameIsAlreadyFinishedException ex,
            WebRequest request) {
        Error err = new Error();
        err.setMessage(ex.getMessage());
        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Error> handleOtherExceptions(
            Exception ex,
            WebRequest request) {
        Error err = new Error();
        err.setMessage(ex.getMessage());
        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }

}
