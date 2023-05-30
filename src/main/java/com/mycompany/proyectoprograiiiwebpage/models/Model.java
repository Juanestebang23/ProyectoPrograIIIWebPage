/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectoprograiiiwebpage.models;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class Model {

    public static final String INSERT_CLIENTE_SQL_STATEMENT = "INSERT INTO cliente (id_cliente, nombres, apellidos, correo_electronico, direccion) VALUES (?, ?, ?, ?, ?)";
    public static final String INSERT_PRODUCTO_SQL_STATEMENT = "INSERT INTO producto (id_producto, nombre_producto, precio, descripcion) VALUES (?, ?, ?, ?)";
    public static final String INSERT_PEDIDO_SQL_STATEMENT = "INSERT INTO pedido (id_pedido, id_cliente, fecha) VALUES (?, ?, ?)";
    public static final String INSERT_DETALLE_PEDIDO_SQL_STATEMENT = "INSERT INTO detalle_pedido (id_pedido, id_producto, cantidad) VALUES (?, ?, ?)";
    public static final String SELECT_CLIENT_COUNT_SQL_STATEMENT = "SELECT count(*) FROM cliente WHERE id_cliente = ?";
    public static final String SELECT_ORDER_DETAIL_COUNT_SQL_STATEMENT = "SELECT count(*) FROM detalle_pedido WHERE id_detalle_pedido = ?";

    public boolean registerClient(int id, String name, String lastName, String email, String address) {
        try (Connection conn = MyConnection.getConnection()) {

            PreparedStatement pstmt = conn.prepareStatement(INSERT_CLIENTE_SQL_STATEMENT);

            pstmt.setInt(1, id);
            pstmt.setString(2, name);
            pstmt.setString(3, lastName);
            pstmt.setString(4, email);
            pstmt.setString(5, address);

            int affectedRow = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
            return affectedRow > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean registerProducto(int id, String name, double price, String description) {
        try (Connection conn = MyConnection.getConnection()) {

            PreparedStatement pstmt = conn.prepareStatement(INSERT_PRODUCTO_SQL_STATEMENT);

            pstmt.setInt(1, id);
            pstmt.setString(2, name);
            pstmt.setDouble(3, price);
            pstmt.setString(4, description);

            int affectedRow = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
            return affectedRow > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean registerPedido(int id, int id_cliente, LocalDate fecha) {
        try (Connection conn = MyConnection.getConnection()) {

            PreparedStatement pstmt = conn.prepareStatement(INSERT_PEDIDO_SQL_STATEMENT);

            pstmt.setInt(1, id);
            pstmt.setInt(2, id_cliente);
            pstmt.setDate(3, Date.valueOf(fecha));

            int affectedRow = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
            return affectedRow > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String[][] selectAllProducts() {
        try (Connection conn = MyConnection.getConnection()) {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM producto"); // Obtener metadatos del resultado
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Crear un vector de String[] para almacenar los resultados
            // El tamaño del vector será el número de filas del resultado
            int rowCount = getRowCount();
            String[][] result = new String[rowCount][columnCount];

            // Recorrer el resultado y guardar los datos en el vector
            int rowIndex = 0;
            while (resultSet.next()) {
                String[] fila = new String[columnCount];
                for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                    fila[columnIndex - 1] = resultSet.getString(columnIndex);
                }
                result[rowIndex] = fila;
                rowIndex++;
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean insert_detalle_pedido(int id_pedido, int id_producto, int cantidad){
        try (Connection conn = MyConnection.getConnection()) {

            PreparedStatement pstmt = conn.prepareStatement(INSERT_DETALLE_PEDIDO_SQL_STATEMENT);

            pstmt.setInt(1, id_pedido);
            pstmt.setInt(2, id_producto);
            pstmt.setInt(3, cantidad);

            int affectedRow = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
            return affectedRow > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean idExists(int id){
        try (Connection conn = MyConnection.getConnection()) {

            PreparedStatement pstmt = conn.prepareStatement(SELECT_CLIENT_COUNT_SQL_STATEMENT);
            pstmt.setInt(1, id);
            
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
    
    public int getRowCount() throws SQLException {
        int count = 0;
        try (Connection connection = MyConnection.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT count(*) AS total FROM producto");
            if (resultSet.next()) {
               count = resultSet.getInt("total");
            }
            return count;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }

    }
    
}
