package taller1.grupo.vueadmin.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "\"Clientes\"", schema = "northwind")
public class Clientes {

    @TableId(value = "\"Id\"", type = IdType.AUTO)
    private Long id;

    @TableField("\"Compañía\"")
    private String compania;

    @TableField("\"Apellidos\"")
    private String apellidos;

    @TableField("\"Nombre\"")
    private String nombre;

    @TableField("\"Dirección de correo electrónico\"")
    private String correo;

    @TableField("\"Cargo\"")
    private String cargo;

    @TableField("\"Teléfono del trabajo\"")
    private String teltrabajo;

    @TableField("\"Teléfono particular\"")
    private String telparticular;

    @TableField("\"Teléfono móvil\"")
    private String telmovil;

    @TableField("\"Número de fax\"")
    private String fax;

    @TableField("\"Dirección\"")
    private String direccion;

    @TableField("\"Ciudad\"")
    private String ciudad;

    @TableField("\"Estado o provincia\"")
    private String estadoprovincia;

    @TableField("\"C Postal\"")
    private String postal;

    @TableField("\"País o región\"")
    private String pais;

    @TableField("\"Página Web\"")
    private String paginaweb;

    @TableField("\"Notas\"")
    private String notas;

    @TableField("\"Datos adjuntos\"")
    private String datosadj;
    
}
