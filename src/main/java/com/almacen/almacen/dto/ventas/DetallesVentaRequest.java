package com.almacen.almacen.dto.ventas;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record DetallesVentaRequest(
        @NotNull(message = "El ID del producto es requerido")
        @Positive(message = "El Id del producto debe ser positivo")
        Long idProducto,
        @NotNull(message = "La cantidad es requerido")
        @Positive(message = "La cantidad del producto debe ser positivo")
        Integer cantidadProducto
) {
}
