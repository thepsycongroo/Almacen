package com.almacen.almacen.dto.ventas;

import java.math.BigDecimal;

public record DetallesVentaResponse(
        Long idProducto,
        String nombreProducto,
        Integer cantidadProducto,
        BigDecimal precioProducto,
        BigDecimal subtotal) {
}
