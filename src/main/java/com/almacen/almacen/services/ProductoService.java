package com.almacen.almacen.services;

import com.almacen.almacen.dto.producto.ProductoRequest;
import com.almacen.almacen.dto.producto.ProductoResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

public interface ProductoService {

    List<ProductoResponse> listar(String nombre, String categoria, BigDecimal precioMin,BigDecimal  precioMax);
    ProductoResponse obtenerPoId(Long id);

    ProductoResponse registrar(ProductoRequest request);

    ProductoResponse actualizar(ProductoRequest request, Long id);

    void eliminar(Long id);
}
