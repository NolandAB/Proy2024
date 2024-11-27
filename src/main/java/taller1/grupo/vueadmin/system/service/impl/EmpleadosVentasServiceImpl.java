package taller1.grupo.vueadmin.system.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.RequiredArgsConstructor;
import taller1.grupo.vueadmin.system.entity.dto.EmpleadosVentasDto;
import taller1.grupo.vueadmin.system.entity.dto.QueryDto;
import taller1.grupo.vueadmin.system.service.EmpleadosVentasService;
import taller1.grupo.vueadmin.system.mapper.EmpleadosVentasMapper;;;

@Service
@RequiredArgsConstructor
public class EmpleadosVentasServiceImpl implements EmpleadosVentasService {
    private final EmpleadosVentasMapper empleadosVentasMapper;

    @Override
    public IPage<EmpleadosVentasDto> queryEmpleadosVentasTable(QueryDto queryDto) {
        Page<EmpleadosVentasDto> page = new Page<>();
        page.setCurrent(queryDto.getCurrentPage());
        page.setSize(queryDto.getSize());
        return empleadosVentasMapper.queryEmpleadosVentasTable(page, queryDto.getBlurry());
    }

}
