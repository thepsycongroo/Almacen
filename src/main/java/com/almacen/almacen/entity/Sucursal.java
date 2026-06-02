package com.almacen.almacen.entity;


import com.almacen.almacen.utils.StringCustomUtils;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter @Setter
@Table(name = "SUCURSALES")
public class Sucursal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_SUCURSAL")
    private Long id;

    @Column(name= "NOMBRE", length = 50, unique = true, nullable = false)
    private String nombre;

    @Column(name="DIRECCION", length = 150, nullable = false)
    private String direccion;


    public void actualizar(String nombre, String direccion){
        // se puede dejar solo StringCustomUtils.validarTamanio(nombre,5,50,"mensaje")
        StringCustomUtils.validarNoVacio(nombre,"El nombre es requerido");
        StringCustomUtils.validarTamanio(nombre,5,50,"El nombre es requerido y debe tener un tamaño de 5 a 50 caracteres ");
        StringCustomUtils.validarNoVacio(direccion,"La direccion es requerido");
        StringCustomUtils.validarTamanio(direccion,10,150,"La direccion es requerida y debe tener un tamaño de 10 a 150 caracteres ");

        this.nombre = nombre.trim();
        this.direccion = direccion.trim();
    }


}
