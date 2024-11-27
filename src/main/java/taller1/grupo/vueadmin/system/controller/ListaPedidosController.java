package taller1.grupo.vueadmin.system.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import taller1.grupo.vueadmin.common.exception.BadRequestException;
import taller1.grupo.vueadmin.common.utils.ResultUtil;
import taller1.grupo.vueadmin.common.utils.StringUtil;
import taller1.grupo.vueadmin.logs.annotation.Log;
import taller1.grupo.vueadmin.system.entity.ListaPedidos;
import taller1.grupo.vueadmin.system.entity.dto.ListaPedidosDto;
import taller1.grupo.vueadmin.system.entity.dto.QueryDto;
import taller1.grupo.vueadmin.system.service.ListaPedidosService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sys")
public class ListaPedidosController extends ResultUtil {
    private final ListaPedidosService listaPedidosService;

    @Log("Consultar lista pedidos")
    @GetMapping("/listapedidos/table")
    public ResponseEntity<Object> queryListaPedidosTable(QueryDto queryDto) {
        try {
            return success(true, listaPedidosService.queryListaPedidosTable(queryDto));
        } catch (BadRequestException e) {
            return fail(false, e.getMsg());
        }
    }

    @Log("Consultar lista pedidos por cliente")
    @GetMapping("/listapedidos/table2")
    public ResponseEntity<Object> queryListaPedidos2Table(QueryDto queryDto) {
        try {
            return success(true, listaPedidosService.queryListaPedidos2Table(queryDto));
        } catch (BadRequestException e) {
            return fail(false, e.getMsg());
        }
    }

    @Log("Editar pedidos")
    @PostMapping("/listapedidos/edit")
    public ResponseEntity<Object> editPedidos(@RequestBody ListaPedidos listapedidos) {
        try {
            String str = StringUtil.getEditType(listapedidos.getId());
            /*
             * if (userDto.getEnabled() == null) {
             * userDto.setEnabled(false);
             * } else {
             * userDto.setEnabled(true);
             * }
             */
            listaPedidosService.editPedidos(listapedidos);
            return success(true, str);
        } catch (BadRequestException e) {
            return fail(false, e.getMsg());
        }
    }

    @Log("eliminar pedido")
    @DeleteMapping("/listapedidos/del")
    public ResponseEntity<Object> delPedidos(Long id) {
        try {
            listaPedidosService.delPedidos(id);
            return success(true, "Eliminar Exitosamente");
        } catch (BadRequestException e) {
            return fail(false, e.getMsg());
        }
    }
    // otros metodos

}
