
package com.mycompany.proyectoprograiiiwebpage.models;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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

    public static final String QUERY_CLIENT = "SELECT * FROM cliente";
    public static final String QUERY_DETAIL_ORDER = "SELECT * FROM detalle_pedido";
    public static final String QUERY_ORDER = "SELECT * FROM pedido";
    public static final String QUERY_PRODUCT = "SELECT * FROM producto";

    public static final String HEADER_ID_CLIENT = "ID CLIENTE";
    public static final String HEADER_NAME_CLIENT = "NOMBRE";
    public static final String HEADER_LASTNAME_CLIENT = "APELLIDO";
    public static final String HEADER_EMAIL_CLIENT = "CORREO ELECTRONICO";
    public static final String HEADER_ADDRESS_CLIENT = "DIRECCION";

    public static final String HEADER_IDORDER_DETAIL_ORDER = "ID PEDIDO";
    public static final String HEADER_IDPRODUCT_DETAIL_ORDER = "ID PRODUCTO";
    public static final String HEADER_QUANTITY_DETAIL_ORDER = "CANTIDAD";

    public static final String HEADER_ID_ORDER = "ID PEDIDO";
    public static final String HEADER_IDCLIENT_ORDER = "ID CLIENTE";
    public static final String HEADER_DATE_ORDER = "FECHA";
    
    public static final String HEADER_ID_PRODUCT = "ID PRODUCTO";
    public static final String HEADER_NAME_PRODUCT = "NOMBRE";
    public static final String HEADER_PRICE_PRODUCT = "PRECIO";
    public static final String HEADER_DESCRIPTION_PRODUCT = "DESCRIPCION";

    public static final String EXTENSION = ".pdf";

    private String adminUser = "ADMIN123";
    private String password = "holass";

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

    ///////////////////////////////////////////////////////////////////////////
    public void createPDFClients(String rutaDestino, String nombreArchivo) {
        try (Connection connection = MyConnection.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(QUERY_CLIENT);
            generateTablePDFClientes(resultSet, rutaDestino, nombreArchivo);
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.err.println("ERROR!!! ---->" + e.getMessage());
        }
    }

    public void generateTablePDFClientes(ResultSet resultSet, String rutaDestino, String nombreArchivo) {
        try {
            Document document = new Document();
            String rutaCompleta = rutaDestino + "/" + nombreArchivo + EXTENSION;
            PdfWriter.getInstance(document, new FileOutputStream(rutaCompleta));
            document.open();

            PdfPTable table = new PdfPTable(5);
            table.setWidths(new float[]{1, 1, 1, 1, 1});
            addTableHeaderClients(table);
            addTableDataClients(table, resultSet);
            document.add(table);

            document.close();

        } catch (DocumentException | FileNotFoundException | SQLException ex) {
            System.err.println("Error!!!" + ex.getMessage());
        }
    }

    private static void addTableHeaderClients(PdfPTable table) {
        PdfPCell header1 = new PdfPCell(new Phrase(HEADER_ID_CLIENT));
        PdfPCell header2 = new PdfPCell(new Phrase(HEADER_NAME_CLIENT));
        PdfPCell header3 = new PdfPCell(new Phrase(HEADER_LASTNAME_CLIENT));
        PdfPCell header4 = new PdfPCell(new Phrase(HEADER_EMAIL_CLIENT));
        PdfPCell header5 = new PdfPCell(new Phrase(HEADER_ADDRESS_CLIENT));

        table.addCell(header1);
        table.addCell(header2);
        table.addCell(header3);
        table.addCell(header4);
        table.addCell(header5);
    }

    private static void addTableDataClients(PdfPTable table, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            String columna1 = resultSet.getString(HEADER_ID_CLIENT);
            String columna2 = resultSet.getString(HEADER_NAME_CLIENT);
            String columna3 = resultSet.getString(HEADER_LASTNAME_CLIENT);
            String columna4 = resultSet.getString(HEADER_EMAIL_CLIENT);
            String columna5 = resultSet.getString(HEADER_ADDRESS_CLIENT);

            table.addCell(columna1);
            table.addCell(columna2);
            table.addCell(columna3);
            table.addCell(columna4);
            table.addCell(columna5);
        }
    }

    public void createPDFDetalle_Pedidos(String rutaDetino, String nombreArchivo) {
        try (Connection connection = MyConnection.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(QUERY_DETAIL_ORDER);
            generateTablePDFDetalle_Pedidos(resultSet, rutaDetino, nombreArchivo);
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.err.println("ERROR!!! ---->" + e.getMessage());
        }
    }

    public void generateTablePDFDetalle_Pedidos(ResultSet resultSet, String rutaDestino, String nombreArchivo) {
        try {
            Document document = new Document();

            String rutaCompleta = rutaDestino + "/" + nombreArchivo + EXTENSION;
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(rutaCompleta));
            document.open();

            PdfPTable table = new PdfPTable(3);
            table.setWidths(new float[]{1, 1, 1});
            addTableHeaderDetalle_Pedidos(table);
            addTableDataDetalle_Pedidos(table, resultSet);
            document.add(table);

            document.close();

        } catch (DocumentException | FileNotFoundException | SQLException ex) {
            System.err.println("Error!!!" + ex.getMessage());
        }
    }

    private static void addTableHeaderDetalle_Pedidos(PdfPTable table) {
        PdfPCell header1 = new PdfPCell(new Phrase(HEADER_IDORDER_DETAIL_ORDER));
        PdfPCell header2 = new PdfPCell(new Phrase(HEADER_IDPRODUCT_DETAIL_ORDER));
        PdfPCell header3 = new PdfPCell(new Phrase(HEADER_QUANTITY_DETAIL_ORDER));

        table.addCell(header1);
        table.addCell(header2);
        table.addCell(header3);
    }

    private static void addTableDataDetalle_Pedidos(PdfPTable table, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            String columna1 = resultSet.getString(HEADER_IDORDER_DETAIL_ORDER);
            String columna2 = resultSet.getString(HEADER_IDPRODUCT_DETAIL_ORDER);
            String columna3 = resultSet.getString(HEADER_QUANTITY_DETAIL_ORDER);

            table.addCell(columna1);
            table.addCell(columna2);
            table.addCell(columna3);
        }
    }

    public void createPDFPedidos(String rutaDetino, String nombreArchivo) {
        try (Connection connection = MyConnection.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(QUERY_ORDER);
            generateTablePDFPedidos(resultSet, rutaDetino, nombreArchivo);
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.err.println("ERROR!!! ---->" + e.getMessage());
        }
    }

    public void generateTablePDFPedidos(ResultSet resultSet, String rutaDestino, String nombreArchivo) {
        try {
            Document document = new Document();

            String rutaCompleta = rutaDestino + "/" + nombreArchivo + EXTENSION;
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(rutaCompleta));
            document.open();

            PdfPTable table = new PdfPTable(3);
            table.setWidths(new float[]{1, 1, 1});
            addTableHeaderPedidos(table);
            addTableDataPedidos(table, resultSet);
            document.add(table);

            document.close();

        } catch (DocumentException | FileNotFoundException | SQLException ex) {
            System.err.println("Error!!!" + ex.getMessage());
        }
    }

    private static void addTableHeaderPedidos(PdfPTable table) {
        PdfPCell header1 = new PdfPCell(new Phrase(HEADER_ID_ORDER));
        PdfPCell header2 = new PdfPCell(new Phrase(HEADER_IDCLIENT_ORDER));
        PdfPCell header3 = new PdfPCell(new Phrase(HEADER_DATE_ORDER));

        table.addCell(header1);
        table.addCell(header2);
        table.addCell(header3);
    }

    private static void addTableDataPedidos(PdfPTable table, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            String columna1 = resultSet.getString(HEADER_ID_ORDER);
            String columna2 = resultSet.getString(HEADER_IDCLIENT_ORDER);
            String columna3 = resultSet.getString(HEADER_DATE_ORDER);

            table.addCell(columna1);
            table.addCell(columna2);
            table.addCell(columna3);
        }
    }

    public void createPDFProducts(String rutaDetino, String nombreArchivo) {
        try (Connection connection = MyConnection.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(QUERY_PRODUCT);
            generateTablePDFProducts(resultSet, rutaDetino, nombreArchivo);
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.err.println("ERROR!!! ---->" + e.getMessage());
        }
    }
    public void generateTablePDFProducts(ResultSet resultSet, String rutaDestino, String nombreArchivo) {
        try {
            Document document = new Document();

            String rutaCompleta = rutaDestino + "/" + nombreArchivo + EXTENSION;
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(rutaCompleta));
            document.open();

            PdfPTable table = new PdfPTable(4);
            table.setWidths(new float[]{1, 1, 1, 1});
            addTableHeaderProducts(table);
            addTableDataProducts(table, resultSet);
            document.add(table);

            document.close();

        } catch (DocumentException | FileNotFoundException | SQLException ex) {
            System.err.println("Error!!!" + ex.getMessage());
        }
    }
    private static void addTableHeaderProducts(PdfPTable table) {
        PdfPCell header1 = new PdfPCell(new Phrase(HEADER_ID_PRODUCT));
        PdfPCell header2 = new PdfPCell(new Phrase(HEADER_NAME_PRODUCT));
        PdfPCell header3 = new PdfPCell(new Phrase(HEADER_PRICE_PRODUCT));
        PdfPCell header4 = new PdfPCell(new Phrase(HEADER_DESCRIPTION_PRODUCT));

        table.addCell(header1);
        table.addCell(header2);
        table.addCell(header3);
        table.addCell(header4);
    }
    private static void addTableDataProducts(PdfPTable table, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            String columna1 = resultSet.getString(HEADER_ID_PRODUCT);
            String columna2 = resultSet.getString(HEADER_NAME_PRODUCT);
            String columna3 = resultSet.getString(HEADER_PRICE_PRODUCT);
            String columna4 = resultSet.getString(HEADER_DESCRIPTION_PRODUCT);

            table.addCell(columna1);
            table.addCell(columna2);
            table.addCell(columna3);
            table.addCell(columna4);
        }
    }
}
