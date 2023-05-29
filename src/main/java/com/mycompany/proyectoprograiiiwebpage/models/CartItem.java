/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectoprograiiiwebpage.models;

public class CartItem {
    private String productName;
    private String price;
    private int units;

    public CartItem(String productName, String price, int units) {
        this.productName = productName;
        this.price = price;
        this.units = units;
    }

    public String getProductName() {
        return productName;
    }

    public String getPrice() {
        return price;
    }

    public int getUnits() {
        return units;
    }
}
