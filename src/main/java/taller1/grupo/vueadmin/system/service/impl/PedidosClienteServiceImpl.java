package taller1.grupo.vueadmin.system.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.RequiredArgsConstructor;
import taller1.grupo.vueadmin.system.entity.dto.PedidosClienteDto;
import taller1.grupo.vueadmin.system.entity.dto.QueryDto;
import taller1.grupo.vueadmin.system.mapper.PedidosClienteMapper;
import taller1.grupo.vueadmin.system.service.PedidosClienteService;

@Service
@RequiredArgsConstructor
public class PedidosClienteServiceImpl implements PedidosClienteService {
    private final PedidosClienteMapper pedidosClienteMapper;

    @Override
    public IPage<PedidosClienteDto> queryPedidosClienteTable(QueryDto queryDto) {
        Page<PedidosClienteDto> page = new Page<>();
        page.setCurrent(queryDto.getCurrentPage());
        page.setSize(queryDto.getSize());
        return pedidosClienteMapper.queryPedidosClienteTable(page, queryDto.getBlurry());
    }

}
