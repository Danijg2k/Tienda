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
    public ResponseEntity<Optional<PrendaModel>> getById(@PathVariable("id") String id) {
        Optional<PrendaModel> prenda = prendaService.getByIdPrenda(id);
        if (prenda.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(prenda, HttpStatus.OK);
    }

    // POST
    @PostMapping()
    public ResponseEntity<PrendaModel> post(@RequestBody PrendaModel prenda) {
        // Truncamos el precio a dos decimales
        bigDecimalFunctions.checkPrice(prenda);
        //
        PrendaModel prendaGuardada = prendaService.ppComprobationsPrenda(prenda);
        // Si el formato de datos no es válido
        if (prendaGuardada == null) {
            System.out.println("uno");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        // Avisamos de que se ha creado
        System.out.println("dos");
        return new ResponseEntity<>(prendaGuardada, HttpStatus.CREATED);
    }

    // PUT
    @PutMapping(path = "/{id}")
    public ResponseEntity<PrendaModel> put(@RequestBody PrendaModel prenda, @PathVariable("id") String id) {
        // Truncamos el precio a dos decimales
        bigDecimalFunctions.checkPrice(prenda);
        //
        prenda.setReferencia(id);
        PrendaModel prendaModificada = prendaService.ppComprobationsPrenda(prenda);
        // Si el formato de datos no es válido
        if (prendaModificada == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        // Avisamos de que se ha modificado
        return new ResponseEntity<>(prendaModificada, HttpStatus.OK);
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
