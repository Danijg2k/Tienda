package com.pruebaSpring.tienda.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pruebaSpring.tienda.constants.Constants;
import com.pruebaSpring.tienda.models.PrendaModel;
import com.pruebaSpring.tienda.repositories.PrendaRepository;

@Service
public class PrendaService {

    @Autowired
    PrendaRepository prendaRepository;

    public ArrayList<PrendaModel> obtenerPrendas() {
        return (ArrayList<PrendaModel>) prendaRepository.findAll();
    }

    public PrendaModel guardarPrenda(PrendaModel prenda) {
        // Si no coincide con el patrón que no se pueda guardar
        if (!prenda.getReferencia().matches(Constants.regexRef) || prenda.getPrecio() < 0) {
            // No se realiza el POST/PUT
            return null;
        }
        // Se puede realizar el POST/PUT (formato de datos correcto)
        // Hacemos que ambos precios coincidan y hacemos POST/UPDATE
        prenda.setPrecio_promocionado(prenda.getPrecio());
        return prendaRepository.save(prenda);
    }

    public Optional<PrendaModel> obtenerPorId(String id) {
        return prendaRepository.findById(id);
    }

    public boolean eliminarPrenda(String id) {
        try {
            prendaRepository.deleteById(id);
            return true;
        } catch (Exception err) {
            return false;
        }
    }
}
