/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectoprograiiiwebpage.views;

/**
 *
 * @author bgdie
 */
import com.mycompany.proyectoprograiiiwebpage.presenters.Presenter;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;

public class MainMenuView extends JFrame {

    public static final String MAIN_MENU_TITTLE = "Menú Principal";
    public static final String MAIN_WINDOW_TITTLE = "Bienvendo al gestor de base de datos";
    public static final String MAIN_WINDOW_SUB_TITTLE = "Seleccione una opción:";
    public static final String REGISTER_DELETED_SUCCESSFUL_MESSAGE = "Registro eliminado correctamente";
    public static final String REGISTER_UPDATED_SUCCESSFUL_MESSAGE = "Registro actualizado correctamente";
    public static final String ID_ERROR_MESSAGE = "No existe una persona con este ID";
    public static final String EMPTY_DB_MESSAGE = "No hay datos registrados";
    public static final String[] ATTRIBUTES = {"NOMBRE", "APELLIDO", "EDAD"};

    public Presenter presenter;

    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    public void init() {
        this.setTitle(MAIN_MENU_TITTLE);
        this.setMinimumSize(new Dimension(500, 400));
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout());
        this.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 1, 1, 10));

        JLabel windowTitle = new JLabel(MAIN_WINDOW_TITTLE);
        windowTitle.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));

        JLabel windowSubTitle = new JLabel(MAIN_WINDOW_SUB_TITTLE);
        windowSubTitle.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));

        JButton registerButton = new JButton("Registrar persona");
        registerButton.addActionListener((ActionEvent e) -> {
            showGUI_INSERT();
        });

        JButton dataButton = new JButton("Todos los registros");
        dataButton.addActionListener((ActionEvent e) -> {
            showGUI_SELECT_ALL();
        });

        JButton deleteButton = new JButton("Eliminar persona");
        deleteButton.addActionListener((ActionEvent e) -> {
            showGUI_DELETE();
        });

        JButton updateButton = new JButton("Actualizar datos");
        updateButton.addActionListener((ActionEvent e) -> {
            showGUI_UPDATE();
        });

        JButton searchButton = new JButton("Consultar datos");
        searchButton.addActionListener((ActionEvent e) -> {
            searchFrame();
        });

        // Agregar botones al panel
        panel.add(windowTitle);
        panel.add(windowSubTitle);
        panel.add(registerButton);
        panel.add(dataButton);
        panel.add(deleteButton);
        panel.add(updateButton);
        panel.add(searchButton);

        // Agregar el panel a la ventana
        this.getContentPane().add(panel);

        // Mostrar la ventana
        this.setVisible(true);
    }

    public void showGUI_INSERT() {
        JFrame insertFrame = new JFrame("Agregar Persona");
        insertFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        insertFrame.setSize(400, 200);
        insertFrame.setLayout(new FlowLayout());
        insertFrame.setLocationRelativeTo(this);

        JLabel idLabel = new JLabel("Id:");
        JTextField idTextField = new JTextField(5);

        JLabel nameLabel = new JLabel("Nombres:");
        JTextField nameTextField = new JTextField(20);

        JLabel lastnameLabel = new JLabel("Apellidos:");
        JTextField lastnameTextField = new JTextField(20);

        JLabel ageLabel = new JLabel("Edad:");
        JTextField ageTextField = new JTextField(5);

        JButton createButton = new JButton("Crear");
        createButton.addActionListener((ActionEvent e) -> {
            int id = getInt(idTextField.getText());
            int age = getInt(ageTextField.getText());

            if (id != -1 && age != -1) {
                String name = nameTextField.getText();
                String lastname = lastnameTextField.getText();
                presenter.saveData(id, name, lastname, age);
                insertFrame.dispose();
            } else {
                insertFrame.dispose();
                if (id == -1) {
                    JOptionPane.showMessageDialog(insertFrame, "Error: Ingrese un número entero válido para el ID");
                } else {
                    JOptionPane.showMessageDialog(insertFrame, "Error: Ingrese un número entero válido para la edad");
                }
                showGUI_INSERT();
            }
        });

        insertFrame.add(idLabel);

        insertFrame.add(idTextField);

        insertFrame.add(nameLabel);

        insertFrame.add(nameTextField);

        insertFrame.add(lastnameLabel);

        insertFrame.add(lastnameTextField);

        insertFrame.add(ageLabel);

        insertFrame.add(ageTextField);

        insertFrame.add(createButton);

        insertFrame.setVisible(
                true);
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
        presenter.fillData(tableModel);

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
                presenter.deleteData(id);
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

        JComboBox attributeComboBox = new JComboBox<>(ATTRIBUTES);

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
                    presenter.updatePerson(id, attribute, value);
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

        JComboBox attributeComboBox = new JComboBox<>(ATTRIBUTES);
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
            }else{
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

        presenter.fillDataSelect(tableModel, attribute, value);

        selectFrame.setVisible(true);
    }

    public int getInt(String numText) {
        try {
            return Integer.parseInt(numText);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public void showSuccessDialog(String successMessage) {
        JOptionPane.showMessageDialog(this, successMessage);
    }

    public void showErrorDialog(String errorMessage) {
        JOptionPane.showMessageDialog(this, errorMessage, "ID Error", JOptionPane.ERROR_MESSAGE);
    }

}
