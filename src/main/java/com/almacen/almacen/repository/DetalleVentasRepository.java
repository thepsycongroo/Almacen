package com.almacen.almacen.repository;

import com.almacen.almacen.entity.DetalleVenta;
import com.almacen.almacen.enums.EstadoVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleVentasRepository extends JpaRepository<DetalleVenta,Long> {
    boolean existsByProducto_IdAndVentas_Estado(Long productoId, EstadoVenta ventasEstado);
}
