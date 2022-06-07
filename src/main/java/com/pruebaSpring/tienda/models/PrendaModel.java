package com.pruebaSpring.tienda.models;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "Prenda")
public class PrendaModel {

    // Atributos
    @Id
    @Column(nullable = true)
    private String referencia;

    private double precio;

    private double precio_promocionado;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<Categorias> categorias;

    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(name = "PromocionesAplicadas", joinColumns = @JoinColumn(name = "referencia_prenda", referencedColumnName = "referencia"), inverseJoinColumns = @JoinColumn(name = "nombre_promocion", referencedColumnName = "nombre"))
    private List<PromocionModel> promocionesModels;

    public enum Categorias {
        Mujer, Hombre, Accesorios, Pantalones, Camisetas, Zapatos, Zapatillas
    }

    // Getters y Setters
    public String getReferencia() {
        return this.referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public double getPrecio() {
        return this.precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getPrecio_promocionado() {
        return this.precio_promocionado;
    }

    public void setPrecio_promocionado(double precio_promocionado) {
        this.precio_promocionado = precio_promocionado;
    }

    public List<Categorias> getCategorias() {
        return this.categorias;
    }

    public void setCategorias(List<Categorias> categorias) {
        this.categorias = categorias;
    }

}
