package edu.comillas.icai.gitt.pat.spring.jpa;

import org.springframework.data.repository.CrudRepository;

public interface LineaCarritoRepository extends CrudRepository<LineaCarrito, Long> {

    Iterable<LineaCarrito> findByCarrito_IdCarrito(String idCarrito);

    LineaCarrito findByCarrito_IdCarritoAndIdArticulo(String idCarrito, String idArticulo);

    void deleteByCarrito_IdCarritoAndIdArticulo(String idCarrito, String idArticulo);

    void deleteByCarrito_IdCarrito(String idCarrito);
}
