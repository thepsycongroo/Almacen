package com.almacen.almacen.services;

import com.almacen.almacen.dto.ventas.VentaRequest;
import com.almacen.almacen.dto.ventas.VentaResponse;

import java.util.List;

public interface VentaService {


    List<VentaResponse> listar();
    List<VentaResponse> listarCanceladas();
    VentaResponse obtenerPorId(Long id);
    VentaResponse registrar(VentaRequest request);
    void cancelar(Long id);
}
