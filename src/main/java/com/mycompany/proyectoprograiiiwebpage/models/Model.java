/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectoprograiiiwebpage.models;

/**
 *
 * @author bgdie
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Model {

    private static final String URL = "jdbc:mysql://localhost:3306/pruebajpa";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public Connection connecting() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public boolean insertPerson(int id, String name, String lastname, int age) {
        String query = "INSERT INTO persona (ID, NOMBRE, APELLIDO, EDAD) VALUES (?, ?, ?, ?)";
        try (Connection connection = connecting(); PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.setString(2, name);
            statement.setString(3, lastname);
            statement.setInt(4, age);

            int rowsInserted = statement.executeUpdate();
            connection.close();
            return rowsInserted > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }

    }

    public boolean fillTable(DefaultTableModel tableModel) {
        try (Connection connection = connecting(); Statement statement = connection.createStatement()) {
            String query = "SELECT * FROM persona";
            ResultSet resultSet = statement.executeQuery(query);

            int columnCount = resultSet.getMetaData().getColumnCount();
            int dataCount = 0;

            for (int i = 1; i <= columnCount; i++) {
                tableModel.addColumn(resultSet.getMetaData().getColumnLabel(i));
            }

            while (resultSet.next()) {
                dataCount++;
                Object[] row = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    row[i - 1] = resultSet.getObject(i);
                }
                tableModel.addRow(row);
            }

            while (resultSet.next()) {

                int id = resultSet.getInt("ID");
                String nombre = resultSet.getString("NOMBRE");
                String apellido = resultSet.getString("APELLIDO");
                int edad = resultSet.getInt("EDAD");

                tableModel.addRow(new Object[]{id, nombre, apellido, edad});
            }
            connection.close();
            resultSet.close();
            return dataCount > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean deleteData(int id) {
        String query = "DELETE FROM persona WHERE ID = ?";
        try (Connection connection = connecting()) {
            PreparedStatement statement = connection.prepareStatement(query);

            // Valor para el ID de la cláusula WHERE
            statement.setInt(1, id);

            int rowsAffected = statement.executeUpdate();
            statement.close();
            connection.close();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean updatePerson(String attribute, String value, int id) {
        String query = "UPDATE persona SET " + attribute + " = ? WHERE ID = ?";
        try (Connection connection = connecting()) {
            PreparedStatement statement = connection.prepareStatement(query);

            if (value.equalsIgnoreCase("EDAD")) {
                statement.setInt(1, Integer.parseInt(value));
            } else {
                statement.setString(1, value);
            }

            statement.setInt(2, id);

            int rowsAffected = statement.executeUpdate();

            connection.close();
            statement.close();
            return rowsAffected > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean fillTableSelect(DefaultTableModel tableModel, String attribute, String value) {
        try (Connection connection = connecting()) {
            String query = "SELECT * FROM persona WHERE " + attribute + " LIKE ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, value);
            ResultSet resultSet = statement.executeQuery();

            int columnCount = resultSet.getMetaData().getColumnCount();
            int dataCount = 0;

            for (int i = 1; i <= columnCount; i++) {
                tableModel.addColumn(resultSet.getMetaData().getColumnLabel(i));
            }

            while (resultSet.next()) {
                dataCount++;
                Object[] row = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    row[i - 1] = resultSet.getObject(i);
                }
                tableModel.addRow(row);
            }

            while (resultSet.next()) {

                int id = resultSet.getInt("ID");
                String nombre = resultSet.getString("NOMBRE");
                String apellido = resultSet.getString("APELLIDO");
                int edad = resultSet.getInt("EDAD");

                tableModel.addRow(new Object[]{id, nombre, apellido, edad});
            }
            connection.close();
            resultSet.close();
            statement.close();
            return dataCount > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

}
