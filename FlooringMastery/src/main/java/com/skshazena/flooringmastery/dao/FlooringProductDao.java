package com.skshazena.flooringmastery.dao;

import com.skshazena.flooringmastery.dto.ProductType;
import java.util.List;

/**
 * Flooring Product Dao - The Product Dao is responsible for managing the
 * various products that are sold at the company.
 *
 * @author Shazena Khan
 *
 * Date Created: Jun 28, 2020
 */
public interface FlooringProductDao {

    ProductType addProductTypeObject(ProductType aProductTypeObject) throws FlooringPersistenceException;

    ProductType getProductTypeObject(String productType) throws FlooringPersistenceException;

    List<ProductType> getAllProductTypeObjects() throws FlooringPersistenceException;

    ProductType editProductTypeObject(ProductType aProductTypeObject) throws FlooringPersistenceException;

    ProductType removeProductTypeObject(String productType) throws FlooringPersistenceException;

}
