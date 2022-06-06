package com.pruebaSpring.tienda.models;

import javax.persistence.*;

@Entity
@Table(name = "Promocion")
public class PromocionModel {

    // Atributos
    @Id
    @Column(nullable = true)
    private String nombre;

    private double descuento;

    // Getters y Setters
    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getDescuento() {
        return this.descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

}
