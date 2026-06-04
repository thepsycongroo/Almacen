package com.almacen.almacen.entity;

import com.almacen.almacen.enums.Categoria;
import com.almacen.almacen.utils.StringCustomUtils;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter @Setter
@ToString
@Table(name = "PRODUCTOS")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PRODUCTO", nullable = false)
    private Long id;

    @Column(name = "NOMBRE",length = 30, nullable = false)
    private String nombre;
    @Column(name = "CATEGORIA", nullable = false)
    @Enumerated(EnumType.STRING)
    private Categoria categoria;
    @Column(name = "PRECIO", nullable = false)
    private BigDecimal precio;
    @Column(name = "CANTIDAD", nullable = false)
    private Integer cantidad;

    @OneToMany(mappedBy = "producto",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DetalleVenta> detalleVentas= new ArrayList<>();

    public void actualizar(String nombre, Categoria categoria, BigDecimal precio, Integer cantidad) {
        validartamanio(nombre,categoria,precio,cantidad);

        this.nombre = nombre.trim();
        this.categoria = categoria;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public void aumentarCantidad(int cantidad){
        if (cantidad < 0)
            throw new IllegalArgumentException("La cantidad es requerida y debe ser positiva");
        this.cantidad += cantidad;
    }

    public void descontarCantidad(int cantidad){
        if (this.cantidad < cantidad)
            throw new IllegalArgumentException("Cantidad insuficiente para descontar");

        this.cantidad -= cantidad;
    }

    private void validartamanio(String nombre, Categoria categoria, BigDecimal precio, Integer cantidad){
        StringCustomUtils.validarTamanio(nombre,5,30,"El nombre es requerido y debe tener 5 y 30 caracteres");

        if (cantidad ==null)
            throw new IllegalArgumentException("La categoria es requerida");
        if (precio==null || precio.compareTo(BigDecimal.ZERO) <0)
            throw new IllegalArgumentException("El precio es requerido y debe ser positivo");
        if (cantidad == null || cantidad < 0)
            throw new IllegalArgumentException("El cantidad es requerido y debe ser positivo");
    }

}
