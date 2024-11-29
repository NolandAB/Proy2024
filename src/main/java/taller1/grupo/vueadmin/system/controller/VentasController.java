package taller1.grupo.vueadmin.system.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import taller1.grupo.vueadmin.common.exception.BadRequestException;
import taller1.grupo.vueadmin.common.utils.ResultUtil;
import taller1.grupo.vueadmin.logs.annotation.Log;
import taller1.grupo.vueadmin.system.entity.dto.QueryDto;
import taller1.grupo.vueadmin.system.service.VentasService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sys")
public class VentasController extends ResultUtil {

    private final VentasService ventasService;

    @Log("Obtener tabla de titulo y agrupacion")
    @GetMapping("/ventas/table")
    public ResponseEntity<Object> queryTituloTable (QueryDto queryDto) {
        try {
            return success(true, ventasService.queryTituloTable(queryDto));
        } catch (BadRequestException e) {
            return fail(false, e.getMsg());
        }
    }
} 
