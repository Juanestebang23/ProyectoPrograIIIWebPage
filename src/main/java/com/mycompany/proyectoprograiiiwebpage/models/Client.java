/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectoprograiiiwebpage.models;

public class Client {
    private int idClient;
    private String name;
    private String lastName;
    private String address;
    private String email;
    
    public Client(int idClient, String name, String lastName ,  String email, String address) {
        this.idClient = idClient;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
   
    public String[] getStringAttributes(){
        return new String[]{"id_cliente", "nombres", "apellidos", "correo_electronico", "direccion"};
    }
}    
