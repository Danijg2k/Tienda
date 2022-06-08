package com.pruebaSpring.tienda.controllers;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pruebaSpring.tienda.models.PrendaModel;
import com.pruebaSpring.tienda.models.PromocionModel;
import com.pruebaSpring.tienda.services.PrendaService;
import com.pruebaSpring.tienda.services.PromocionService;

@RestController
@RequestMapping(value = "/promociones")
public class PromocionController {

    @Autowired
    PromocionService promocionService;
    @Autowired
    PrendaService prendaService;

    // GET ALL
    @GetMapping()
    public ResponseEntity<ArrayList<PromocionModel>> getAll() {
        ArrayList<PromocionModel> promociones = promocionService.getAllPromocion();
        return new ResponseEntity<>(promociones, HttpStatus.OK);
    }

    // GET BY ID
    @GetMapping(path = "/{id}")
    public ResponseEntity<Optional<PromocionModel>> getById(@PathVariable("id") String id) {
        Optional<PromocionModel> promocion = promocionService.getByIdPromocion(id);
        if (promocion.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(promocion, HttpStatus.OK);
    }

    // POST
    @PostMapping()
    public ResponseEntity<PromocionModel> post(@RequestBody PromocionModel promocion) {
        PromocionModel promocionGuardada = promocionService.ppPromocion(promocion);
        // Avisamos de que se ha creado
        return new ResponseEntity<>(promocionGuardada, HttpStatus.CREATED);
    }

    // DELETE
    @DeleteMapping(path = "/{nombre}")
    public ResponseEntity<Boolean> delete(@PathVariable("nombre") String nombre) {
        // Obtener prendas promocionadas por la promoción actual
        Set<PrendaModel> prendasPromocionadas = promocionService.getByIdPromocion(nombre).get().getPrendasModels();
        // Si se puede borrar la promoción ->
        if (promocionService.deletePromocion(nombre)) {
            // -> Recalculamos precio promocionado de los productos afectados
            for (PrendaModel pr : prendasPromocionadas) {
                recalcularPrecioProm(pr.getReferencia());
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // APLICAR PROMOCIÓN (PUT) -> Llamamos a función pasando parámetro 0
    @PutMapping(path = "/aplicar")
    public ResponseEntity<Boolean> aplicarPromocion(@RequestParam("promocion") String nombre,
            @RequestParam("prenda") String referencia) {
        return aplicarDesaplicarPromo(nombre, referencia, 0);
    }

    // DESAPLICAR PROMOCIÓN (PUT) -> Llamamos a función pasando parámetro 1
    @PutMapping(path = "/desaplicar")
    public ResponseEntity<Boolean> desaplicarPromocion(@RequestParam("promocion") String nombre,
            @RequestParam("prenda") String referencia) {
        return aplicarDesaplicarPromo(nombre, referencia, 1);
    }

    // FUNCIÓN AUXILIAR PARA APLICAR / DESAPLICAR (NO REPETIR CÓDIGO)
    private ResponseEntity<Boolean> aplicarDesaplicarPromo(String nombre, String referencia, int opc) {
        // Recogemos la promoción y prenda
        PromocionModel promo = promocionService.getByIdPromocion(nombre).get();
        PrendaModel prenda = prendaService.getByIdPrenda(referencia).get();
        // Debemos eliminar la prenda de la lista de prendas afectadas por la promoción
        Set<PrendaModel> prendas = promo.getPrendasModels();
        // Aplicamos o desaplicamos dependiendo del parámetro pasado
        if (opc == 0) {
            prendas.add(prenda);
        } else if (opc == 1) {
            prendas.remove(prenda);
        }
        promo.setPrendasModels(prendas);
        // Hacemos un put de la promoción (actualizarla)
        promocionService.ppPromocion(promo);
        // Recalculamos precio de la prenda
        recalcularPrecioProm(referencia);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // FUNCIÓN AUXILIAR PARA RECALCULAR PRECIO
    private void recalcularPrecioProm(String ref) {
        // Obtenemos la prenda
        PrendaModel pr = prendaService.getByIdPrenda(ref).get();
        // Volvemos a poner el precio base, para empezar a calcular de nuevo descuentos
        pr.setPrecio_promocionado(pr.getPrecio());
        // Vamos aplicando los descuentos de las promociones
        for (PromocionModel promo : pr.getPromocionesModels()) {
            pr.setPrecio_promocionado(pr.getPrecio_promocionado() * (1 - promo.getDescuento() / 100));
        }
        // Guardamos la modificación
        prendaService.ppPrecioPrenda(pr);
    }
}
