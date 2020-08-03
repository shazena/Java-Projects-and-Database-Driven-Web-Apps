package com.skshazena.vendingmachine.dto;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Vending Machine Item DTO - contains Item name, item price, and quantity of
 * item in inventory
 *
 * @author Shazena Khan
 *
 * Date Created: Jun 5, 2020
 */
public class Item {

    private String name;
    private BigDecimal price;
    private int quantity;

    public Item(String name, BigDecimal price, int quanity) {
        this.name = name;
        this.price = price;
        this.quantity = quanity;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        if (price == null) {
            return null;
        } else {
            return price.setScale(2);
        }
    }

    public void setPrice(BigDecimal price) {
        this.price = price.setScale(2);
    }

    public int getQuanity() {
        return quantity;
    }

    public void setQuanity(int quanity) {
        this.quantity = quanity;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + Objects.hashCode(this.name);
        hash = 23 * hash + Objects.hashCode(this.price);
        hash = 23 * hash + this.quantity;
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
        final Item other = (Item) obj;
        if (this.quantity != other.quantity) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.price, other.price)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Item{" + "name = " + name
                + ", price = " + price
                + ", quanity = " + quantity + '}';
    }

}
