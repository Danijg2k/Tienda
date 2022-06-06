package com.pruebaSpring.tienda.controllers;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pruebaSpring.tienda.models.PromocionModel;
import com.pruebaSpring.tienda.services.PromocionService;

@RestController
@RequestMapping(value = "/promociones")
public class PromocionController {

    @Autowired
    PromocionService promocionService;

    @GetMapping()
    public ArrayList<PromocionModel> obtenerPromociones() {
        return promocionService.obtenerPromociones();
    }

    @PostMapping()
    public PromocionModel guardarPromocion(@RequestBody PromocionModel promocion) {
        return promocionService.guardarPromocion(promocion);
    }

    @GetMapping(path = "/{id}")
    public Optional<PromocionModel> obtenerPromocionPorId(@PathVariable("id") String id) {
        return promocionService.obtenerPorId(id);
    }

    @DeleteMapping(path = "/{id}")
    public String eliminarPorId(@PathVariable("id") String id) {
        boolean ok = this.promocionService.eliminarPromocion(id);
        if (ok) {
            return "Eliminado promocion con id " + id;
        } else {
            return "No se ha podido eliminar el promocion con id " + id;
        }
    }
}
