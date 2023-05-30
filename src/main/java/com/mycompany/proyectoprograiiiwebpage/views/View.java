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

    public static final String MAIN_MENU_TITTLE = "Acciones de administrador";
    public static final String MAIN_WINDOW_TITTLE = "Bienvendo al gestor de base de datos";
    public static final String MAIN_WINDOW_SUB_TITTLE = "Seleccione una opción:";
    public static final String[] TABLES = {"CLIENTE", "DETALLE PEDIDO", "PEDIDO", "PRODUCTO"};

    public static final Dimension SCREEN_DIMENSION = Toolkit.getDefaultToolkit().getScreenSize();
    public static final String SUCCESSFUL_PURCHASE_MESSAGE = "Gracias por su compra";
    public static final String SUCCESSFUL_CLIENT_REGISTER_MESSAGE = "Cliente registrado exitosamente";
    public static final String SUCCESSFUL_ORDER_REGISTER_MESSAGE = "Pedido registrado exitosamente";
    public static final String SUCCESSFUL_ORDER_DETAIL_REGISTER_MESSAGE = "Detalle de pedido registrado exitosamente";
    public static final String ERROR_PURCHASE_MESSAGE = "Error al realizar la compra";
    public static final String ERROR_CLIENT_REGISTER_MESSAGE = "Error al registrar el cliente";
    public static final String ERROR_ORDER_REGISTER_MESSAGE = "Error al registrar el pedido";
    public static final String ERROR_ORDER_DETAIL_REGISTER_MESSAGE = "Error al registrar el detalle del pedido";

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
            CartItem item = new CartItem(Integer.parseInt(product[0]), product[1], product[2], 1);

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

            addButton
                    .addActionListener((ActionEvent e) -> {
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

        JButton loginButton = new JButton("Acciones de administrador");
        loginButton.addActionListener((ActionEvent e) -> {
            JPanel panel = new JPanel();
            JTextField usernameField = new JTextField(20);
            JPasswordField passwordField = new JPasswordField(20);
            JLabel usernameLabel = new JLabel("Nombre de usuario:");
            JLabel passwordLabel = new JLabel("Clave:");

            panel.add(usernameLabel);
            panel.add(usernameField);
            panel.add(passwordLabel);
            panel.add(passwordField);

            int option = JOptionPane.showOptionDialog(null, panel, "MODO GOD",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);

            if (option == JOptionPane.OK_OPTION) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                if (username.equals(presenter.getUserAdmin()) && password.equals(presenter.getUserPassword())) {
                    showCRUDdataBase();
                }
            }
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

    public void showCRUDdataBase() {
        JFrame frameCRUD = new JFrame();
        frameCRUD.setTitle(MAIN_MENU_TITTLE);
        frameCRUD.setMinimumSize(new Dimension(500, 400));
        frameCRUD.setResizable(false);
        frameCRUD.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameCRUD.setLayout(new FlowLayout());
        frameCRUD.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 1, 1, 10));

        JLabel windowTitle = new JLabel("");
        windowTitle.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));

        JLabel windowSubTitle = new JLabel(MAIN_WINDOW_SUB_TITTLE);
        windowSubTitle.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));

        JButton registerButton = new JButton("Registrar");
        registerButton.addActionListener((ActionEvent e) -> {
            showGUI_INSERT();
        });

        JButton dataButton = new JButton("Todos los registros");
        dataButton.addActionListener((ActionEvent e) -> {
            //showGUI_SELECT_ALL();
        });

        JButton deleteButton = new JButton("Eliminar registros");
        deleteButton.addActionListener((ActionEvent e) -> {
            //showGUI_DELETE();
        });

        JButton updateButton = new JButton("Actualizar registros");
        updateButton.addActionListener((ActionEvent e) -> {
            //showGUI_UPDATE();
        });

        JButton searchButton = new JButton("Consultar registros");
        searchButton.addActionListener((ActionEvent e) -> {
            //searchFrame();
        });

        panel.add(windowTitle);
        panel.add(windowSubTitle);
        panel.add(registerButton);
        panel.add(dataButton);
        panel.add(deleteButton);
        panel.add(updateButton);
        panel.add(searchButton);

        frameCRUD.getContentPane().add(panel);

        frameCRUD.setVisible(true);
    }

    public void showGUI_INSERT() {
        JFrame insertFrame = new JFrame("Agregar Registro");
        insertFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        insertFrame.setSize(400, 200);
        insertFrame.setLayout(new FlowLayout());
        insertFrame.setLocationRelativeTo(this);

        JPanel containerPanel = new JPanel();
        insertFrame.add(containerPanel);

        JComboBox comboTables = new JComboBox<>(TABLES);
        insertFrame.add(comboTables);
        comboTables.addActionListener((ActionEvent e) -> {
            String optionSelected = comboTables.getSelectedItem().toString();
            containerPanel.removeAll();
            if (optionSelected.equals("CLIENTE")) {
                JPanel panelClient = createPanelClient();
                containerPanel.add(panelClient);
            } else if (optionSelected.equals("PRODUCTO")) {
                JPanel panelProduct = createPanelProduct();
                containerPanel.add(panelProduct);
            }
            insertFrame.revalidate();
        });
        insertFrame.setVisible(true);
    }

    private JPanel createPanelClient() {
        JPanel panelClient = new JPanel();
        JLabel idClienteLabel = new JLabel("ID del cliente:");
        JLabel nombreLabel = new JLabel("Nombre:");
        JLabel apellidoLabel = new JLabel("Apellido:");
        JLabel correoLabel = new JLabel("Correo electrónico:");
        JLabel direccionLabel = new JLabel("Dirección:");

        JTextField idClienteField = new JTextField(20);
        JTextField nombreField = new JTextField(20);
        JTextField apellidoField = new JTextField(20);
        JTextField correoField = new JTextField(20);
        JTextField direccionField = new JTextField(20);

        JButton createButton = new JButton("Crear");
        createButton.addActionListener((ActionEvent e) -> {

            int idCliente = getInt(idClienteField.getText());
            String nombre = nombreField.getText();
            String apellido = apellidoField.getText();
            String correo = correoField.getText();
            String direccion = direccionField.getText();
            if (idCliente != -1) {
                presenter.insertCliente(idCliente, nombre, apellido, correo, direccion);
            } else {
                if (idCliente == -1) {
                    JOptionPane.showMessageDialog(panelClient, "Error: Ingrese un número entero válido para el ID");
                } else {
                    JOptionPane.showMessageDialog(panelClient, "Error: Ingrese un número entero válido para la edad");
                }
                showGUI_INSERT();
            }
            idClienteField.setText("");
            nombreField.setText("");
            apellidoField.setText("");
            correoField.setText("");
            direccionField.setText("");
        });
        panelClient.setLayout(new BoxLayout(panelClient, BoxLayout.PAGE_AXIS));
        panelClient.add(idClienteLabel);
        panelClient.add(idClienteField);
        panelClient.add(nombreLabel);
        panelClient.add(nombreField);
        panelClient.add(apellidoLabel);
        panelClient.add(apellidoField);
        panelClient.add(correoLabel);
        panelClient.add(correoField);
        panelClient.add(direccionLabel);
        panelClient.add(direccionField);
        panelClient.add(createButton);
        return panelClient;
    }

    private JPanel createPanelProduct() {
        JPanel panelProduct = new JPanel();
        JLabel idProductoLabel = new JLabel("ID producto:");
        JLabel nombreLabel = new JLabel("Nombre:");
        JLabel precioLabel = new JLabel("Precio:");
        JLabel descriptionLabel = new JLabel("Descripcion:");

        JTextField idProductoField = new JTextField(20);
        JTextField nombreField = new JTextField(20);
        JTextField precioField = new JTextField(20);
        JTextField descripcionField = new JTextField(20);

        JButton createButton = new JButton("Crear");
        createButton.addActionListener((ActionEvent e) -> {

            int idProducto = getInt(idProductoField.getText());
            String nombre = nombreField.getText();
            double precio = Double.parseDouble(precioField.getText());
            String descripcion = descripcionField.getText();
            if (idProducto != -1) {
                presenter.insertProducto(idProducto, nombre, precio, descripcion);
            } else {
                if (idProducto == -1) {
                    JOptionPane.showMessageDialog(panelProduct, "Error: Ingrese un número entero válido para el ID");
                } else {
                    JOptionPane.showMessageDialog(panelProduct, "Error: Ingrese un número entero válido para la edad");
                }
                showGUI_INSERT();
            }

            idProductoField.setText("");
            nombreField.setText("");
            precioField.setText("");
            descripcionField.setText("");
        });
        panelProduct.setLayout(new BoxLayout(panelProduct, BoxLayout.PAGE_AXIS));
        panelProduct.add(idProductoLabel);
        panelProduct.add(idProductoField);
        panelProduct.add(nombreLabel);
        panelProduct.add(nombreField);
        panelProduct.add(precioLabel);
        panelProduct.add(precioField);
        panelProduct.add(descriptionLabel);
        panelProduct.add(descripcionField);
        panelProduct.add(createButton);
        return panelProduct;
    }

    private void showGUI_SELECT_ALL() {
        JFrame selectFrame = new JFrame("Mostrar Registros");
        selectFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        selectFrame.setSize(400, 300);
        selectFrame.setLayout(new BorderLayout());
        selectFrame.setLocationRelativeTo(this);

        DefaultTableModel tableModel = new DefaultTableModel();
        JTable table = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);
        selectFrame.add(scrollPane, BorderLayout.CENTER);

        // Llenar la tabla con los datos de la base de datos
        //presenter.fillData(tableModel);
        selectFrame.setVisible(true);
    }

    public void showGUI_DELETE() {
        JFrame deleteFrame = new JFrame("Eliminación de Registro");

        deleteFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        deleteFrame.setSize(300, 150);
        deleteFrame.setLayout(new FlowLayout());
        deleteFrame.setLocationRelativeTo(null);

        JTextField idField = new JTextField(10);;
        JButton button = new JButton("Eliminar");;

        button.addActionListener((e) -> {
            int id = getInt(idField.getText());
            if (id != -1) {
                //presenter.deleteData(id);
                deleteFrame.dispose();
            } else {
                deleteFrame.dispose();
                JOptionPane.showMessageDialog(deleteFrame, "Error: Ingrese un número entero válido para el ID");
                showGUI_DELETE();
            }
        });

        deleteFrame.add(new JLabel("ID del registro a eliminar:"));
        deleteFrame.add(idField);
        deleteFrame.add(button);

        deleteFrame.setVisible(true);
    }

    public void showGUI_UPDATE() {
        JFrame updateFrame = new JFrame("Actualizar Persona");
        updateFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        updateFrame.setSize(400, 200);
        updateFrame.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 10));

        JLabel idLabel = new JLabel("ID:");
        JTextField idField = new JTextField(10);

        JLabel attributeLabel = new JLabel("Atributo:");

        JComboBox attributeComboBox = new JComboBox<>();//"FALTA AÑADIR"

        JLabel valueLabel = new JLabel("Nuevo valor:");
        JTextField valueField = new JTextField(10);

        JButton updateButton = new JButton("Actualizar");
        updateButton.addActionListener((ActionEvent e) -> {

            int id = getInt(idField.getText());
            String value = valueField.getText();
            int valueInt = getInt(value);
            String attribute = attributeComboBox.getSelectedItem().toString();

            if (id != -1) {
                if (attribute.equals("EDAD") && valueInt == -1) {
                    JOptionPane.showMessageDialog(updateFrame, "Error: Ingrese un número entero válido para la edad");
                } else {
                    //presenter.updatePerson(id, attribute, value);
                    updateFrame.dispose();
                }
            } else {
                updateFrame.dispose();
                if (id == -1) {
                    JOptionPane.showMessageDialog(updateFrame, "Error: Ingrese un número entero válido para el ID");
                }
                showGUI_UPDATE();
            }

        });

        panel.add(idLabel);
        panel.add(idField);
        panel.add(attributeLabel);
        panel.add(attributeComboBox);
        panel.add(valueLabel);
        panel.add(valueField);
        panel.add(updateButton);

        updateFrame.add(panel);
        updateFrame.setVisible(true);

    }

    public void searchFrame() {
        JFrame frame = new JFrame("Búsqueda");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(300, 150);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridLayout(2, 2));

        JLabel attributeLabel = new JLabel("Atributo:");
        JLabel valueLabel = new JLabel("Valor:");

        JComboBox attributeComboBox = new JComboBox<>();//"FALTA AÑADIR"
        attributeComboBox.addItem("ID");

        JTextField valueTextField = new JTextField();

        panel.add(attributeLabel);
        panel.add(attributeComboBox);
        panel.add(valueLabel);
        panel.add(valueTextField);

        JButton searchButton = new JButton("Buscar");
        searchButton.addActionListener((ActionEvent e) -> {
            String value = valueTextField.getText();
            int valueInt = getInt(value);
            String attribute = attributeComboBox.getSelectedItem().toString();

            if (attribute.equals("EDAD") && valueInt == -1) {
                JOptionPane.showMessageDialog(frame, "Error: Ingrese un número entero válido para la edad");
            } else if (attribute.equals("ID") && valueInt == -1) {
                JOptionPane.showMessageDialog(frame, "Error: Ingrese un número entero válido para el ID");
            } else {
                showGUI_SELECT_ATTRIBUTE(attribute, value);
                frame.dispose();
            }
        });
        /*frame.dispose();
                if (id == -1) {
                    JOptionPane.showMessageDialog(frame, "Error: Ingrese un número entero válido para el ID");
                }
                showGUI_UPDATE();*/

        ///
        //String attribute = attributeComboBox.getSelectedItem().toString();
        //String value = valueTextField.getText();
        //showGUI_SELECT_ATTRIBUTE(attribute, value);
        //frame.dispose();
        frame.add(panel, BorderLayout.CENTER);

        frame.add(searchButton, BorderLayout.SOUTH);

        frame.setVisible(
                true);
    }

    private void showGUI_SELECT_ATTRIBUTE(String attribute, String value) {
        JFrame selectFrame = new JFrame("Mostrar Registros");
        selectFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        selectFrame.setSize(400, 300);
        selectFrame.setLayout(new BorderLayout());
        selectFrame.setLocationRelativeTo(this);

        DefaultTableModel tableModel = new DefaultTableModel();
        JTable table = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);
        selectFrame.add(scrollPane, BorderLayout.CENTER);

        //presenter.fillDataSelect(tableModel, attribute, value);
        selectFrame.setVisible(true);
    }

    public int getInt(String numText) {
        try {
            return Integer.parseInt(numText);
        } catch (NumberFormatException e) {
            return -1;
        }
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
            if (!presenter.idExists(docNum)) {
                presenter.insertCliente(docNum, nameField.getText(), lastNameField.getText(), emailField.getText(), addressField.getText());
            }
            int id_pedido = Presenter.getRandomNumber(1000, 9999);
            if (!presenter.idExists(docNum)) {
                presenter.insertCliente(docNum, nameField.getText(), lastNameField.getText(), emailField.getText(), addressField.getText());
            }
            presenter.insertPedido(id_pedido, docNum);
            for (CartItem item : purchaseList) {
                presenter.insert_detalle_pedido(id_pedido, item.getId(), item.getUnits());
            }
            purchaseFrame.dispose();

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
