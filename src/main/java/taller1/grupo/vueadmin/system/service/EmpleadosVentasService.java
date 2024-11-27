package taller1.grupo.vueadmin.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;

import taller1.grupo.vueadmin.system.entity.dto.EmpleadosVentasDto;
import taller1.grupo.vueadmin.system.entity.dto.QueryDto;

public interface EmpleadosVentasService {
    public IPage<EmpleadosVentasDto> queryEmpleadosVentasTable(QueryDto queryDto);
}
