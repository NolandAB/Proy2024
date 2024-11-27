package taller1.grupo.vueadmin.system.entity.dto;

import lombok.Setter;

import java.util.Date;

import lombok.Getter;

@Setter
@Getter
public class ListaPedidosDto {
    private Long id;
    private Integer idcliente;
    private Integer idempleado;
    private String clientenombre;
    private String clienteemail;
    private Integer idtransportista;
    private String transportista;
    private Date fechadepedido;
    private Date fechadeenvio;
    private float gastosenvio;
    private float cantidad;
    private String nombreempleado;
    private String direccioncliente;
    private String ciudaddest;
    private String edoprov;
    private String cpostal;
    private String pais;
    private String tipopago;
    private Date fechapago;
    private String estadoimp;
    private float total;
    private String productonombre;
    private float precioproducto;
    private String nombreestado;

}
