package edu.comillas.icai.gitt.pat.spring.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Carrito {

    @Id
    public String idCarrito;

    @Column(nullable = false)
    public String idUsuario;

    @Column(nullable = false)
    public String correo;

    @Column(nullable = false)
    public Double totalPrecio;
}
