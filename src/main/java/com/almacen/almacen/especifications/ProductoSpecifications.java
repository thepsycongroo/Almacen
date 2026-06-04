package com.almacen.almacen.especifications;

import com.almacen.almacen.entity.Producto;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class ProductoSpecifications {

    public static Specification<Producto> productosPorNombre(String nombre){
        return (root, query, cb) ->
                (nombre == null || nombre.isBlank())
                        ? null
                        : cb.like(cb.lower(root.get("nombre")), "%" + nombre.toLowerCase() + "%");
    }

    public static Specification<Producto> productosPorCategoria(String categoria){
        return (root, query, criteriaBuilder) ->
                (categoria == null || categoria.isBlank())? null :
                criteriaBuilder.like(criteriaBuilder.lower(root.get("categoria")),"%"+categoria.toLowerCase()+"%");
    }

    public static Specification<Producto> productosPorPrecioMin(BigDecimal precioMin){
        return (root, query, criteriaBuilder) ->
                (precioMin == null || precioMin.compareTo(BigDecimal.ZERO) <= 0)? null :
                        criteriaBuilder.greaterThanOrEqualTo(root.get("precio"),precioMin);
    }
    public static Specification<Producto> productosPorPrecioMax(BigDecimal precioMax){
        return (root, query, criteriaBuilder) ->
                (precioMax == null || precioMax.compareTo(BigDecimal.ZERO) <= 0)? null :
                        criteriaBuilder.lessThanOrEqualTo(root.get("precio"),precioMax);
    }

}
