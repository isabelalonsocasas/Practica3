package edu.comillas.icai.gitt.pat.spring.jpa;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class LineaCarrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(optional = false)
    public Carrito carrito; //

    @Column(nullable = false)
    public String idArticulo;

    @Column(nullable = false)
    public Double precioUnitario;

    @Column(nullable = false)
    public Integer numeroUnidades;

    @Column(nullable = false)
    public Double costeLineaArticulo;
}
