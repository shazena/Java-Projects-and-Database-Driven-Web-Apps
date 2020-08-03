package com.skshazena.flooringmastery.service;

import com.skshazena.flooringmastery.dao.FlooringPersistenceException;
import com.skshazena.flooringmastery.dao.FlooringTaxDao;
import com.skshazena.flooringmastery.dto.StateTaxInfo;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Shazena Khan
 *
 * Date Created: Jul 6, 2020
 */
public class FlooringTaxDaoStubImpl implements FlooringTaxDao {

    public StateTaxInfo firstState;

    public FlooringTaxDaoStubImpl() {
        firstState = new StateTaxInfo("TX", "Texas", new BigDecimal("4.45"));
    }

    @Override
    public StateTaxInfo addStateTaxInfoObject(StateTaxInfo aStateTaxInfoObject) throws FlooringPersistenceException {
        if (aStateTaxInfoObject.getStateAbbreviation().equals(firstState.getStateAbbreviation())) {
            return firstState;
        } else {
            return null;
        }
    }

    @Override
    public StateTaxInfo getStateTaxInfoObjectFromAbbreviation(String stateAbbreviation) throws FlooringPersistenceException {
        if (stateAbbreviation.equals(firstState.getStateAbbreviation())) {
            return firstState;
        } else {
            return null;
        }
    }

    @Override
    public StateTaxInfo getStateTaxInfoObjectFromName(String stateName) throws FlooringPersistenceException {
        if (stateName.equals(firstState.getStateName())) {
            return firstState;
        } else {
            return null;
        }
    }

    @Override
    public List<StateTaxInfo> getAllStateTaxInfoObjects() throws FlooringPersistenceException {
        ArrayList<StateTaxInfo> listOfAllStates = new ArrayList<StateTaxInfo>();
        listOfAllStates.add(firstState);
        return listOfAllStates;
    }

    @Override
    public StateTaxInfo editStateTaxInfoObject(StateTaxInfo aStateTaxInfoObject) throws FlooringPersistenceException {
        if (aStateTaxInfoObject.getStateAbbreviation().equals(firstState.getStateAbbreviation())) {
            return null;
        } else {
            return aStateTaxInfoObject;
        }
    }

    @Override
    public StateTaxInfo removeStateTaxInfoObject(String stateAbbreviation) throws FlooringPersistenceException {
        if (stateAbbreviation.equals(firstState.getStateAbbreviation())) {
            return firstState;
        } else {
            return null;
        }
    }

}
