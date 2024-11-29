package taller1.grupo.vueadmin.system.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import taller1.grupo.vueadmin.system.entity.dto.VentasDto;

@Repository
public interface VentasMapper extends BaseMapper<VentasDto> {
    @Select("""
            SELECT
    rp."Id de pedido" AS idpedido,
    c."Nombre" AS "nombrecliente",
    c."Apellidos" AS "apellidoscliente",
    e."Nombre" AS "nombremepleado",
    e."Apellidos" AS "apellidosempleado",
    DATE (rp."Fecha de pedido") AS fechapedido,
    rp.subtotal,
    rp."Gastos de envío" AS gastosenv,
    rp."Impuestos" AS impuestos,
    rp."Total de pedido" AS totalpedido,
    p."País o región de envío" AS pais,
    dp."Id de producto" AS idproducto,
    pr."Nombre del producto" AS nombreproducto
FROM 
    northwind."Resumen de pedidos" rp
LEFT JOIN 
    northwind."Clientes" c ON rp."Id de cliente" = c."Id"
LEFT JOIN 
    northwind."Empleados" e ON rp."Id de empleado" = e."Id"
LEFT JOIN 
    northwind."Pedidos" p ON rp."Id de pedido" = p."Id de pedido"
LEFT JOIN 
    northwind."Detalles de pedido" dp ON rp."Id de pedido" = dp."Id de pedido"
LEFT JOIN 
    northwind."Productos" pr ON dp."Id de producto" = pr."Id"
ORDER BY 
    rp."Id de pedido" DESC
            """)
    IPage<VentasDto> queryTituloTable(Page<?> page, @Param("blurry") String blurry);
}