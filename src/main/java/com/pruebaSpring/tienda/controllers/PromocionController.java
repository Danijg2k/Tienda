package com.pruebaSpring.tienda.controllers;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ArrayList<PromocionModel>> obtenerPromociones() {
        ArrayList<PromocionModel> promociones = promocionService.obtenerPromociones();
        return new ResponseEntity<>(promociones, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<PromocionModel> guardarPromocion(@RequestBody PromocionModel promocion) {
        PromocionModel promocionGuardada = promocionService.guardarPromocion(promocion);
        // Avisamos de que se ha creado
        return new ResponseEntity<>(promocionGuardada, HttpStatus.CREATED);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Optional<PromocionModel>> obtenerPromocionPorId(@PathVariable("id") String id) {
        Optional<PromocionModel> promocion = promocionService.obtenerPorId(id);
        if (promocion.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(promocion, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> eliminarPorId(@PathVariable("id") String id) {
        boolean ok = this.promocionService.eliminarPromocion(id);
        if (ok) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
