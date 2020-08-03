package com.skshazena.flooringmastery.dao;

import com.skshazena.flooringmastery.dto.StateTaxInfo;
import java.util.List;

/**
 * Flooring Tax Dao - The Tax Dao is responsible for managing the various states
 * and their respective tax info that the company sells in.
 *
 * @author Shazena Khan
 *
 * Date Created: Jun 28, 2020
 */
public interface FlooringTaxDao {

    StateTaxInfo addStateTaxInfoObject(StateTaxInfo aStateTaxInfoObject) throws FlooringPersistenceException;

    StateTaxInfo getStateTaxInfoObjectFromAbbreviation(String stateAbbreviation) throws FlooringPersistenceException;

    StateTaxInfo getStateTaxInfoObjectFromName(String stateName) throws FlooringPersistenceException;

    List<StateTaxInfo> getAllStateTaxInfoObjects() throws FlooringPersistenceException;

    StateTaxInfo editStateTaxInfoObject(StateTaxInfo aStateTaxInfoObject) throws FlooringPersistenceException;

    StateTaxInfo removeStateTaxInfoObject(String stateAbbreviation) throws FlooringPersistenceException;

}
