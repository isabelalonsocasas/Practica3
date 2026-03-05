package edu.comillas.icai.gitt.pat.spring.jpa;

import org.springframework.data.repository.CrudRepository;

public interface CarritoRepository extends CrudRepository<Carrito, String> {

    Iterable<Carrito> findByIdUsuario(String idUsuario);

    Iterable<Carrito> findByCorreo(String correo);
}
