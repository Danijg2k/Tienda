package com.pruebaSpring.tienda.controllers;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pruebaSpring.tienda.models.PrendaModel;
import com.pruebaSpring.tienda.services.PrendaService;

@RestController
@RequestMapping(value = "/prendas")
public class PrendaController {

    @Autowired
    PrendaService prendaService;

    @GetMapping()
    public ResponseEntity<ArrayList<PrendaModel>> obtenerPrendas() {
        ArrayList<PrendaModel> prendas = prendaService.obtenerPrendas();
        return new ResponseEntity<>(prendas, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<PrendaModel> guardarPrenda(@RequestBody PrendaModel prenda) {
        PrendaModel prendaGuardada = prendaService.guardarPrenda(prenda);
        // Si el formato de datos no es válido
        if (prendaGuardada == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        // Avisamos de que se ha creado
        return new ResponseEntity<>(prendaGuardada, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<PrendaModel> modificarPrenda(@RequestBody PrendaModel prenda, @PathVariable("id") String id) {
        prenda.setReferencia(id);
        PrendaModel prendaModificada = prendaService.guardarPrenda(prenda);
        // Si el formato de datos no es válido
        if (prendaModificada == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        // Avisamos de que se ha modificado
        return new ResponseEntity<>(prendaModificada, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Optional<PrendaModel>> obtenerPrendaPorId(@PathVariable("id") String id) {
        Optional<PrendaModel> prenda = prendaService.obtenerPorId(id);
        if (prenda.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(prenda, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> eliminarPorId(@PathVariable("id") String id) {
        boolean ok = this.prendaService.eliminarPrenda(id);
        if (ok) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
