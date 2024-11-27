package taller1.grupo.vueadmin.system.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import taller1.grupo.vueadmin.system.entity.dto.PedidosClienteDto;

@Repository
public interface PedidosClienteMapper extends BaseMapper<PedidosClienteDto> {
    @Select("""
        SELECT 
    c."Id" AS "idcliente",
    p."Id de pedido" AS idpedido,
    DATE(p."Fecha de pedido") AS fechadepedido,
	DATE(p."Fecha de envío") AS fechadeenvio,
    p."Impuestos" AS impuestos,
    p."Gastos de envío" AS gastosenvio,
    s."subtotal" AS subtotal,
    t."Precio total" AS preciototal
FROM 
    northwind."Clientes" c
LEFT JOIN 
    northwind."Pedidos" p ON c."Id" = p."Id de cliente"
LEFT JOIN 
    northwind."Subtotales de pedidos" s ON p."Id de pedido" = s."Id de pedido"
LEFT JOIN 
    northwind."Totales de precios de pedidos" t ON p."Id de pedido" = t."IdPedido"
ORDER BY 
    c."Id", p."Id de pedido"
    """)
    IPage<PedidosClienteDto> queryPedidosClienteTable(Page<?> page, @Param("blurry") String blurry);
}
