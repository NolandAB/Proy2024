package taller1.grupo.vueadmin.system.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "\"Pedidos\"", schema = "northwind")
public class ListaPedidos {

    @TableId(value = "\"Id de pedido\"", type = IdType.AUTO)
    private Long id;
    
    @TableField("\"Id de cliente\"")
    private Integer idcliente;

    private String clientenombre;
    private String clienteemail;
    @TableField("\"Id de empleado\"")
    private Integer idempleado;

    @TableField("\"Id de transportista\"")
    private Integer idtransportista;
    @TableField("\"Nombre de envío\"")
    private String transportista;
    @TableField("\"Fecha de pedido\"")
    private Date fechadepedido;
    @TableField("\"Fecha de envío\"")
    private Date fechadeenvio;
    @TableField("\"Gastos de envío\"")
    private float gastosenvio;
    private float cantidad;
    private String nombreempleado;
    @TableField("\"Dirección de envío\"")
    private String direccioncliente;
    @TableField("\"Ciudad dest\"")
    private String ciudaddest;
    @TableField("\"Edo/Prov dest\"")
    private String edoprov;
    @TableField("\"Código postal de envío\"")
    private String cpostal;
    @TableField("\"País o región de envío\"")
    private String pais;
    @TableField("\"Tipo de pago\"")
    private String tipopago;
    @TableField("\"Fecha de pago\"")
    private Date fechapago;
    @TableField("\"Estado de impuestos\"")
    private String estadoimp;
    private float total;
    private String productonombre;
    private float precioproducto;
    private String nombreestado;
    
}
