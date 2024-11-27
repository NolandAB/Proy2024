package taller1.grupo.vueadmin.system.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import taller1.grupo.vueadmin.system.entity.dto.EmpleadosVentasDto;

@Repository
public interface EmpleadosVentasMapper extends BaseMapper<EmpleadosVentasDto> {
    @Select("""
    SELECT 
        DATE("Fechadepedido") AS fechadepedido,
        "NombreDeEmpleado" AS nombreDeEmpleado,
        ROUND("SumaDeCantidad", 2) AS sumadecantidad,
        "NombreDelProducto" AS nombreDelProducto
    FROM 
        northwind."Cantidad de ventas de productos por empleado"
    WHERE
        "NombreDeEmpleado" ILIKE CONCAT('%', #{blurry}, '%') OR
        "NombreDelProducto" ILIKE CONCAT('%', #{blurry}, '%')
""")
IPage<EmpleadosVentasDto> queryEmpleadosVentasTable(Page<?> page, @Param("blurry") String blurry);

    
}
