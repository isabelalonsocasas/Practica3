# Práctica3 – Carrito con persistencia

## Descripción

En esta práctica se amplía la API desarrollada en la **Práctica 2**.

Ahora el carrito puede contener **varios artículos**, y la información se guarda en una **base de datos** 

Se han añadido también **entidades separadas para el carrito y para las líneas del carrito**.

## Cambios principales respecto a la Práctica 2

* Se ha añadido **persistencia en base de datos**.
* Se ha creado una **capa de servicios** para separar la lógica del controlador.
* El carrito ahora puede tener **varias líneas de artículos**.
* Se han creado dos entidades: `Carrito` y `LineaCarrito`.

## Entidades

### Carrito

Contiene la información general del carrito.

Campos:

* `idCarrito`: identificador del carrito.
* `idUsuario`: identificador del usuario.
* `correo`: correo del usuario.
* `totalPrecio`: precio total del carrito (calculado a partir de las líneas).

### LineaCarrito

Representa cada artículo dentro de un carrito.

Campos:

* `id`: identificador de la línea.
* `idArticulo`: identificador del artículo.
* `precioUnitario`: precio de una unidad del artículo.
* `numeroUnidades`: número de unidades del artículo.
* `costeLineaArticulo`: precio total de la línea (`precioUnitario * numeroUnidades`).
* `carrito`: referencia al carrito al que pertenece.

## Funcionalidades

La aplicación permite:

* Crear un carrito.
* Obtener un carrito.
* Obtener todos los carritos.
* Añadir una línea de artículo a un carrito.
* Listar todas las lineas asociadas a un carrito
* Eliminar una línea de un carrito.
* Eliminar un carrito.

Cuando se añade un artículo que **ya existe en el carrito**, se **suman las unidades** en lugar de crear una línea duplicada.

El **precio total del carrito** se recalcula automáticamente cada vez que se añaden o eliminan líneas.

## Colección de Postman

Puedes probar todos los endpoints desde la colección pública de Postman:

[Ver colección de Postman](https://documenter.getpostman.com/view/51822944/2sBXcKDKBD)

