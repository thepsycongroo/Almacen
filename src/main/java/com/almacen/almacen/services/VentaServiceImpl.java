package com.almacen.almacen.services;

import com.almacen.almacen.dto.ventas.DetallesVentaResponse;
import com.almacen.almacen.dto.ventas.VentaRequest;
import com.almacen.almacen.dto.ventas.VentaResponse;
import com.almacen.almacen.entity.DetalleVenta;
import com.almacen.almacen.entity.Producto;
import com.almacen.almacen.entity.Ventas;
import com.almacen.almacen.enums.Categoria;
import com.almacen.almacen.enums.EstadoVenta;
import com.almacen.almacen.exceptions.RecursoNoEncontradoException;
import com.almacen.almacen.mappers.SucursalMapper;
import com.almacen.almacen.mappers.VentasMapper;
import com.almacen.almacen.repository.ProductoRepository;
import com.almacen.almacen.repository.VentaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class VentaServiceImpl implements VentaService{

    private final VentasMapper ventasMapper;
    private final VentaRepository ventaRepository;
    private final ProductoRepository productoRepositoy;


    @Override
    @Transactional(readOnly = true)
    public List<VentaResponse> listar() {
        log.info("Listando todas las ventas");
        return ventaRepository.findAll().stream().map(entidad-> ventasMapper.entidadResponse(entidad))
                .filter(n->n.estado().equalsIgnoreCase(EstadoVenta.REGISTRADA.toString())).toList();
    }
    @Override
    @Transactional(readOnly = true)
    public List<VentaResponse> listarCanceladas() {
        log.info("Listando todas las ventas canceladas");
        return ventaRepository.findAll().stream().map(entidad-> ventasMapper.entidadResponse(entidad))
                .filter(n->n.estado().equalsIgnoreCase(EstadoVenta.CANCELADA.toString())).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public VentaResponse obtenerPorId(Long id) {
        return ventasMapper.entidadResponse(obtenerPorIdOException(id));
    }

    @Override
    public VentaResponse registrar(VentaRequest request) {
        Ventas venta = ventasMapper.requestEntidad(request);
        log.info("Se esta registrando la venta");
        for (DetalleVenta detalleVenta: venta.getDetalleVenta()){
            Producto producto = productoRepositoy.findById(detalleVenta.getProducto().getId())
                    .orElseThrow(()->new RecursoNoEncontradoException("No se encontro el producto"));
            int cantidad = detalleVenta.getCantidad_producto();
            if (cantidad > 0){

                producto.descontarCantidad(cantidad);
                detalleVenta.setPrecio_producto(producto.getPrecio());            }
        }

        ventaRepository.save(venta);
        log.info("Se ha registrado la venta");
        return ventasMapper.entidadResponse(venta);
    }

    @Override
    public void cancelar(Long id) {
        Ventas ventas = obtenerPorIdOException(id);
        log.info("Eliminando venta con id: {}",id);
        for (DetalleVenta ventasDetalles : ventas.getDetalleVenta()){
            Producto producto = ventasDetalles.getProducto();
            producto.aumentarCantidad(ventasDetalles.getCantidad_producto());
        }
        ventas.setEstado(EstadoVenta.CANCELADA);
        ventaRepository.save(ventas);
        log.info("Se ha eliminando la venta con id: {}",id);
    }

    private Ventas obtenerPorIdOException(Long id){
        log.info("Buscando venta con id : {}",id);
        return ventaRepository.findById(id).filter(n->n.getEstado().toString().equalsIgnoreCase(EstadoVenta.REGISTRADA.toString())).orElseThrow(()-> new RecursoNoEncontradoException("No se encontro la venta con el id: "+id));
    }
}
