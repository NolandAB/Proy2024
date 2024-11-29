package taller1.grupo.vueadmin.system.service;

import taller1.grupo.vueadmin.system.entity.dto.QueryDto;
import taller1.grupo.vueadmin.system.entity.dto.VentasDto;


import com.baomidou.mybatisplus.core.metadata.IPage;

public interface VentasService {
    
    public IPage<VentasDto> queryTituloTable(QueryDto queryDto);
}