
package com.mycompany.proyectoprograiiiwebpage.presenters;

import com.mycompany.proyectoprograiiiwebpage.views.MainMenuView;
import com.mycompany.proyectoprograiiiwebpage.models.Model;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author bgdie
 */
// PersonaPresenter.java
public class Presenter {

    private MainMenuView view;
    private Model model;

    public Presenter(MainMenuView view, Model model) {
        this.view = view;
        this.model = model;
        //setupViewListeners();
    }

    private void run() {
        view.init();
    }

    /*
    private void setupViewListeners() {
        view.addGuardarButtonListener(e -> {
            String nombre = view.getNombreTextFieldValue();
            model.setNombre(nombre);
            guardarData();
        });

        view.addAgregarPersonaButtonListener(e -> {
            GUI_1_INSERT guiInsert = new GUI_1_INSERT(model);
            guiInsert.showGUI();
        });
    }*/
    public void saveData(int id, String name, String lastname, int age) {
        boolean success = model.insertPerson(id, name, lastname, age);
        if (success) {
            JOptionPane.showMessageDialog(view, "Registro creado correctamente");
        } else {
            JOptionPane.showMessageDialog(view, "Error al crear el registro");
            view.showGUI_INSERT();
        }
    }

    public void fillData(DefaultTableModel tableModel) {
        boolean success = model.fillTable(tableModel);
        if (!success) {
            view.showSuccessDialog(MainMenuView.EMPTY_DB_MESSAGE);
        }
    }

    public void deleteData(int id) {
        boolean success = model.deleteData(id);
        if (success) {
            view.showSuccessDialog(MainMenuView.REGISTER_DELETED_SUCCESSFUL_MESSAGE);
        } else {
            view.showErrorDialog(MainMenuView.ID_ERROR_MESSAGE);
            view.showGUI_DELETE();
        }
    }

    public void updatePerson(int id, String attribute, String value) {
        boolean success = model.updatePerson(attribute, value, id);
        if (success) {
            view.showSuccessDialog(MainMenuView.REGISTER_UPDATED_SUCCESSFUL_MESSAGE);
        } else {
            view.showErrorDialog(MainMenuView.ID_ERROR_MESSAGE);
            view.showGUI_UPDATE();
        }
    }
    
    public void fillDataSelect(DefaultTableModel tableModel, String attribute, String value) {
        boolean success = model.fillTableSelect(tableModel, attribute, value);
        if (!success) {
            view.showSuccessDialog(MainMenuView.EMPTY_DB_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Model model = new Model();
            MainMenuView view = new MainMenuView();
            Presenter presenter = new Presenter(view, model);
            view.setPresenter(presenter);
            presenter.run();
        });
    }

}
