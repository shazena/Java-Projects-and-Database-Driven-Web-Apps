package com.skshazena.flooringmastery.dao;

import com.skshazena.flooringmastery.dto.StateTaxInfo;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Flooring Tax Dao File Implementation
 *
 * @author Shazena Khan
 *
 * Date Created: Jun 28, 2020
 */
public class FlooringTaxDaoFileImpl implements FlooringTaxDao {

    private Map<String, StateTaxInfo> stateTaxInfoMap = new HashMap<>();
    private static final String DELIMITER = ",";
    private final String TAX_FILE;

    public FlooringTaxDaoFileImpl() {
        TAX_FILE = ".\\Data\\Taxes.txt";
    }

    public FlooringTaxDaoFileImpl(String TAX_FILE) {
        this.TAX_FILE = TAX_FILE;
    }

    @Override
    public StateTaxInfo addStateTaxInfoObject(StateTaxInfo aStateTaxInfoObject) throws FlooringPersistenceException {
        loadStateTaxInfoData();
        StateTaxInfo addedStateTaxInfoObject = stateTaxInfoMap.put(aStateTaxInfoObject.getStateAbbreviation(), aStateTaxInfoObject);
        writeStateTaxInfoData();
        return addedStateTaxInfoObject;
    }

    @Override
    public StateTaxInfo getStateTaxInfoObjectFromAbbreviation(String stateAbbreviation) throws FlooringPersistenceException {
        loadStateTaxInfoData();
        return stateTaxInfoMap.get(stateAbbreviation);
    }

    @Override
    public StateTaxInfo getStateTaxInfoObjectFromName(String stateName) throws FlooringPersistenceException {
        loadStateTaxInfoData();
        //FIXME Not sure this is the best way to do this, but it works for now
        ArrayList<StateTaxInfo> listOfStates = new ArrayList<>(stateTaxInfoMap.values());
        List<StateTaxInfo> listOfMatchingStates = listOfStates.stream()
                .filter(x -> x.getStateName().equals(stateName)).collect(Collectors.toList());
        if (listOfMatchingStates.size() == 1) {
            return listOfMatchingStates.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<StateTaxInfo> getAllStateTaxInfoObjects() throws FlooringPersistenceException {
        loadStateTaxInfoData();
        return new ArrayList<StateTaxInfo>(stateTaxInfoMap.values());
    }

    @Override
    public StateTaxInfo editStateTaxInfoObject(StateTaxInfo aStateTaxInfoObject) throws FlooringPersistenceException {
        loadStateTaxInfoData();
        stateTaxInfoMap.remove(aStateTaxInfoObject.getStateAbbreviation());//FIXME consider assigning this to the "EditedStateTaxInfoObject" variable and then overwriting it below. 
        StateTaxInfo editedStateTaxInfoObject = stateTaxInfoMap.put(aStateTaxInfoObject.getStateAbbreviation(), aStateTaxInfoObject);
        writeStateTaxInfoData();
        return editedStateTaxInfoObject;
    }

    @Override
    public StateTaxInfo removeStateTaxInfoObject(String stateAbbreviation) throws FlooringPersistenceException {
        loadStateTaxInfoData();
        StateTaxInfo removedStateTaxInfoObject = stateTaxInfoMap.remove(stateAbbreviation);
        writeStateTaxInfoData();
        return removedStateTaxInfoObject;
    }

    private StateTaxInfo unmarshallStateTaxInfo(String stateTaxInfoAsString) {
        String[] itemTokens = stateTaxInfoAsString.split(DELIMITER);
        String stateAbbreviation = itemTokens[0];
        String stateName = itemTokens[1];
        BigDecimal taxRate = new BigDecimal(itemTokens[2]);

        StateTaxInfo stateTaxInfoFromFile = new StateTaxInfo(stateAbbreviation, stateName, taxRate);

        return stateTaxInfoFromFile;
    }

    private String marshallStateTaxInfo(StateTaxInfo aStateTaxInfoObject) {
        String stateTaxInfoAsString = aStateTaxInfoObject.getStateAbbreviation() + DELIMITER;
        stateTaxInfoAsString += aStateTaxInfoObject.getStateName() + DELIMITER;
        stateTaxInfoAsString += aStateTaxInfoObject.getTaxRate();
        return stateTaxInfoAsString;
    }

    private void loadStateTaxInfoData() throws FlooringPersistenceException {
        Scanner scanner;

        try {
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(TAX_FILE)));
        } catch (FileNotFoundException e) {
            throw new FlooringPersistenceException("Could not load tax file.");
        }

        String currentLine;
        StateTaxInfo currentTaxInfo;

        if (scanner.hasNextLine()) {
            scanner.nextLine();//skip header line
        }

        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentTaxInfo = unmarshallStateTaxInfo(currentLine);
            stateTaxInfoMap.put(currentTaxInfo.getStateAbbreviation(), currentTaxInfo);
        }
        scanner.close();
    }

    private void writeStateTaxInfoData() throws FlooringPersistenceException {
        PrintWriter out;
        try {
            out = new PrintWriter(new FileWriter(TAX_FILE));
        } catch (IOException e) {
            throw new FlooringPersistenceException("Could not save Tax file");
        }
        out.println("State,StateName,TaxRate");
        List<StateTaxInfo> stateTaxInfoList = new ArrayList<>(stateTaxInfoMap.values());
        stateTaxInfoList.stream().forEach(
                (stateTaxInfoObject) -> {
                    String stateTaxInfoObjectAsString = marshallStateTaxInfo(stateTaxInfoObject);
                    out.println(stateTaxInfoObjectAsString);
                    out.flush();
                });
        out.close();
    }

}
