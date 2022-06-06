package com.pruebaSpring.tienda.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.pruebaSpring.tienda.models.PrendaModel;

// Repositorio que hereda métodos CRUD
@Repository
public interface PrendaRepository extends CrudRepository<PrendaModel, String> {

}
