package taller1.grupo.vueadmin.system.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import taller1.grupo.vueadmin.common.exception.BadRequestException;
import taller1.grupo.vueadmin.common.utils.ResultUtil;
import taller1.grupo.vueadmin.logs.annotation.Log;
import taller1.grupo.vueadmin.system.entity.dto.QueryDto;
import taller1.grupo.vueadmin.system.service.EmpleadosVentasService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sys")
public class EmpleadosVentasController extends ResultUtil {
    private final EmpleadosVentasService empleadosVentasService;

    @Log("Consultar tabla")
    @GetMapping("/empleados/table")
    public ResponseEntity<Object> queryEmpleadosVentasTable(QueryDto queryDto) {
        try {
            return success(true, empleadosVentasService.queryEmpleadosVentasTable(queryDto));
        } catch (BadRequestException e) {
            return fail(false, e.getMsg());
        }
    }
    // otros metodos

}
