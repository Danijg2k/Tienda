package com.pruebaSpring.tienda.models;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pruebaSpring.tienda.constants.StringConstants;

@Entity
@Table(name = "Prenda")
public class PrendaModel {

    // Atributos
    @Id
    @Column(nullable = true)
    // Comprobaciones para refencia (10 caracteres y seguir patrón)
    @Size(min = 10, max = 10)
    @Pattern(regexp = StringConstants.regexRef, message = StringConstants.regexPatternAdvice)
    private String referencia;

    // Tiene que ser positivo, dos cifras decimales
    @Min(0)
    // @Digits(integer = 10, fraction = 2)
    private double precio;

    // Tiene que ser positivo, dos cifras decimales
    @Min(0)
    // @Digits(integer = 10, fraction = 2)
    private double precio_promocionado;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<Categorias> categorias;

    // JsonIgnore para que no muestre esta parte en el JSON devuelto en peticiones
    @JsonIgnore
    @ManyToMany(mappedBy = "prendasModels")
    private Set<PromocionModel> promocionesModels = new HashSet<>();

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

    public Set<PromocionModel> getPromocionesModels() {
        return this.promocionesModels;
    }

    public void setPromocionesModels(Set<PromocionModel> promocionesModels) {
        this.promocionesModels = promocionesModels;
    }

    // Si borramos promoción se borra prenda, porque promoción es la parte que
    // manda, pero si queremos que se pueda realizar al revés tenemos que
    // gestionarlo

    // Métodos para que al borrar prenda se borren los registros de la
    // "tabla intermedia"

    private void removePromo(PromocionModel promo) {
        this.getPromocionesModels().remove(promo);
        promo.getPrendasModels().remove(this);
    }

    public void removePromociones() {
        for (PromocionModel promo : new HashSet<>(promocionesModels)) {
            removePromo(promo);
        }
    }
    //
}
