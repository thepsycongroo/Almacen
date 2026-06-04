package com.almacen.almacen.mappers;

import com.almacen.almacen.dto.sucursales.SucursalResponse;
import com.almacen.almacen.dto.ventas.DetallesVentaRequest;
import com.almacen.almacen.dto.ventas.DetallesVentaResponse;
import com.almacen.almacen.dto.ventas.VentaRequest;
import com.almacen.almacen.dto.ventas.VentaResponse;
import com.almacen.almacen.entity.DetalleVenta;
import com.almacen.almacen.entity.Producto;
import com.almacen.almacen.entity.Sucursal;
import com.almacen.almacen.entity.Ventas;
import com.almacen.almacen.enums.EstadoVenta;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class VentasMapper {
    public Ventas requestEntidad(VentaRequest request){
        if (request==null)
            return null;
        Ventas venta = Ventas.builder()
                .sucursal(Sucursal.builder().id(request.idSocursal()).build())
                .fecha(LocalDateTime.now())
                .estado(EstadoVenta.REGISTRADA)
                .build();


        venta.setDetalleVenta(listaVenta(request, venta));

        return venta;
    }


    public VentaResponse entidadResponse(Ventas venta){
        if (venta==null)
            return null;
        return new VentaResponse(
                venta.getId_venta(),
                venta.getFecha().toString(),
                venta.getEstado().getDescripcion(),
                new SucursalResponse(venta.getSucursal().getId(),venta.getSucursal().getNombre(),venta.getSucursal().getDireccion()),
                listaDetallesVentas(venta),
                venta.calcularTotal()
        );

    }

    private List<DetallesVentaResponse> listaDetallesVentas(Ventas venta){
        return venta.getDetalleVenta().stream().map(detalle-> new DetallesVentaResponse(
                        detalle.getProducto().getId(),
                        detalle.getProducto().getNombre(),
                        detalle.getCantidad_producto(),
                        detalle.getPrecio_producto(),
                        detalle.calcularSubTotal()
                ))
                .toList();
    }
    private List<DetalleVenta> listaVenta(VentaRequest detalleVenta, Ventas venta){

        return detalleVenta.productos().stream()
                .map(detalle-> DetalleVenta.builder()
                        .ventas(venta)
                        .producto(
                                Producto.builder()
                                        .id(detalle.idProducto())
                                        .build()
                        ).cantidad_producto(detalle.cantidadProducto()).build()
                )
                .toList();
    }
}
