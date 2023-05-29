/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectoprograiiiwebpage.views;

import com.mycompany.proyectoprograiiiwebpage.models.CartItem;
import com.mycompany.proyectoprograiiiwebpage.presenter.Presenter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class View extends JFrame {

    public static final Dimension SCREEN_DIMENSION = Toolkit.getDefaultToolkit().getScreenSize();
    public static final String SUCCESSFUL_PURCHASE_MESSAGE = "Gracias por su compra";
    public static final String SUCCESSFUL_CLIENT_REGISTER_MESSAGE = "Cliente registrado exitosamente";
    public static final String SUCCESSFUL_ORDER_REGISTER_MESSAGE = "Pedido registrado exitosamente";
    public static final String ERROR_PURCHASE_MESSAGE = "Error al realizar la compra";
    public static final String ERROR_CLIENT_REGISTER_MESSAGE = "Error al registrar el cliente";
    public static final String ERROR_ORDER_REGISTER_MESSAGE = "Error al registrar el pedido";

    private Presenter presenter;
    private ArrayList<CartItem> cartList;
    private ArrayList<CartItem> auxCartList;// tabla auxiliar para no perder el carrito
    private JFrame cartFrame;
    private boolean unitaryPurchase;

    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    public void init() {
        setTitle("Tienda Virtual");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        showGUIProducts();

        cartFrame = new JFrame("Carrito");
        cartFrame.setResizable(false);
        cartFrame.setPreferredSize(new Dimension(300, 400));
    }

    public void showGUIProducts() {

        JPanel panel = new JPanel();
        JPanel navPanel = createNavigationPanel();
        panel.add(navPanel);

        panel.setBorder(new EmptyBorder(15, 15, 10, 15));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        cartList = new ArrayList<>();

        String[][] products = presenter.getProducts();

        for (String[] product : products) {
            JPanel productPanel = new JPanel(new GridBagLayout());
            CartItem item = new CartItem(product[1], product[2], 1);

            JLabel nameLabel = new JLabel(item.getProductName());
            JLabel priceLabel = new JLabel("$ " + item.getPrice());
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
            panel.add(productPanel);

            addButton.addActionListener((ActionEvent e) -> {
                unitaryPurchase = false;
                if (productExists(item.getProductName())) {
                    JOptionPane.showMessageDialog(this, "Este producto ya esta agregado al carrito");
                } else {
                    cartList.add(item);
                    JOptionPane.showMessageDialog(this, "Producto agregado al carrito: " + product[1]);
                    showCartProducts();
                }

            });

            buyButton.addActionListener((ActionEvent e) -> {
                unitaryPurchase = true;
                auxCartList = new ArrayList<>();
                auxCartList.add(item);
                showGUI_BuyProduct();
            });
            productPanel.setBorder(BorderFactory.createLineBorder(Color.lightGray));
        }

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        pack();

        setSize(new Dimension(580, SCREEN_DIMENSION.height - 32));
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
            if (cartList.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El carrito esta vacio");
            } else {
                unitaryPurchase = false;
                showCartProducts();
            }
        });

        navigationPanel.add(loginButton);
        navigationPanel.add(cartButton);

        return navigationPanel;
    }

    public void showCartProducts() {
        JPanel panel = new JPanel();
        refreshFrame(cartFrame, panel);
        panel.setBorder(new EmptyBorder(15, 15, 10, 15));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        for (int i = 0; i < cartList.size(); i++) {
            CartItem item = cartList.get(i);
            JPanel productPanel = new JPanel(new GridBagLayout());

            JLabel nameLabel = new JLabel(item.getProductName());
            JLabel priceLabel = new JLabel("$ " + item.getPrice());
            JLabel quatityLabel = new JLabel("Cantidad:");
            JButton removeButton = new JButton("Sacar del carro");
            SpinnerNumberModel spinnerModel = new SpinnerNumberModel(item.getUnits(), 1, 100, 1);
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

            spinnerModel.addChangeListener(e -> {
                int selectedUnits = (int) spinnerModel.getValue();
                item.setUnits(selectedUnits);
            });

            removeButton.addActionListener((ActionEvent e) -> {
                cartList.remove(item);
                cartFrame.dispose();
                if (!cartList.isEmpty()) {
                    showCartProducts();
                }
            });

            productPanel.setBorder(BorderFactory.createLineBorder(Color.lightGray));
            panel.add(productPanel);
        }

        JButton buyCart = new JButton("Comprar Carrito");
        buyCart.setAlignmentX(CENTER_ALIGNMENT);

        buyCart.addActionListener((ActionEvent e) -> {
            cartFrame.dispose();
            showGUI_BuyProduct();
        });
        panel.add(buyCart);

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        cartFrame.getContentPane().setLayout(new BorderLayout());
        cartFrame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        cartFrame.pack();

        cartFrame.setLocationRelativeTo(cartFrame);
        cartFrame.setVisible(true);
    }

    public void showGUI_BuyProduct() {
        JFrame purchaseFrame = new JFrame("Compra");
        purchaseFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        purchaseFrame.setVisible(true);
        purchaseFrame.setResizable(false);
        purchaseFrame.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);

        // Crear el modelo de tabla con las columnas "Nombre", "Precio" y "Unidades"
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Nombre");
        tableModel.addColumn("Precio");
        tableModel.addColumn("Unidades");

        ArrayList<CartItem> purchaseList = ((unitaryPurchase) ? auxCartList : cartList);
        // Agregar los datos de los productos al modelo de tabla unitaria o del carrito
        for (CartItem product : purchaseList) {
            Object[] rowData = {product.getProductName(), product.getPrice(), product.getUnits()};
            tableModel.addRow(rowData);
        }

        // Crear la tabla con el modelo de tabla personalizado
        JTable table = new JTable(tableModel);

        // Agregar la tabla a un JScrollPane para permitir el desplazamiento
        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setPreferredSize(new Dimension(400, 150));

        // Agregar el JScrollPane al panel principal
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.WEST;
        panel.add(tableScrollPane, constraints);

        // Etiqueta y campo de texto para el número de documento
        JLabel docLabel = new JLabel("Número de Documento:");
        JTextField docField = new JTextField(20);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        panel.add(docLabel, constraints);

        constraints.gridx = 1;
        panel.add(docField, constraints);

        // Etiqueta y campo de texto para el nombre
        JLabel nameLabel = new JLabel("Nombre:");
        JTextField nameField = new JTextField(20);

        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(nameLabel, constraints);

        constraints.gridx = 1;
        panel.add(nameField, constraints);

        // Etiqueta y campo de texto para el apellido
        JLabel lastNameLabel = new JLabel("Apellido:");
        JTextField lastNameField = new JTextField(20);

        constraints.gridx = 0;
        constraints.gridy = 3;
        panel.add(lastNameLabel, constraints);

        constraints.gridx = 1;
        panel.add(lastNameField, constraints);

        // Etiqueta y campo de texto para la dirección
        JLabel addressLabel = new JLabel("Dirección:");
        JTextField addressField = new JTextField(20);

        constraints.gridx = 0;
        constraints.gridy = 4;
        panel.add(addressLabel, constraints);

        constraints.gridx = 1;
        panel.add(addressField, constraints);

        //// Etiqueta y campo de texto para el email
        JLabel emailLabel = new JLabel("Correo electronico:");
        JTextField emailField = new JTextField(20);

        constraints.gridx = 0;
        constraints.gridy = 5;
        panel.add(emailLabel, constraints);

        constraints.gridx = 1;
        panel.add(emailField, constraints);

        // Botones para efectuar la compra y cancelar
        JButton purchaseButton = new JButton("Efectuar Compra");
        JButton cancelButton = new JButton("Cancelar");

        purchaseButton.addActionListener((ActionEvent e) -> {
            int docNum = Integer.parseInt(docField.getText());
            if (!presenter.clientExists(docNum)) {
                presenter.insertCliente(docNum, nameField.getText(), lastNameField.getText(), emailField.getText(), addressField.getText());
            }
            presenter.insertPedido(docNum);
        });

        cancelButton.addActionListener((ActionEvent e) -> {
            purchaseFrame.dispose();
        });

        constraints.gridx = 0;
        constraints.gridy = 6;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(purchaseButton, constraints);

        constraints.gridx = 0;
        constraints.gridy = 7;
        panel.add(cancelButton, constraints);

        purchaseFrame.getContentPane().add(panel);
        purchaseFrame.pack();
    }

    public boolean productExists(String name) {
        for (CartItem cartProduct : cartList) {
            if (cartProduct.getProductName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public void refreshFrame(JFrame frame, JPanel newPanel) {
        frame.getContentPane().removeAll(); // Elimina los componentes existentes del JFrame
        frame.getContentPane().add(newPanel); // Agrega el nuevo panel al JFrame
        frame.revalidate(); // Actualiza el diseño del JFrame
        frame.repaint(); // Vuelve a pintar el JFrame
    }

    public void showSuccessMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", ERROR);
    }

}
