package com.almacen.almacen.services;

import com.almacen.almacen.dto.sucursales.SucursalRequest;
import com.almacen.almacen.dto.sucursales.SucursalResponse;
import com.almacen.almacen.entity.Sucursal;
import com.almacen.almacen.enums.EstadoVenta;
import com.almacen.almacen.exceptions.RecursoNoEncontradoException;
import com.almacen.almacen.mappers.SucursalMapper;
import com.almacen.almacen.repository.DetalleVentasRepository;
import com.almacen.almacen.repository.SucursalRepository;
import com.almacen.almacen.repository.VentaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class SucursalServiceImpl implements SucursalService {

    private final SucursalRepository sucursalRepository;
    private final SucursalMapper sucursalMapper;
    private final VentaRepository ventaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<SucursalResponse> listar() {
        log.info("Listando todas las sucursales");
        return sucursalRepository.findAll().stream().map(entidad-> sucursalMapper.entidadResponse(entidad)).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public SucursalResponse obtenerPorId(Long id) {
        return sucursalMapper.entidadResponse(obtenerSucursalOException(id));
    }

    @Override
    public SucursalResponse registrar(SucursalRequest request) {
        validarDatosUnicos(request);
        Sucursal sucursal = sucursalMapper.requestEntidad(request);
        sucursalRepository.save(sucursal);
        return sucursalMapper.entidadResponse(sucursal);
    }

    @Override
    public SucursalResponse actualizar(SucursalRequest request, Long id) {
        Sucursal sucursal = obtenerSucursalOException(id);
        validarCambiosUnicos(request,id);

        sucursal.actualizar(request.nombre(),request.direccion());
        log.info("Sucursal con id {} actualizada correctamente",id);
        //sucursal.save(sucursal); al tener transacction detecta en este caso automaticamente un cambio y guarda

        return sucursalMapper.entidadResponse(sucursal);
    }

    @Override
    public void eliminar(Long id) {
        Sucursal sucursal = obtenerSucursalOException(id);
        log.info("Eliminando Sucursal con id {}",id);
        if (ventaRepository.existsBySucursal_IdAndEstado(id, EstadoVenta.REGISTRADA))
            throw new IllegalStateException("Existe almenos 1 venta registrada");
        sucursalRepository.delete(sucursal);
        log.info("Sucursal con id {} eliminada",id);
    }


    private Sucursal obtenerSucursalOException(Long id){
        log.info("Buscando sucursal con id : {}",id);
        return sucursalRepository.findById(id).orElseThrow(()-> new RecursoNoEncontradoException("Sucursal no encontrada con id:"+id));
    }

    private void validarDatosUnicos(SucursalRequest request){
        log.info("Validando nombre unico");
        if(sucursalRepository.existsByNombreIgnoreCase(request.nombre().trim()))
            throw new IllegalArgumentException("Ya existe una sucursal con el nombre de: "+request.nombre());

    }


    private void validarCambiosUnicos(SucursalRequest request,Long id){
        log.info("Validando cambio de nombre unico");
        if(sucursalRepository.existsByNombreIgnoreCaseAndIdNot(request.nombre().trim(),id))
            throw new IllegalArgumentException("Ya existe una sucursal con el nombre de: "+request.nombre());

    }









}
