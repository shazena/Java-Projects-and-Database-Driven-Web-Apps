package com.skshazena.flooringmastery.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Order DTO - contains properties that belong to the Order DTO and also
 * comprises of a StateTaxInfo DTO and a ProductType DTO.
 *
 * @author Shazena Khan
 *
 * Date Created: Jun 28, 2020
 */
public class Order {

    private LocalDate dateOfOrder;
    private String customerName;
    private StateTaxInfo taxInfo;//3 pieces of info
    private ProductType productType;//3 pieces of info
    private BigDecimal area;
    private int orderNumber;
    private BigDecimal materialCostForOrder;
    private BigDecimal laborCostForOrder;
    private BigDecimal taxForOrder;
    private BigDecimal totalForOrder;

    public Order(LocalDate dateOfOrder, String customerName, String stateName, String productTypeName, BigDecimal area) {
        this.dateOfOrder = dateOfOrder;
        this.customerName = customerName;
        this.taxInfo = new StateTaxInfo(stateName);
        this.productType = new ProductType(productTypeName);
        this.area = area;
    }

    public Order(LocalDate dateOfOrder, String customerName, StateTaxInfo taxInfo, ProductType productType, BigDecimal area, int orderNumber, BigDecimal materialCostForOrder, BigDecimal laborCostForOrder, BigDecimal taxForOrder, BigDecimal totalForOrder) {
        this.dateOfOrder = dateOfOrder;
        this.customerName = customerName;
        this.taxInfo = taxInfo;
        this.productType = productType;
        this.area = area;
        this.orderNumber = orderNumber;
        this.materialCostForOrder = materialCostForOrder;
        this.laborCostForOrder = laborCostForOrder;
        this.taxForOrder = taxForOrder;
        this.totalForOrder = totalForOrder;
    }

    public LocalDate getDateOfOrder() {
        return dateOfOrder;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public StateTaxInfo getTaxInfo() {
        return taxInfo;
    }

    public void setTaxInfo(StateTaxInfo taxInfo) {
        this.taxInfo = taxInfo;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public BigDecimal getMaterialCostForOrder() {
        return materialCostForOrder;
    }

    public void setMaterialCostForOrder(BigDecimal materialCostForOrder) {
        this.materialCostForOrder = materialCostForOrder;
    }

    public BigDecimal getLaborCostForOrder() {
        return laborCostForOrder;
    }

    public void setLaborCostForOrder(BigDecimal laborCostForOrder) {
        this.laborCostForOrder = laborCostForOrder;
    }

    public BigDecimal getTaxForOrder() {
        return taxForOrder;
    }

    public void setTaxForOrder(BigDecimal taxForOrder) {
        this.taxForOrder = taxForOrder;
    }

    public BigDecimal getTotalForOrder() {
        return totalForOrder;
    }

    public void setTotalForOrder(BigDecimal totalForOrder) {
        this.totalForOrder = totalForOrder;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 19 * hash + Objects.hashCode(this.dateOfOrder);
        hash = 19 * hash + Objects.hashCode(this.customerName);
        hash = 19 * hash + Objects.hashCode(this.taxInfo);
        hash = 19 * hash + Objects.hashCode(this.productType);
        hash = 19 * hash + Objects.hashCode(this.area);
        hash = 19 * hash + this.orderNumber;
        hash = 19 * hash + Objects.hashCode(this.materialCostForOrder);
        hash = 19 * hash + Objects.hashCode(this.laborCostForOrder);
        hash = 19 * hash + Objects.hashCode(this.taxForOrder);
        hash = 19 * hash + Objects.hashCode(this.totalForOrder);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Order other = (Order) obj;
        if (this.orderNumber != other.orderNumber) {
            return false;
        }
        if (!Objects.equals(this.customerName, other.customerName)) {
            return false;
        }
        if (!Objects.equals(this.dateOfOrder, other.dateOfOrder)) {
            return false;
        }
        if (!Objects.equals(this.taxInfo, other.taxInfo)) {
            return false;
        }
        if (!Objects.equals(this.productType, other.productType)) {
            return false;
        }
        if (!Objects.equals(this.area, other.area)) {
            return false;
        }
        if (!Objects.equals(this.materialCostForOrder, other.materialCostForOrder)) {
            return false;
        }
        if (!Objects.equals(this.laborCostForOrder, other.laborCostForOrder)) {
            return false;
        }
        if (!Objects.equals(this.taxForOrder, other.taxForOrder)) {
            return false;
        }
        if (!Objects.equals(this.totalForOrder, other.totalForOrder)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Order{" + "dateOfOrder=" + dateOfOrder + ", customerName=" + customerName + ", taxInfo=" + taxInfo + ", productType=" + productType + ", area=" + area + ", orderNumber=" + orderNumber + ", materialCostForOrder=" + materialCostForOrder + ", laborCostForOrder=" + laborCostForOrder + ", taxForOrder=" + taxForOrder + ", totalForOrder=" + totalForOrder + '}';
    }

}
