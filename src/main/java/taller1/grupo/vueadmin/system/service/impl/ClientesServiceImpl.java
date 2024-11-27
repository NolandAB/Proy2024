package taller1.grupo.vueadmin.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import taller1.grupo.vueadmin.common.exception.BadRequestException;
import taller1.grupo.vueadmin.common.utils.StringUtil;
import taller1.grupo.vueadmin.constant.CommonConstants;
import taller1.grupo.vueadmin.system.entity.Clientes;
import taller1.grupo.vueadmin.system.entity.dto.QueryDto;
import taller1.grupo.vueadmin.system.entity.dto.ClientesDto;
import taller1.grupo.vueadmin.system.mapper.ClientesMapper;
import taller1.grupo.vueadmin.system.service.ClientesService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientesServiceImpl implements ClientesService {
    private final ClientesMapper clientesMapper;

    @Override
    public List<Clientes> getClientesList(String blurry) {
        LambdaQueryWrapper<Clientes> wrapper = new LambdaQueryWrapper<>();

        if (StringUtils.isNotBlank(blurry)) {
            wrapper.like(Clientes::getNombre, blurry);
            wrapper.or();
            wrapper.like(Clientes::getApellidos, blurry);
        }
        wrapper.ne(Clientes::getNombre, CommonConstants.ROLE_ADMIN);

        return clientesMapper.selectList(wrapper);
    }

    @Override
    public IPage<ClientesDto> queryClientesTable(QueryDto queryDto) {
        Page<ClientesDto> page = new Page<>();
        page.setCurrent(queryDto.getCurrentPage());
        page.setSize(queryDto.getSize());
        return clientesMapper.queryClientesTable(page, queryDto.getBlurry());
    }

    private void checkClientes(ClientesDto clientesDto) {
        LambdaQueryWrapper<Clientes> wrapper = new LambdaQueryWrapper<>();

        if (clientesDto.getId() != null) {
            wrapper.ne(Clientes::getId, clientesDto.getId());
        }
        if (StringUtil.isNotBlank(clientesDto.getNombre()) && StringUtil.isNotBlank(clientesDto.getApellidos())) {
            wrapper.and(w -> w.eq(Clientes::getNombre, clientesDto.getNombre()).or().eq(Clientes::getApellidos,
                    clientesDto.getApellidos()));
        }
        long count = clientesMapper.selectCount(wrapper);
        if (count > 0) {
            throw new BadRequestException("El nombre de cliente Por favor vuelve a entrar");
        }

    }
    /**
     * @param clientes
     * @Description: Editar menú
     * @Param: [clientes]
     * @return: void
     * @Author: richard sivila
     * @Date: 2021/11/27
     */

    @Override
    public void editClientes(Clientes clientes) {
        
        if (clientes.getId() != null) {
            clientesMapper.updateById(clientes);
        }else{
            clientesMapper.insert(clientes);
        }
    }

    /**
     * @param id
     * @Description: Eliminar el enlace entre usuario y rol
     * @Param: [id]
     * @return: void
     * @Author: richard sivila
     * @Date: 2024
     */
    @Override
    public void delCliente(Long id) {
        clientesMapper.deleteById(id);
    }
    


}
