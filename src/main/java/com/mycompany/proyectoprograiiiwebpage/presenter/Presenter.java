/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectoprograiiiwebpage.presenter;

import com.mycompany.proyectoprograiiiwebpage.models.Model;
import com.mycompany.proyectoprograiiiwebpage.views.View;
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

    public String[][] getProducts() {
        return model.selectAllProducts();
    }

    public String[] getProductByName(String name){
        String[] product = new String[4];
        String[][] products = getProducts();
        for (String[] element : products) {
            if (element[1].equals(name)) {
                product = element;
            }
        }
        return product;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Model model = new Model();
            View view = new View();
            Presenter presenter = new Presenter(model, view);
            view.setPresenter(presenter);
            presenter.run();
            String[][] products = {
                {"1", "Producto 1", "100", "Descripción del producto 1"},
                {"2", "Producto 2", "200", "Descripción del producto 2"},
                {"3", "Producto 3", "300", "Descripción del producto 3"}
            };
            //view.showCartProducts();
        });
    }

}
