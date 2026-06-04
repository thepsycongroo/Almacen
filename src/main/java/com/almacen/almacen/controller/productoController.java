package com.almacen.almacen.controller;

import com.almacen.almacen.dto.producto.ProductoRequest;
import com.almacen.almacen.dto.producto.ProductoResponse;
import com.almacen.almacen.services.ProductoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/producto")
public class productoController {
    private final ProductoService productoService;

    public productoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public ResponseEntity<List<ProductoResponse>> listar(@RequestParam(required = false) String nombre,
                                                         @RequestParam(required = false) String categoria,
                                                         @RequestParam(required = false) BigDecimal precioMin,
                                                         @RequestParam(required = false) BigDecimal precioMax){
        return ResponseEntity.ok(productoService.listar(nombre,categoria,precioMin,precioMax));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponse> obtenerPorId(@PathVariable @Positive(message = "El id debe ser positivo") Long id){
        return ResponseEntity.ok(productoService.obtenerPoId(id));
    }

    @PostMapping
    public ResponseEntity<ProductoResponse> registrar(@Valid @RequestBody ProductoRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(productoService.registrar(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponse> actualizar(@RequestBody ProductoRequest request, @PathVariable @Positive(message = "El id debe ser ositivo") Long id){
        return ResponseEntity.ok(productoService.actualizar(request,id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
