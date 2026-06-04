package com.almacen.almacen.controller;

import com.almacen.almacen.dto.sucursales.SucursalRequest;
import com.almacen.almacen.dto.sucursales.SucursalResponse;
import com.almacen.almacen.entity.Sucursal;
import com.almacen.almacen.services.SucursalService;
import com.almacen.almacen.services.VentaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/sucursales")
@AllArgsConstructor
@Validated
public class sucursalController {
    private final SucursalService sucursalService;



    @GetMapping
    public ResponseEntity<List<SucursalResponse>> listar(){
        return ResponseEntity.ok(sucursalService.listar());
    }


    @GetMapping("/{id}")
    public ResponseEntity<SucursalResponse> obtenerPorId(@PathVariable @Positive(message = "El ID debe ser positivo") Long id){
        return ResponseEntity.ok(sucursalService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<SucursalResponse> registrar(@Valid @RequestBody SucursalRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(sucursalService.registrar(request));
    }

    @PutMapping("/{id}")
    public  ResponseEntity<SucursalResponse> actualizar(@PathVariable @Positive Long id, @Valid @RequestBody SucursalRequest request ){
        return ResponseEntity.ok(sucursalService.actualizar(request,id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable @Positive(message = "El ID debe ser positivo") Long id){
        sucursalService.eliminar(id);
        return ResponseEntity.noContent().build();

    }


}
