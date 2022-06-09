package com.pruebaSpring.tienda.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pruebaSpring.tienda.models.PrendaModel;
import com.pruebaSpring.tienda.repositories.PrendaRepository;
import com.pruebaSpring.tienda.utilities.constants.StringConstants;

@Service
@Transactional(rollbackFor = Exception.class)
public class PrendaService {

    @Autowired
    PrendaRepository prendaRepository;

    // GET ALL
    @Transactional(readOnly = true)
    public ArrayList<PrendaModel> getAllPrenda() {
        return (ArrayList<PrendaModel>) prendaRepository.findAll();
    }

    // GET BY ID
    @Transactional(readOnly = true)
    public Optional<PrendaModel> getByIdPrenda(String id) {
        return prendaRepository.findById(id);
    }

    // POST / PUT (NO COMPROBATIONS - USED WHEN APPLYING DISCOUNT)
    // AVOID SET PRICE OF NORMAL POST / PUT
    public PrendaModel ppPrecioPrenda(PrendaModel prenda) {
        return prendaRepository.save(prenda);
    }

    // POST / PUT (WITH COMPROBATIONS - USED NORMALLY)
    public PrendaModel ppComprobationsPrenda(PrendaModel prenda) {
        System.out.println("tres");
        // Si no coincide con el patrón que no se pueda guardar
        BigDecimal precioMin = new BigDecimal(0);
        if (!prenda.getReferencia().matches(StringConstants.regexRef)
                || (prenda.getPrecio().compareTo(precioMin) <= 0)) {
            // No se realiza el POST/PUT
            return null;
        }
        // Se puede realizar el POST/PUT (formato de datos correcto)
        // Hacemos que ambos precios coincidan y hacemos POST/UPDATE
        prenda.setPrecio_promocionado(prenda.getPrecio());
        return prendaRepository.save(prenda);
    }

    // DELETE
    public boolean deletePrenda(String id) {
        // Intentamos obtener la prenda pedida
        Optional<PrendaModel> prenda = prendaRepository.findById(id);
        // Si no se encuentra, no seguimos
        if (!prenda.isPresent()) {
            return false;
        }
        try {
            // La prenda existe, por lo que borramos sus promociones (tabla intermedia) ->
            prenda.get().removePromociones();
            // -> y posteriormente borramos a la misma prenda
            prendaRepository.deleteById(id);
            return true;
        } catch (Exception err) {
            return false;
        }
    }
}
