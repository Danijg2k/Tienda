package com.pruebaSpring.tienda.controllers;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pruebaSpring.tienda.models.PrendaModel;
import com.pruebaSpring.tienda.services.PrendaService;
import com.pruebaSpring.tienda.utilities.functions.bigDecimalFunctions;

@RestController
@RequestMapping(value = "/prendas")
public class PrendaController {

    @Autowired
    PrendaService prendaService;

    // GET ALL
    @GetMapping()
    public ResponseEntity<ArrayList<PrendaModel>> getAll() {
        ArrayList<PrendaModel> prendas = prendaService.getAllPrenda();
        return new ResponseEntity<>(prendas, HttpStatus.OK);
    }

    // GET BY ID
    @GetMapping(path = "/{id}")
    public ResponseEntity<PrendaModel> getById(@PathVariable("id") String id) {
        Optional<PrendaModel> prenda = prendaService.getByIdPrenda(id);
        if (prenda.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(prenda.get(), HttpStatus.OK);
    }

    // POST
    @PostMapping()
    public ResponseEntity<PrendaModel> post(@RequestBody PrendaModel prenda) {
        // Truncamos el precio a dos decimales
        bigDecimalFunctions.truncatePrice(prenda);
        //
        PrendaModel prendaGuardada = prendaService.ppComprobationsPrenda(prenda);
        // Si el formato de datos no es válido
        if (prendaGuardada == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        // Avisamos de que se ha creado
        return new ResponseEntity<>(prendaGuardada, HttpStatus.CREATED);
    }

    // DELETE
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id) {
        boolean ok = this.prendaService.deletePrenda(id);
        if (ok) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
