package com.almacen.almacen.services;

import com.almacen.almacen.dto.producto.ProductoRequest;
import com.almacen.almacen.dto.producto.ProductoResponse;
import com.almacen.almacen.dto.sucursales.SucursalRequest;
import com.almacen.almacen.dto.sucursales.SucursalResponse;
import com.almacen.almacen.entity.Producto;
import com.almacen.almacen.enums.Categoria;
import com.almacen.almacen.exceptions.RecursoNoEncontradoException;
import com.almacen.almacen.mappers.ProductoMapper;
import com.almacen.almacen.repository.ProductoRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class ProductoServiceImpl implements ProductoService {


    private final ProductoRepository productoRepository;
    private final ProductoMapper productoMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ProductoResponse> listar() {
        log.info("Se estan listando los productos");
        return productoRepository.findAll().stream().map(entidad->productoMapper.entidadResponse(entidad)).toList();
    //return productoRepository.findAll().stream().map(productoMapper::entidadResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProductoResponse obtenerPoId(Long id) {
        return productoMapper.entidadResponse(obtenerPorIdOException(id));
    }

    @Override
    public ProductoResponse registrar(ProductoRequest request) {
        log.info("Se esta registrando un producto");
        Categoria categoria= Categoria.obtenerCategoriaPorDescripcion(request.categoria());
        Producto producto = productoMapper.requestEntidad(request,categoria);
        productoRepository.save(producto);
        log.info("Producto {} registrado",producto.getNombre());
        return productoMapper.entidadResponse(producto);
    }

    @Override
    public ProductoResponse actualizar(ProductoRequest request, Long id) {
        Producto producto = obtenerPorIdOException(id);
        log.info("Actualizando producto con id {}",id);
        producto.actualizar(request.nombre(),Categoria.obtenerCategoriaPorDescripcion(request.categoria()),request.precio(),request.cantidad());
        log.info("Producto con id {} actualizado",id);
        return productoMapper.entidadResponse(producto);
    }

    @Override
    public void eliminar(Long id) {
        Producto producto = obtenerPorIdOException(id);
        log.info("Eliminando producto con id {}",id);
        productoRepository.delete(producto);
        log.info("Producto con id {} eliminado",id);
    }

    public Producto obtenerPorIdOException(Long id){
        log.info("Buscando producto con id {}",id);
        return productoRepository.findById(id).orElseThrow(()-> new RecursoNoEncontradoException("El producto no encontrado con id "+id));

    }
}
