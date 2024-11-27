package taller1.grupo.vueadmin.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;

import taller1.grupo.vueadmin.system.entity.dto.PedidosClienteDto;
import taller1.grupo.vueadmin.system.entity.dto.QueryDto;

public interface PedidosClienteService {
    public IPage<PedidosClienteDto> queryPedidosClienteTable(QueryDto queryDto);

}
