package com.almacen.almacen.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder
@Table(name = "DETALLES_VENTAS")
public class DetalleVenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DETALLE_VENTA", nullable = false)
    private Long id_detalle_venta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_VENTA",nullable = false)
    private Ventas ventas;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PRODUCTO",nullable = false)
    private Producto producto;

    @Column(name = "CANTIDAD_PRODUCTO",nullable = false)
    private Integer cantidad_producto;

    @Column(name = "PRECIO_PRODUCTO", nullable = false)
    private BigDecimal precio_producto;

    public BigDecimal calcularSubTotal(){
        return this.precio_producto.multiply(BigDecimal.valueOf(this.cantidad_producto));
    }
}
