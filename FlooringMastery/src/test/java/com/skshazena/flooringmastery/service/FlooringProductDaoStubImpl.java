package com.skshazena.flooringmastery.service;

import com.skshazena.flooringmastery.dao.FlooringPersistenceException;
import com.skshazena.flooringmastery.dao.FlooringProductDao;
import com.skshazena.flooringmastery.dto.ProductType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Shazena Khan
 *
 * Date Created: Jul 6, 2020
 */
public class FlooringProductDaoStubImpl implements FlooringProductDao {

    public ProductType firstProduct;

    public FlooringProductDaoStubImpl() {
        firstProduct = new ProductType("Laminate", new BigDecimal("1.75"), new BigDecimal("2.10"));
    }

    @Override
    public ProductType addProductTypeObject(ProductType aProductTypeObject) throws FlooringPersistenceException {
        if (aProductTypeObject.getProductType().equals(firstProduct.getProductType())) {
            return firstProduct;
        } else {
            return null;
        }
    }

    @Override
    public ProductType getProductTypeObject(String productType) throws FlooringPersistenceException {
        if (productType.equals(firstProduct.getProductType())) {
            return firstProduct;
        } else {
            return null;
        }
    }

    @Override
    public List<ProductType> getAllProductTypeObjects() throws FlooringPersistenceException {
        ArrayList<ProductType> listOfAllProducts = new ArrayList<ProductType>();
        listOfAllProducts.add(firstProduct);
        return listOfAllProducts;
    }

    @Override
    public ProductType editProductTypeObject(ProductType aProductTypeObject) throws FlooringPersistenceException {
        if (aProductTypeObject.getProductType().equals(firstProduct.getProductType())) {
            return null;
        } else {
            return firstProduct;
        }
    }

    @Override
    public ProductType removeProductTypeObject(String productType) throws FlooringPersistenceException {
        if (productType.equals(firstProduct.getProductType())) {
            return firstProduct;
        } else {
            return null;
        }
    }
}
