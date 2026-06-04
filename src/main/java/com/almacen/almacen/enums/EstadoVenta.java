package com.almacen.almacen.enums;

import com.almacen.almacen.exceptions.RecursoNoEncontradoException;
import com.almacen.almacen.utils.StringCustomUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
@Getter
public enum EstadoVenta {
    REGISTRADA("Registrada",1L),
    CANCELADA("Cancelada",2L);
    private final String descripcion;

    private final Long codigo;

    public static EstadoVenta obtenerEstadoPorDescripcion(String descripcion){
        StringCustomUtils.validarNoVacio(descripcion,"La descripcion es requerida");
        String descripcionNormalizada = StringCustomUtils.quitarAcentos(descripcion);

        for (EstadoVenta c : values()){
            if(StringCustomUtils.quitarAcentos(c.descripcion).equalsIgnoreCase(descripcionNormalizada))
                return c;
        }
        throw new RecursoNoEncontradoException("No existe un estado venta con la descripcion: "+ descripcion);
    }

    public EstadoVenta obtenerEstadoVentaPorCodigo(Long codigo){
        for (EstadoVenta c : values())
            if (Objects.equals(c.codigo,codigo))
                return c;

        throw new RecursoNoEncontradoException("No existe un estado de vemta con el codigo: "+codigo);
    }


}
