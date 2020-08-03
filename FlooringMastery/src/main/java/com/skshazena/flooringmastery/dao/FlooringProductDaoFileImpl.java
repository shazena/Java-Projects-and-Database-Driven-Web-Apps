package com.skshazena.flooringmastery.dao;

import com.skshazena.flooringmastery.dto.ProductType;
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

/**
 * Flooring Product Dao File Implementation
 *
 * @author Shazena Khan
 *
 * Date Created: Jun 28, 2020
 */
public class FlooringProductDaoFileImpl implements FlooringProductDao {

    private Map<String, ProductType> productInfo = new HashMap<>();
    private static final String DELIMITER = ",";
    private static String PRODUCT_FILE;

    public FlooringProductDaoFileImpl() {
        PRODUCT_FILE = ".\\Data\\Products.txt";
    }

    public FlooringProductDaoFileImpl(String PRODUCT_FILE) {
        this.PRODUCT_FILE = PRODUCT_FILE;
    }

    @Override
    public ProductType addProductTypeObject(ProductType aProductTypeObject) throws FlooringPersistenceException {
        loadProductTypeData();
        ProductType addedProductTypeObject = productInfo.put(aProductTypeObject.getProductType(), aProductTypeObject);
        writeProductTypeData();
        return addedProductTypeObject;
    }

    @Override
    public ProductType getProductTypeObject(String productType) throws FlooringPersistenceException {
        loadProductTypeData();
        return productInfo.get(productType);
    }

    @Override
    public List<ProductType> getAllProductTypeObjects() throws FlooringPersistenceException {
        loadProductTypeData();
        return new ArrayList<ProductType>(productInfo.values());
    }

    @Override
    public ProductType editProductTypeObject(ProductType aProductTypeObject) throws FlooringPersistenceException {
        loadProductTypeData();
        productInfo.remove(aProductTypeObject.getProductType());
        ProductType editedProductTypeObject = productInfo.put(aProductTypeObject.getProductType(), aProductTypeObject);
        writeProductTypeData();
        return editedProductTypeObject;
    }

    @Override
    public ProductType removeProductTypeObject(String productType) throws FlooringPersistenceException {
        loadProductTypeData();
        ProductType removedProductType = productInfo.remove(productType);
        writeProductTypeData();
        return removedProductType;

    }

    private ProductType unmarshallProductType(String productTypeAsString) {
        String[] itemTokens = productTypeAsString.split(DELIMITER);
        String productType = itemTokens[0];
        BigDecimal costPerSquareFoot = new BigDecimal(itemTokens[1]);
        BigDecimal laborCostPerSquareFoot = new BigDecimal(itemTokens[2]);

        ProductType productTypeFromFile = new ProductType(productType, costPerSquareFoot, laborCostPerSquareFoot);

        return productTypeFromFile;
    }

    private String marshallProductType(ProductType aProductTypeObject) {
        String productTypeAsString = aProductTypeObject.getProductType() + DELIMITER;
        productTypeAsString += aProductTypeObject.getCostPerSquareFoot() + DELIMITER;
        productTypeAsString += aProductTypeObject.getLaborCostPerSquareFoot();
        return productTypeAsString;
    }

    private void loadProductTypeData() throws FlooringPersistenceException {
        Scanner scanner;

        try {
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(PRODUCT_FILE)));
        } catch (FileNotFoundException e) {
            throw new FlooringPersistenceException("Could not load product file.");
        }

        String currentLine;
        ProductType currentProductInfo;

        if (scanner.hasNextLine()) {
            scanner.nextLine();//skip header
        }

        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentProductInfo = unmarshallProductType(currentLine);
            productInfo.put(currentProductInfo.getProductType(), currentProductInfo);
        }
        scanner.close();

    }

    private void writeProductTypeData() throws FlooringPersistenceException {
        PrintWriter out;
        try {
            out = new PrintWriter(new FileWriter(PRODUCT_FILE));
        } catch (IOException e) {
            throw new FlooringPersistenceException("Could not save Product file");
        }
        out.println("ProductType,CostPerSquareFoot,LaborCostPerSquareFoot");
        List<ProductType> productTypeList = new ArrayList<>(productInfo.values());
        productTypeList.stream().forEach(
                (productTypeObject) -> {
                    String productTypeObjectAsString = marshallProductType(productTypeObject);
                    out.println(productTypeObjectAsString);
                    out.flush();
                });
        out.close();
    }

}
