package com.pruebaSpring.tienda.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.pruebaSpring.tienda.models.PrendaModel;
import com.pruebaSpring.tienda.models.PrendaModel.Categorias;

@ExtendWith(MockitoExtension.class)
public class PrendaServiceTest {

    @Mock
    private PrendaService prendaService;

    private PrendaModel prenda;

    @BeforeEach
    public void setUp() {
        prenda = new PrendaModel();
        prenda.setReferencia("S123456734");
        prenda.setPrecio(BigDecimal.valueOf(111.54));
        prenda.setPrecio_promocionado(BigDecimal.valueOf(111.54));
        List<Categorias> categorias = new ArrayList<>();
        categorias.add(PrendaModel.Categorias.Hombre);
        prenda.setCategorias(categorias);
    }

    // TESTS

    @Test
    public void getAll() {
        List<PrendaModel> prendas = prendaService.getAllPrenda();
        Assertions.assertNotNull(prendas.get(0));
    }
}
