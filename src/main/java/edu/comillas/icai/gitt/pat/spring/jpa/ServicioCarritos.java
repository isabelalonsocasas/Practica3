package edu.comillas.icai.gitt.pat.spring.jpa;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ServicioCarritos {

    @Autowired
    CarritoRepository repoCarrito;

    @Autowired
    LineaCarritoRepository repoLinea;

    @Transactional
    public LineaCarrito anadeLinea(String idCarrito, LineaCarrito linea) {

        // Comprobar que el carrito existe
        Carrito carrito = repoCarrito.findById(idCarrito)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Carrito no encontrado"));

        //Validaciones de lineaCarrito
        if (linea == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Línea vacía");
        }
        if (linea.idArticulo == null || linea.idArticulo.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Falta idArticulo");
        }
        if (linea.precioUnitario == null || linea.numeroUnidades == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Falta precioUnitario o numeroUnidades");
        }

        // Comprobar si ya existe una linea
        LineaCarrito existente =
                repoLinea.findByCarrito_IdCarritoAndIdArticulo(idCarrito, linea.idArticulo);

        if (existente != null) {

            //sumamos unidades
            existente.numeroUnidades = existente.numeroUnidades + linea.numeroUnidades;

            // recalculamos el coste
            existente.costeLineaArticulo = existente.precioUnitario * existente.numeroUnidades;

            repoLinea.save(existente);

            recalculaTotalYGuarda(idCarrito);

            return existente;

        } else {

            // crear línea nueva
            linea.carrito = carrito;

            linea.costeLineaArticulo = linea.precioUnitario * linea.numeroUnidades;

            LineaCarrito guardada = repoLinea.save(linea);

            recalculaTotalYGuarda(idCarrito);

            return guardada;
        }
    }

    @Transactional
    public void borraLinea(String idCarrito, String idArticulo) {

        // Comprobar carrito existe
        repoCarrito.findById(idCarrito)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Carrito no encontrado"));

        // Buscar la línea
        LineaCarrito existente =
                repoLinea.findByCarrito_IdCarritoAndIdArticulo(idCarrito, idArticulo);

        if (existente == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Línea no encontrada");
        }

        // Borrar por id
        repoLinea.deleteById(existente.id);

        //Recalcular total del carrito
        recalculaTotalYGuarda(idCarrito);
    }

    @Transactional
    public void borraCarrito(String idCarrito) {

        repoCarrito.findById(idCarrito)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Carrito no encontrado"));

        repoLinea.deleteByCarrito_IdCarrito(idCarrito);

        repoCarrito.deleteById(idCarrito);
    }


    private void recalculaTotalYGuarda(String idCarrito) {

        double total = 0.0;
        for (LineaCarrito l : repoLinea.findByCarrito_IdCarrito(idCarrito)) {
            if (l.costeLineaArticulo != null) {
                total = total + l.costeLineaArticulo;
            } else {
                total = total + 0.0;
            }
        }

        Carrito carrito = repoCarrito.findById(idCarrito)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Carrito no encontrado"));

        carrito.totalPrecio = total;
        repoCarrito.save(carrito);
    }
}
