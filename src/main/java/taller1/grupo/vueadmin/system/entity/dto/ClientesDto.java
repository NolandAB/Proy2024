package taller1.grupo.vueadmin.system.entity.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @program: tarija
 * @description: this is a class
 * @author: richard sivila
 * @create: 2024
 **/
@Setter
@Getter
public class ClientesDto {
    private Long id;
    private String compania;
    private String apellidos;
    private String nombre;
    private String correo;
    private String cargo;
    private String teltrabajo;
    private String telparticular;
    private String telmovil;
    private String fax;
    private String direccion;
    private String ciudad;
    private String estadoprovincia;
    private String postal;
    private String pais;
    private String paginaweb;
    private String notas;
    private String datosadj;
}
