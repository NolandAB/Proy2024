package taller1.grupo.vueadmin.system.service.impl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import taller1.grupo.vueadmin.system.entity.dto.QueryDto;
import taller1.grupo.vueadmin.system.entity.dto.VentasDto;
import taller1.grupo.vueadmin.system.mapper.VentasMapper;
import taller1.grupo.vueadmin.system.service.VentasService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor

public class VentasServiceImpl implements VentasService {
    private final VentasMapper ventasMapper;

    @Override
    public IPage<VentasDto> queryTituloTable(QueryDto queryDto) {
        Page<VentasDto> page = new Page<>();
        page.setCurrent(queryDto.getCurrentPage());
        page.setSize(queryDto.getSize());
        return ventasMapper.queryTituloTable(page, queryDto.getBlurry());
    }
}