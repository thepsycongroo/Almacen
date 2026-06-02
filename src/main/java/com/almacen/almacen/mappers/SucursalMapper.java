package com.almacen.almacen.mappers;

import com.almacen.almacen.dto.sucursales.SucursalRequest;
import com.almacen.almacen.dto.sucursales.SucursalResponse;
import com.almacen.almacen.entity.Sucursal;
import org.springframework.stereotype.Component;

@Component
public class SucursalMapper {
    public Sucursal requestEntidad(SucursalRequest request){
        if(request == null) return null;

        return Sucursal.builder()
                .nombre(request.nombre().trim())
                .direccion(request.direccion().trim())
                .build();
    }
    public SucursalResponse entidadResponse(Sucursal entidad){
        if(entidad == null) return null;

        return new SucursalResponse(entidad.getId(),entidad.getNombre(),entidad.getDireccion());
    }

}
