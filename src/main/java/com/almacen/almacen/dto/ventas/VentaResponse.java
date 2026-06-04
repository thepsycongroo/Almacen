package com.almacen.almacen.dto.ventas;

import com.almacen.almacen.dto.sucursales.SucursalResponse;

import java.math.BigDecimal;
import java.util.List;

public record VentaResponse(
        Long id,
        String fecha,
        String estado,
        SucursalResponse sucursal,
        List<DetallesVentaResponse> detalles,
        BigDecimal total
) {
}
