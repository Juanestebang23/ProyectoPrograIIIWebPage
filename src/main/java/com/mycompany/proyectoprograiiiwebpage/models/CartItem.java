/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectoprograiiiwebpage.models;

public class CartItem {
    private int id;
    private String productName;
    private String price;
    private int units;

    public CartItem(int id, String productName, String price, int units) {
        this.id = id;
        this.productName = productName;
        this.price = price;
        this.units = units;
    }

    public int getId() {
        return id;
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

    public void setUnits(int units) {
        this.units = units;
    }
    
    
}
