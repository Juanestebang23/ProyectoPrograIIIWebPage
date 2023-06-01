package com.mycompany.proyectoprograiiiwebpage;

import com.github.javafaker.Faker;
import com.mycompany.proyectoprograiiiwebpage.models.Model;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;

public class ProyectoPrograIIIWebPage {

    public static void main(String[] args) {
        Model model = new Model();
        Faker faker = new Faker();// Libreria github para obtener datos falsos

        //Llenar la tabla con productos
        /*
        int cantidadProductos = 10; // El número de productos que se quiere agregar
        for (int i = 0; i < cantidadProductos; i++) {
            int id = (int) (Math.random()*10000);
            String name = faker.commerce().productName();
            double price = faker.number().randomDouble(2, 10000, 500000);
            String description = faker.shakespeare().hamletQuote() + faker.shakespeare().romeoAndJulietQuote();
            
            System.out.println("id: "+ id);
            System.out.println("name: "+ name);
            System.out.println("price: "+ price);
            System.out.println("description: "+ description);
            
            boolean success = model.registerProducto(id, name, price, description);

            if (success) {
                System.out.println("Agregado");

            } else {
                System.out.println("No Agregado");

            }
            System.out.println("------------------------------------");
        }*/
        //Llenar la tabla clientes
        /*
        for (int i = 0; i < 5; i++) {
            int id = (int) (Math.random()*1000);
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String email = faker.internet().emailAddress();
            String address = faker.address().streetAddress();

            System.out.println("Id: " + id);
            System.out.println("First Name: " + firstName);
            System.out.println("Last Name: " + lastName);
            System.out.println("Dirección: " + address);
            System.out.println("Email: " + email);

            boolean success = model.registerClient(id, email, lastName, email, address);

            if (success) {
                System.out.println("Agregado");

            } else {
                System.out.println("No Agregado");

            }
            System.out.println("------------------------------------");
            
        }*/
        //Llenar la tabla factura
        for (int i = 0; i < 20; i++) {
            int id = (int) faker.number().randomNumber(5, true);
            String fullName = faker.name().firstName() + faker.name().lastName();
            String email = faker.internet().emailAddress();
            String address = faker.address().streetAddress();
            String product = faker.commerce().productName();
            double price = faker.number().randomDouble(2, 10000, 500000);
            int units = faker.number().numberBetween(1, 10);
            java.util.Date date = faker.date().birthday(0, 7);
            LocalDate buyDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            

            System.out.println("Id: " + id);
            System.out.println("Full Name: " + fullName);
            System.out.println("Email: " + email);
            System.out.println("Dirección: " + address);
            System.out.println("Producto: " + product);
            System.out.println("Price: " + price);
            System.out.println("Date: " + buyDate);

            boolean success = model.insert_factura(id, fullName, email, address, product, price, units, buyDate);
            

            if (success) {
                System.out.println("Agregado");

            } else {
                System.out.println("No Agregado");

            }
            System.out.println("------------------------------------");
        }
    }

}
