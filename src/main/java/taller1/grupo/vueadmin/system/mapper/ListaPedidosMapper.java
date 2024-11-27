package taller1.grupo.vueadmin.system.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import taller1.grupo.vueadmin.system.entity.*;


import taller1.grupo.vueadmin.system.entity.dto.ListaPedidosDto;

@Repository
public interface ListaPedidosMapper extends BaseMapper<ListaPedidos> {
    @Select("""
    SELECT 
    p."Id de pedido" AS id,
    p."Id de cliente" AS idcliente,
    p."Id de empleado" AS idempleado,
    c."Nombre" AS clientenombre,
    c."Dirección de correo electrónico" AS clienteemail,
    p."Id de transportista" AS idtransportista,
    t."Compañía" AS transportista,
    DATE (p."Fecha de pedido") AS fechadepedido,
    DATE (p."Fecha de envío") AS fechadeenvio,
    p."Gastos de envío" AS gastosenvio,
    e."Nombre" AS nombreempleado,
    p."Dirección de envío" AS direccioncliente,
    p."Ciudad dest" AS ciudaddest,
    p."Edo/Prov dest" AS edoprov,
    p."Código postal de envío" AS cpostal,
    p."País o región de envío" AS pais,
    p."Tipo de pago" AS tipopago,
    DATE(p."Fecha de pago") AS fechapago,
    p."Estado de impuestos" AS estadoimp,
    tp."Precio total" AS total,
    ep."Nombre del estado" AS nombreestado
FROM 
    northwind."Pedidos" p
JOIN 
    northwind."Clientes" c ON p."Id de cliente" = c."Id"
JOIN 
    northwind."Transportistas" t ON p."Id de transportista" = t."Id"
JOIN 
    northwind."Totales de precios de pedidos" tp ON p."Id de pedido" = tp."IdPedido"
JOIN 
    northwind."Empleados" e ON p."Id de empleado" = e."Id"
JOIN
    northwind."Estado de pedidos" ep ON p."Id de situación" = ep."Id de situación"
WHERE 
    p."Id de pedido"::TEXT ILIKE CONCAT('%', #{blurry}, '%') OR
    c."Nombre" ILIKE CONCAT('%', #{blurry}, '%') OR
    e."Nombre" ILIKE CONCAT('%', #{blurry}, '%') OR
    t."Compañía" ILIKE CONCAT('%', #{blurry}, '%') OR
    p."País o región de envío" ILIKE CONCAT('%', #{blurry}, '%') OR
    p."Ciudad dest" ILIKE CONCAT('%', #{blurry}, '%')
ORDER BY 
    p."Id de pedido"
    """)
IPage<ListaPedidosDto> queryListaPedidosTable(Page<?> page, @Param("blurry") String blurry);


    @Select("""
        SELECT 
    p."Id de pedido" AS id,
    p."Id de cliente" AS idcliente,
    c."Nombre" AS clientenombre,
    c."Dirección de correo electrónico" AS clienteemail,
    p."Id de transportista" AS idtransportista,
    t."Compañía" AS transportista,
    DATE (p."Fecha de pedido") AS fechadepedido,
    DATE (p."Fecha de envío") AS fechadeenvio,
    p."Gastos de envío" AS gastosenvio,
    dp."Cantidad" AS cantidad,
    e."Nombre" AS nombreempleado,
    p."Dirección de envío" AS direccioncliente,
    p."Ciudad dest" AS ciudaddest,
    p."Edo/Prov dest" AS edoprov,
    p."Código postal de envío" AS cpostal,
    p."País o región de envío" AS pais,
    p."Tipo de pago" AS tipopago,
    DATE(p."Fecha de pago") AS fechapago,
    p."Estado de impuestos" AS estadoimp,
    tp."Precio total" AS total,
    pr."Nombre del producto" AS productonombre,
    pr."Precio listado" AS precioproducto,
	ep."Nombre del estado" AS nombreestado
FROM 
    northwind."Pedidos" p
JOIN 
    northwind."Clientes" c ON p."Id de cliente" = c."Id"
JOIN 
    northwind."Transportistas" t ON p."Id de transportista" = t."Id"
JOIN 
    northwind."Detalles de pedido" dp ON p."Id de pedido" = dp."Id de pedido"
JOIN 
    northwind."Productos" pr ON dp."Id de producto" = pr."Id"
JOIN 
    northwind."Totales de precios de pedidos" tp ON p."Id de pedido" = tp."IdPedido"
JOIN 
	northwind."Empleados" e ON p."Id de empleado" = e."Id"
JOIN
	northwind."Estado de pedidos" ep ON p."Id de situación" = ep."Id de situación"
ORDER BY 
    p."Id de pedido"
            
            """)
    IPage<ListaPedidosDto> queryListaPedidos2Table(Page<?> page, @Param("blurry") String blurry);


}
