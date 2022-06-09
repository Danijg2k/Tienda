package com.pruebaSpring.tienda.services;

import java.math.BigDecimal;
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
        // Si el descuento es 100% o mayor devolvemos respuesta correspondiente
        BigDecimal descuentoMax = new BigDecimal(100); // Exclusivo (100 no es válido)
        if (promocion.getDescuento().compareTo(descuentoMax) >= 0) {
            // No se realiza el POST/PUT
            return null;
        }
        // Se puede realizar el POST/PUT (formato de datos correcto)
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
