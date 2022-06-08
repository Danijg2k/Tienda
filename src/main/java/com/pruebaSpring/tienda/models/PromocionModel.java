package com.pruebaSpring.tienda.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Promocion")
public class PromocionModel {

    // Atributos
    @Id
    @Column(nullable = true)
    // Por limitar algo el campo en la BBDD
    @Size(max = 100)
    private String nombre;

    // Tiene que ser positivo, dos cifras decimales
    @Min(0)
    // @Digits(integer = 10, fraction = 2)
    private double descuento;

    // JsonIgnore para que no muestre esta parte en el JSON devuelto en peticiones
    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "PromocionesAplicadas", joinColumns = @JoinColumn(name = "nombre_promocion", referencedColumnName = "nombre"), inverseJoinColumns = @JoinColumn(name = "referencia_prenda", referencedColumnName = "referencia"))
    private Set<PrendaModel> prendasModels = new HashSet<>();

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

    public Set<PrendaModel> getPrendasModels() {
        return this.prendasModels;
    }

    public void setPrendasModels(Set<PrendaModel> prendasModels) {
        this.prendasModels = prendasModels;
    }
}
