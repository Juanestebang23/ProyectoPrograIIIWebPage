
package com.mycompany.proyectoprograiiiwebpage.presenter;

import com.mycompany.proyectoprograiiiwebpage.models.Model;
import com.mycompany.proyectoprograiiiwebpage.views.View;
import java.time.LocalDate;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

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
            view.showSuccessMessage(View.SUCCESSFUL_CLIENT_REGISTER_MESSAGE);
        } else {
            view.showErrorMessage(View.ERROR_PURCHASE_MESSAGE);
        }
    }

    public void insertProducto(int id, String name, double price, String description) {
        boolean success = model.registerProducto(id, name, price, description);
        if (success) {
            view.showSuccessMessage(View.SUCCESSFUL_PRODUCT_REGISTER_MESSAGE);
        } else {
            view.showErrorMessage(View.ERROR_CLIENT_REGISTER_MESSAGE);
        }
    }
    
    public void deleteDataClient(int id) {
        boolean success = model.deleteDataClient(id);
        if (success) {
            view.showSuccessMessage(View.REGISTER_DELETED_SUCCESSFUL_MESSAGE);
        } else {
            view.showErrorMessage(View.ID_ERROR_MESSAGE);
            view.showGUI_DELETE();
        }
    }
    public void deleteDataProduct(int id) {
        boolean success = model.deleteDataProduct(id);
        if (success) {
            view.showSuccessMessage(View.REGISTER_DELETED_SUCCESSFUL_MESSAGE);
        } else {
            view.showErrorMessage(View.ID_ERROR_MESSAGE);
            view.showGUI_DELETE();
        }
    }

    public void insertPedido(int id_pedido, int id_cliente) {
        LocalDate fechaPedido = getCurrentlyDate();
        boolean success = model.registerPedido(id_pedido, id_cliente, fechaPedido);
        if (success) {
            view.showSuccessMessage(View.SUCCESSFUL_ORDER_REGISTER_MESSAGE);
        } else {
            view.showErrorMessage(View.ERROR_ORDER_REGISTER_MESSAGE);
        }
    }
    
    public void insert_detalle_pedido(int id_pedido, int id_producto, int cantidad) {
        boolean succcess = model.insert_detalle_pedido(id_pedido, id_producto, cantidad);
        if (succcess) {
            view.showSuccessMessage(View.SUCCESSFUL_ORDER_DETAIL_REGISTER_MESSAGE);
        } else {
            view.showErrorMessage(View.ERROR_ORDER_DETAIL_REGISTER_MESSAGE);
        }
    }
    
    public void fillDataClientes(DefaultTableModel tableModel) {
        boolean success = model.fillTableClientes(tableModel);
        if (!success) {
            view.showSuccessMessage(View.EMPTY_DB_MESSAGE);
        }
    }
    public void fillDataProductos(DefaultTableModel tableModel) {
        boolean success = model.fillTableProducts(tableModel);
        if (!success) {
            view.showSuccessMessage(View.EMPTY_DB_MESSAGE);
        }
    }
    public void fillDataDetallePedidos(DefaultTableModel tableModel) {
        boolean success = model.fillTableDetallePedido(tableModel);
        if (!success) {
            view.showSuccessMessage(View.EMPTY_DB_MESSAGE);
        }
    }
    public void fillDataPedidos(DefaultTableModel tableModel) {
        boolean success = model.fillTablePedido(tableModel);
        if (!success) {
            view.showSuccessMessage(View.EMPTY_DB_MESSAGE);
        }
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

    public boolean idExists(int id) {
        return model.idExists(id);
    }

    public static int getRandomNumber(int min, int max) {
        return (int) (Math.random() * (max - min + 1)) + min;
    }

    public LocalDate getCurrentlyDate() {
        return LocalDate.now();
    }

    public String getUserAdmin() {
        return model.getUserAdmin();
    }

    public String getUserPassword() {
        return model.getUserPassword();
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
