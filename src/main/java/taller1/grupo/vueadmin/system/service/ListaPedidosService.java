package taller1.grupo.vueadmin.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;

import taller1.grupo.vueadmin.system.entity.ListaPedidos;
import taller1.grupo.vueadmin.system.entity.dto.ListaPedidosDto;
import taller1.grupo.vueadmin.system.entity.dto.QueryDto;

public interface ListaPedidosService {
    public IPage<ListaPedidosDto> queryListaPedidosTable(QueryDto queryDto);

    public IPage<ListaPedidosDto> queryListaPedidos2Table(QueryDto queryDto);

    void editPedidos(ListaPedidos listaPedidos);

    void delPedidos(Long id);
}
