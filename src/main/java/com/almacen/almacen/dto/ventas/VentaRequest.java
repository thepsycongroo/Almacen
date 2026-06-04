package com.almacen.almacen.dto.ventas;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record VentaRequest(
        @NotNull(message = "El ID de la socursal es requerida")
        @Positive(message = "El ID de la socursal debe ser positivo")
        Long idSocursal,
        @NotEmpty(message = "La lista de productos es requerida y no debe estar vacia")
        List<@Valid DetallesVentaRequest> productos
) {
}
