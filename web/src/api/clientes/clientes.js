import request from "../../utils/request";

/**
 * Obtener lista de registro
 * @param params
 * @returns {AxiosPromise}
 */
export function getClientesList(params){
    return request({
        //url: '/api/sys/clientes/list',
        url: '/api/sys/clientes/table',
        method: 'get',
        params
    })
}
/**
 * Obtener lista de registro
 * @param data
 * @returns {AxiosPromise}
 */
export function editClientes(data){
    return request({
        url: '/api/sys/clientes/edit',
        method: 'post',
        data
    })
}
/**
 * Eliminar cliente según ID
 * @param params
 * @returns {AxiosPromise}
 */
export function delCliente(params){
    return request({
        url: '/api/sys/clientes/del',
        method: 'delete',
        params
    })
}