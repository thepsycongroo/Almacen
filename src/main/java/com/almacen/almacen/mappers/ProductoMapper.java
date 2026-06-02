package com.almacen.almacen.mappers;

import com.almacen.almacen.dto.producto.ProductoRequest;
import com.almacen.almacen.dto.producto.ProductoResponse;
import com.almacen.almacen.entity.Producto;
import com.almacen.almacen.enums.Categoria;
import org.springframework.stereotype.Component;

@Component
public class ProductoMapper {

    public Producto requestEntidad(ProductoRequest request, Categoria categoria){
        if (request==null)
            return null;
        return Producto.builder().nombre(request.nombre()).categoria(categoria).precio(request.precio()).cantidad(request.cantidad()).build();

    }

    public ProductoResponse entidadResponse(Producto entidad){
        if (entidad==null)
            return null;
        return new ProductoResponse(entidad.getId(),entidad.getNombre(),entidad.getCategoria().getDescripcion(),entidad.getPrecio(),entidad.getCantidad());

    }
}
