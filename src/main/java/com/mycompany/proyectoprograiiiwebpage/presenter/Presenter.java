/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectoprograiiiwebpage.presenter;

import com.mycompany.proyectoprograiiiwebpage.models.Model;
import com.mycompany.proyectoprograiiiwebpage.views.View;
import java.sql.Date;
import java.time.LocalDate;
import javax.swing.SwingUtilities;

public class Presenter {

    private Model model;
    private View view;

    public Presenter(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    public void run() {
        view.init();
    }

    public void insertCliente(int id, String name, String lastName, String email, String address) {
        boolean success = model.registerClient(id, name, lastName, email, address);
        if (success) {
            view.showSuccessMessage(View.SUCCESSFUL_PURCHASE_MESSAGE);
        } else {
            view.showErrorMessage(View.ERROR_PURCHASE_MESSAGE);
        }
    }

    public void insertProducto(int id, String name, double price, String description) {
        boolean success = model.registerProducto(id, name, price, description);
        if (success) {
            view.showSuccessMessage(View.SUCCESSFUL_CLIENT_REGISTER_MESSAGE);
        } else {
            view.showErrorMessage(View.ERROR_CLIENT_REGISTER_MESSAGE);
        }
    }
    
    public void insertPedido(int id_cliente) {
        int id_random = getRandomNumber(1000, 9999);
        LocalDate fechaPedido = getCurrentlyDate();
        boolean success = model.registerPedido( id_random, id_cliente, fechaPedido);
        if (success) {
            view.showSuccessMessage(View.SUCCESSFUL_ORDER_REGISTER_MESSAGE);
        } else {
            view.showErrorMessage(View.ERROR_CLIENT_REGISTER_MESSAGE);
        }
    }
    
    public void insert_detalle_pedido(){
        
    }
    
    public String[][] getProducts() {
        return model.selectAllProducts();
    }

    public String[] getProductByName(String name) {
        String[] product = new String[4];
        String[][] products = getProducts();
        for (String[] element : products) {
            if (element[1].equals(name)) {
                product = element;
            }
        }
        return product;
    }

    public boolean clientExists(int id){
        return model.clienteExists(id);
    }
    public static int getRandomNumber(int min, int max) {
        return (int) (Math.random() * (max - min + 1)) + min;
    }
    
    public LocalDate getCurrentlyDate(){
        return LocalDate.now();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Model model = new Model();
            View view = new View();
            Presenter presenter = new Presenter(model, view);
            view.setPresenter(presenter);
            presenter.run();

        });
    }

}
