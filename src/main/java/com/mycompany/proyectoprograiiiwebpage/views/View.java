/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectoprograiiiwebpage.views;

import com.mycompany.proyectoprograiiiwebpage.presenter.Presenter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class View extends JFrame {

    private Presenter presenter;
    private DefaultListModel<String> cartListModel;

    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    public void init() {
        setTitle("Tienda Virtual");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        showGUIProducts();
    }

    public void showGUIProducts() {
        JPanel panel = new JPanel();
        JPanel navPanel = createNavigationPanel();
        panel.add(navPanel);
        panel.setBorder(new EmptyBorder(15, 15, 10, 15));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        cartListModel = new DefaultListModel<>();

        String[][] products = presenter.getProducts();

        for (String[] product : products) {
            JPanel productPanel = new JPanel(new GridBagLayout());

            JLabel nameLabel = new JLabel(product[1]);
            JLabel priceLabel = new JLabel("$ " + product[2]);
            JTextArea descriptionArea = new JTextArea(product[3]);
            descriptionArea.setEditable(false);
            descriptionArea.setLineWrap(true);
            descriptionArea.setWrapStyleWord(true);
            descriptionArea.setPreferredSize(new Dimension(500, 50));
            JButton addButton = new JButton("Agregar al carrito");
            JButton buyButton = new JButton("Comprar");

            GridBagConstraints constraints = new GridBagConstraints();
            constraints.gridx = 0;
            constraints.gridy = 0;
            constraints.anchor = GridBagConstraints.WEST;
            constraints.insets = new Insets(5, 5, 5, 5);

            productPanel.add(nameLabel, constraints);

            constraints.gridx = 1;
            constraints.anchor = GridBagConstraints.EAST;
            productPanel.add(priceLabel, constraints);

            constraints.gridx = 0;
            constraints.gridy++;
            constraints.gridwidth = 2;
            constraints.anchor = GridBagConstraints.ABOVE_BASELINE;
            productPanel.add(descriptionArea, constraints);

            constraints.gridx = 1;
            constraints.gridy++;
            constraints.gridwidth = 1;

            productPanel.add(addButton, constraints);

            constraints.gridx = 0;
            productPanel.add(buyButton, constraints);

            addButton.addActionListener((ActionEvent e) -> {
                cartListModel.addElement(product[1]);
                JOptionPane.showMessageDialog(this, "Item agregado al carrito: " + product[1]);
            });

            productPanel.setBorder(BorderFactory.createLineBorder(Color.lightGray));
            panel.add(productPanel);
        }

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        pack();

        setExtendedState(JFrame.MAXIMIZED_VERT);
        setLocationRelativeTo(null);

    }

    public JPanel createNavigationPanel() {
        JPanel navigationPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        navigationPanel.setBackground(Color.LIGHT_GRAY);

        JButton loginButton = new JButton("Iniciar sesión");
        loginButton.addActionListener((ActionEvent e) -> {

        });

        JButton cartButton = new JButton("Ver carrito");
        cartButton.addActionListener((ActionEvent e) -> {
            String[][] products = {
                {"1", "Producto 1", "100", "Descripción del producto 1"},
                {"2", "Producto 2", "200", "Descripción del producto 2"},
                {"3", "Producto 3", "300", "Descripción del producto 3"}
            };
            showCartProducts();
        });

        navigationPanel.add(loginButton);
        navigationPanel.add(cartButton);

        return navigationPanel;
    }

    /*
    public void showCartProducts() {
        JFrame cartFrame = new JFrame("Carrito");
        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(15, 15, 10, 15));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JList<String> cartList = new JList<>(cartListModel);

        JScrollPane scrollPane = new JScrollPane(cartList);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        panel.add(scrollPane);

        cartFrame.getContentPane().setLayout(new BorderLayout());
        cartFrame.getContentPane().add(panel, BorderLayout.CENTER);
        cartFrame.pack();

        cartFrame.setLocationRelativeTo(null);
        cartFrame.setVisible(true);
    }*/
    public void showCartProducts() {
        JFrame cartFrame = new JFrame("Carrito");
        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(15, 15, 10, 15));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        for (int i = 0; i < cartListModel.size(); i++) {
            String productName = cartListModel.elementAt(i);
            String[] product = presenter.getProductByName(productName);
            JPanel productPanel = new JPanel(new GridBagLayout());

            JLabel nameLabel = new JLabel(product[1]);
            JLabel priceLabel = new JLabel("$ " + product[2]);
            JLabel quatityLabel = new JLabel("Cantidad:");
            JButton removeButton = new JButton("Sacar del carro");
            SpinnerNumberModel spinnerModel = new SpinnerNumberModel(1, 1, 100, 1);
            JSpinner quantitySpinner = new JSpinner(spinnerModel);

            GridBagConstraints constraints = new GridBagConstraints();
            constraints.gridx = 0;
            constraints.gridy = 0;
            constraints.anchor = GridBagConstraints.WEST;
            constraints.insets = new Insets(5, 5, 5, 5);

            productPanel.add(nameLabel, constraints);

            constraints.gridx = 1;
            constraints.anchor = GridBagConstraints.EAST;
            productPanel.add(priceLabel, constraints);

            constraints.gridy++;
            constraints.gridwidth = 1;
            constraints.anchor = GridBagConstraints.WEST;
            productPanel.add(quantitySpinner, constraints);

            constraints.gridx = 0;
            productPanel.add(quatityLabel, constraints);
            constraints.gridy++;
            productPanel.add(removeButton, constraints);

            removeButton.addActionListener((ActionEvent e) -> {
                cartListModel.removeElement(product[1]);
                cartFrame.dispose();
                if (cartListModel.size() != 0) {
                    showCartProducts();
                }
            });

            productPanel.setBorder(BorderFactory.createLineBorder(Color.lightGray));
            panel.add(productPanel);
        }

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        cartFrame.getContentPane().setLayout(new BorderLayout());
        cartFrame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        cartFrame.pack();

        cartFrame.setLocationRelativeTo(null);
        cartFrame.setVisible(true);

    }

}
