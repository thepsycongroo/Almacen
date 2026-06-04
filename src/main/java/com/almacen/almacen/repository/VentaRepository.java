package com.almacen.almacen.repository;

import com.almacen.almacen.entity.Ventas;
import com.almacen.almacen.enums.EstadoVenta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VentaRepository extends JpaRepository<Ventas,Long> {
    boolean existsBySucursal_IdAndEstado(Long sucursalId, EstadoVenta estadoDescripcion);
}
