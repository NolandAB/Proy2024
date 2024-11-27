package taller1.grupo.vueadmin.system.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.RequiredArgsConstructor;
import taller1.grupo.vueadmin.system.entity.ListaPedidos;
import taller1.grupo.vueadmin.system.entity.dto.ListaPedidosDto;
import taller1.grupo.vueadmin.system.entity.dto.QueryDto;
import taller1.grupo.vueadmin.system.service.ListaPedidosService;
import taller1.grupo.vueadmin.system.mapper.ListaPedidosMapper;

@Service
@RequiredArgsConstructor
public class ListaPedidosServiceImpl implements ListaPedidosService {
    private final ListaPedidosMapper listaPedidosMapper;

    @Override
    public IPage<ListaPedidosDto> queryListaPedidosTable(QueryDto queryDto) {
        Page<ListaPedidosDto> page = new Page<>();
        page.setCurrent(queryDto.getCurrentPage());
        page.setSize(queryDto.getSize());
        return listaPedidosMapper.queryListaPedidosTable(page, queryDto.getBlurry());
    }

    @Override
    public IPage<ListaPedidosDto> queryListaPedidos2Table(QueryDto queryDto) {
        Page<ListaPedidosDto> page = new Page<>();
        page.setCurrent(queryDto.getCurrentPage());
        page.setSize(queryDto.getSize());
        return listaPedidosMapper.queryListaPedidos2Table(page, queryDto.getBlurry());
    }

    @Override
    public void editPedidos(ListaPedidos listaPedidos) {
        
        if (listaPedidos.getId() != null) {
            listaPedidosMapper.updateById(listaPedidos);
        }else{
            listaPedidosMapper.insert(listaPedidos);
        }
    }

    public void delPedidos(Long id) {
        listaPedidosMapper.deleteById(id);
    }

}
