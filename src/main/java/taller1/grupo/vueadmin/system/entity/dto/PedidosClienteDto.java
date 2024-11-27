package taller1.grupo.vueadmin.system.entity.dto;

import lombok.Setter;

import java.util.Date;

import lombok.Getter;

@Setter
@Getter
public class PedidosClienteDto {
    private Integer idcliente;
    private Integer idpedido;
    private Date fechadepedido;
    private Date fechadeenvio;
    private float impuestos;
    private float gastosenvio;
    private float subtotal;
    private float preciototal;
}
