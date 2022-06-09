package com.pruebaSpring.tienda.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pruebaSpring.tienda.models.PrendaModel;
import com.pruebaSpring.tienda.models.PrendaModel.Categorias;
import com.pruebaSpring.tienda.services.PrendaService;

@WebMvcTest(PrendaController.class)
public class PrendaControllerTest {

    // Nos permite realizar peticiones a API REST (controladores)
    @Autowired
    private MockMvc mockMVC;

    // Permite agregar simulacros de Mockito
    @MockBean
    private PrendaService prendaService;

    @InjectMocks
    private PrendaController prendaController;

    private PrendaModel prenda;

    // CREAR EMPLEADO ANTES DE LOS TEST

    @BeforeEach
    public void setUp() {

    }

    // TESTS

    @Test
    public void test_GetAll_Prenda_is_OK() throws Exception {
        mockMVC.perform(MockMvcRequestBuilders.get("/prendas"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void test_GetById_Prenda_is_OK() throws Exception {
        mockMVC.perform(MockMvcRequestBuilders.get("/prendas/S123456734"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void test_Post_Prenda_is_OK() throws Exception {
        // Creamos una prenda
        PrendaModel prenda = new PrendaModel();
        prenda.setReferencia("S123456734");
        prenda.setPrecio(BigDecimal.valueOf(111.54));
        prenda.setPrecio_promocionado(BigDecimal.valueOf(111.54));
        List<Categorias> categorias = new ArrayList<>();
        categorias.add(PrendaModel.Categorias.Hombre);
        prenda.setCategorias(categorias);
        //
        Mockito.when(prendaService.ppComprobationsPrenda(Mockito.any(PrendaModel.class))).thenReturn(prenda);
        //
        mockMVC.perform(MockMvcRequestBuilders.post("/prendas")
                .content(asJsonString(prenda))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
    }

    // Auxiliar para POST
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
