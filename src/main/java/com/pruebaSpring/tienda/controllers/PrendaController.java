package com.pruebaSpring.tienda.controllers;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.pruebaSpring.tienda.models.PrendaModel;
import com.pruebaSpring.tienda.services.PrendaService;

@RestController
@RequestMapping(value = "/prendas")
public class PrendaController {

    @Autowired
    PrendaService prendaService;

    @GetMapping()
    public ArrayList<PrendaModel> obtenerPrendas() {
        return prendaService.obtenerPrendas();
    }

    @PostMapping()
    public PrendaModel guardarPrenda(@RequestBody PrendaModel prenda) {
        return prendaService.guardarPrenda(prenda);
    }

    @GetMapping(path = "/{id}")
    public Optional<PrendaModel> obtenerPrendaPorId(@PathVariable("id") String id) {
        return prendaService.obtenerPorId(id);
    }

    @DeleteMapping(path = "/{id}")
    public String eliminarPorId(@PathVariable("id") String id) {
        boolean ok = this.prendaService.eliminarPrenda(id);
        if (ok) {
            return "Eliminado prenda con id " + id;
        } else {
            return "No se ha podido eliminar el prenda con id " + id;
        }
    }
}
