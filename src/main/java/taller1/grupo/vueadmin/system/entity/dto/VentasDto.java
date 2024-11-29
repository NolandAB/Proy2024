package taller1.grupo.vueadmin.system.entity.dto;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class VentasDto {
    private Integer idpedido;
    private String nombrecliente;
    private String apellidoscliente;
    private String nombremepleado;
    private String apellidosempleado;
    private Date fechapedido;
    private Integer subtotal;
    private Integer gastosenv;
    private Integer impuestos;
    private Integer totalpedido;
    private String pais;
    private Integer idproducto;
    private String nombreproducto;
}
