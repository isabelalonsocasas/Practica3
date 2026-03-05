package edu.comillas.icai.gitt.pat.spring.jpa;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class controladorRest {

    @Autowired
    ServicioCarritos servicioCarritos;

    @Autowired
    CarritoRepository repoCarrito;

    @Autowired
    LineaCarritoRepository repoLinea;

    //Crear un carrito nuevo
    @PostMapping("/api/carrito")
    @ResponseStatus(HttpStatus.CREATED)
    public Carrito crea(@Valid @RequestBody Carrito carritoNuevo) {
        if (carritoNuevo.totalPrecio == null) carritoNuevo.totalPrecio = 0.0;
        return repoCarrito.save(carritoNuevo);
    }

    //Obtener todos los carritos
    @GetMapping("/api/carrito")
    public Iterable<Carrito> obtenerTodos() {
        return repoCarrito.findAll();
    }

    //Obtener por id el carrito
    @GetMapping("/api/carrito/{idCarrito}")
    public Carrito obtener(@PathVariable String idCarrito) {
        Carrito carrito = repoCarrito.findById(idCarrito).orElse(null);

        if (carrito == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Carrito no encontrado");
        }

        return carrito;
    }

    //Eliminar un carrito
    @DeleteMapping("/api/carrito/{idCarrito}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void elimina(@PathVariable String idCarrito) {
        servicioCarritos.borraCarrito(idCarrito);
    }

    //Añadir una linea
    @PostMapping("/api/carrito/{idCarrito}/lineas")
    @ResponseStatus(HttpStatus.CREATED)
    public LineaCarrito anadeLinea(@PathVariable String idCarrito,
                                   @Valid @RequestBody LineaCarrito lineaNueva) {

        return servicioCarritos.anadeLinea(idCarrito, lineaNueva);
    }

    //Borrar linea
    @DeleteMapping("/api/carrito/{idCarrito}/lineas/{idArticulo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void borraLinea(@PathVariable String idCarrito,
                           @PathVariable String idArticulo) {
        servicioCarritos.borraLinea(idCarrito, idArticulo);
    }

    //Mostrar lineas por ID del carrito
    @GetMapping("/api/carrito/{idCarrito}/lineas")
    public Iterable<LineaCarrito> obtenerLineas(@PathVariable String idCarrito) {

        repoCarrito.findById(idCarrito)
                .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Carrito no encontrado"));

        return repoLinea.findByCarrito_IdCarrito(idCarrito);
    }


}
