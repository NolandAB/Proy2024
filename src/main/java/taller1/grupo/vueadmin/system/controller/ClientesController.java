package taller1.grupo.vueadmin.system.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import taller1.grupo.vueadmin.common.exception.BadRequestException;
import taller1.grupo.vueadmin.common.utils.ResultUtil;
import taller1.grupo.vueadmin.common.utils.StringUtil;
import taller1.grupo.vueadmin.logs.annotation.Log;
import taller1.grupo.vueadmin.system.entity.dto.QueryDto;
import taller1.grupo.vueadmin.system.entity.dto.ClientesDto;
import taller1.grupo.vueadmin.system.entity.Clientes;
import taller1.grupo.vueadmin.system.service.ClientesService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sys")
public class ClientesController extends ResultUtil {

    private final ClientesService clientesService;

    @Log("Obtener lista de clientes")
    @GetMapping("/clientes/list")
    public ResponseEntity<Object> getClientesList(String blurry) {
        try {
            return success(true, clientesService.getClientesList(blurry));
        } catch (BadRequestException e) {
            return fail(false, e.getMsg());
        }
    }

    @Log("Consultar lista de clientes dto")
    @GetMapping("/clientes/table")
    public ResponseEntity<Object> queryClientesTable(QueryDto queryDto) {
        try {
            return success(true, clientesService.queryClientesTable(queryDto));
        } catch (BadRequestException e) {
            return fail(false, e.getMsg());
        }
    }


    @Log("Editar clientes")
    @PostMapping("/clientes/edit")
    public ResponseEntity<Object> editClientes(@RequestBody Clientes clientes) {
        try {
            String str = StringUtil.getEditType(clientes.getId());
            /*
             * if (userDto.getEnabled() == null) {
             * userDto.setEnabled(false);
             * } else {
             * userDto.setEnabled(true);
             * }
             */
            clientesService.editClientes(clientes);
            return success(true, str);
        } catch (BadRequestException e) {
            return fail(false, e.getMsg());
        }
    }
    @Log("eliminar cliente")
    @DeleteMapping("/clientes/del")
    public ResponseEntity<Object> delCliente(Long id) {
        try {
            clientesService.delCliente(id);
            return success(true, "Eliminar Exitosamente");
        } catch (BadRequestException e) {
            return fail(false, e.getMsg());
        }
    }

}
