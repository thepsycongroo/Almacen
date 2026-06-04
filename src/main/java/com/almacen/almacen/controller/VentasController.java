package com.almacen.almacen.controller;

import com.almacen.almacen.dto.producto.ProductoResponse;
import com.almacen.almacen.dto.ventas.VentaRequest;
import com.almacen.almacen.dto.ventas.VentaResponse;
import com.almacen.almacen.entity.Ventas;
import com.almacen.almacen.services.VentaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/ventas")
@AllArgsConstructor
@Validated
public class VentasController {
    private final VentaService ventaService;


    @GetMapping
    public ResponseEntity<List<VentaResponse>> listar(){
        return ResponseEntity.ok(ventaService.listar());
    }

    @GetMapping("/canceladas")
    public ResponseEntity<List<VentaResponse>> listarCanceladas(){
        return ResponseEntity.ok(ventaService.listarCanceladas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VentaResponse> obtenerVentaPorId(@PathVariable Long id){
        return ResponseEntity.ok(ventaService.obtenerPorId(id));
    }

    @PostMapping
    public  ResponseEntity<VentaResponse> registrar(@RequestBody VentaRequest request){
        return  ResponseEntity.status(HttpStatus.CREATED).body(ventaService.registrar(request));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<VentaResponse> eliminarVenta(@PathVariable Long id){
        ventaService.cancelar(id);
        return ResponseEntity.noContent().build();
    }
}
