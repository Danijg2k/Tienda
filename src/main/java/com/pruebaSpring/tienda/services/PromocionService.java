package com.pruebaSpring.tienda.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pruebaSpring.tienda.models.PromocionModel;
import com.pruebaSpring.tienda.repositories.PromocionRepository;

@Service
public class PromocionService {

    @Autowired
    PromocionRepository promocionRepository;

    public ArrayList<PromocionModel> obtenerPromociones() {
        return (ArrayList<PromocionModel>) promocionRepository.findAll();
    }

    public PromocionModel guardarPromocion(PromocionModel promocion) {
        return promocionRepository.save(promocion);
    }

    public Optional<PromocionModel> obtenerPorId(String id) {
        return promocionRepository.findById(id);
    }

    public boolean eliminarPromocion(String id) {
        try {
            promocionRepository.deleteById(id);
            return true;
        } catch (Exception err) {
            return false;
        }
    }
}
