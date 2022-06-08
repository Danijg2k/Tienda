package com.pruebaSpring.tienda.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pruebaSpring.tienda.models.PromocionModel;
import com.pruebaSpring.tienda.repositories.PromocionRepository;

@Service
@Transactional(rollbackFor = Exception.class)
public class PromocionService {

    @Autowired
    PromocionRepository promocionRepository;

    // GET ALL
    @Transactional(readOnly = true)
    public ArrayList<PromocionModel> getAllPromocion() {
        return (ArrayList<PromocionModel>) promocionRepository.findAll();
    }

    // GET BY ID
    @Transactional(readOnly = true)
    public Optional<PromocionModel> getByIdPromocion(String id) {
        return promocionRepository.findById(id);
    }

    // POST / PUT
    public PromocionModel ppPromocion(PromocionModel promocion) {
        return promocionRepository.save(promocion);
    }

    // DELETE
    public boolean deletePromocion(String id) {
        try {
            promocionRepository.deleteById(id);
            return true;
        } catch (Exception err) {
            return false;
        }
    }

}
