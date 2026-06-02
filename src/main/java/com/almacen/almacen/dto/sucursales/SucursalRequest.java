package com.almacen.almacen.dto.sucursales;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SucursalRequest(
        @NotBlank(message = "Nombre es requerido")
        @Size(min=5,max=50, message = "El nombre debe tener entre 5 y 50 caracteres")
        String nombre,
        @NotBlank(message = "Direccion es requerido")
        @Size(min=10,max=150, message = "la direccion debe tener entre 10 y 150 caracteres")
        String direccion) {


}
