package com.almacen.almacen.entity;

import com.almacen.almacen.dto.ventas.DetallesVentaResponse;
import com.almacen.almacen.enums.EstadoVenta;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Table(name = "VENTAS")
public class Ventas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_VENTA", nullable = false)
    private long id_venta;
    @Column(name = "ESTADO", nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoVenta estado;
    @Column(name = "FECHA", nullable = false)
    private LocalDateTime fecha;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_SUCURSAL",nullable = false)
    private Sucursal sucursal;

    @OneToMany(mappedBy = "ventas",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DetalleVenta> detalleVenta = new ArrayList<>();

    public  void agregarDetalle(DetalleVenta detalleVenta){
        if (detalleVenta == null)
            throw  new IllegalArgumentException("El detalle es requerido");
        this.detalleVenta.add(detalleVenta);
        detalleVenta.setVentas(this);
    }

    public  void cancelar(){
        if (this.estado == EstadoVenta.CANCELADA)
            throw new IllegalStateException("La venta ya esta cancelada");
        this.estado= EstadoVenta.CANCELADA;
    }

    public BigDecimal calcularTotal(){
        return  this.detalleVenta.stream()
                .map(DetalleVenta::calcularSubTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }






}

