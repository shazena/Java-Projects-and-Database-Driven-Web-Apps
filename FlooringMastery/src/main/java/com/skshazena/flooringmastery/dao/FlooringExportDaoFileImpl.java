package com.skshazena.flooringmastery.dao;

import com.skshazena.flooringmastery.dto.Order;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Flooring Export Dao File Implementation
 *
 * @author Shazena Khan
 *
 * Date Created: Jun 28, 2020
 */
public class FlooringExportDaoFileImpl implements FlooringExportDao {

    private static final String DELIMITER = ",";
    private final String BACKUP_FILE;

    public FlooringExportDaoFileImpl(String BACKUP_FILE) {
        this.BACKUP_FILE = BACKUP_FILE;
    }

    public FlooringExportDaoFileImpl() {
        BACKUP_FILE = ".\\Backup\\DataExport.txt";
    }

    @Override
    public void createBackupOfAllOrders(List<Order> listOfAllOrders) throws FlooringPersistenceException {
        PrintWriter out;
        try {
            out = new PrintWriter(new FileWriter(BACKUP_FILE));
        } catch (IOException e) {
            throw new FlooringPersistenceException("Could not save Backup file");
        }
        out.println("OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total,OrderDate");

        listOfAllOrders.stream().forEach(
                (order) -> {
                    String orderAsString = marshallOrderForBackup(order);
                    out.println(orderAsString);
                    out.flush();
                });
        out.close();
    }

    private String marshallOrderForBackup(Order anOrder) {
        String orderAsString = anOrder.getOrderNumber() + DELIMITER;
        orderAsString += anOrder.getCustomerName() + DELIMITER;
        orderAsString += anOrder.getTaxInfo().getStateAbbreviation() + DELIMITER;
        orderAsString += anOrder.getTaxInfo().getTaxRate() + DELIMITER;
        orderAsString += anOrder.getProductType().getProductType() + DELIMITER;
        orderAsString += anOrder.getArea() + DELIMITER;
        orderAsString += anOrder.getProductType().getCostPerSquareFoot() + DELIMITER;
        orderAsString += anOrder.getProductType().getLaborCostPerSquareFoot() + DELIMITER;
        orderAsString += anOrder.getMaterialCostForOrder() + DELIMITER;
        orderAsString += anOrder.getLaborCostForOrder() + DELIMITER;
        orderAsString += anOrder.getTaxForOrder() + DELIMITER;
        orderAsString += anOrder.getTotalForOrder() + DELIMITER;
        orderAsString += anOrder.getDateOfOrder().format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        return orderAsString;
    }
}
