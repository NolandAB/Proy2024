import request from "../../utils/request";

export function getListaPedidosTable(params){
    return request({
        url: '/api/sys/listapedidos/table',
        method: 'get',
        params
    })
}

export function getListaPedidos2Table(params){
    return request({
        url: '/api/sys/listapedidos/table2',
        method: 'get',
        params
    })
}

/**
 * Obtener lista de registro
 * @param data
 * @returns {AxiosPromise}
 */
export function editPedidos(data){
    return request({
        url: '/api/sys/listapedidos/edit',
        method: 'post',
        data
    })
}

/**
 * Eliminar pedido según ID
 * @param params
 * @returns {AxiosPromise}
 */
export function delPedidos(params){
    return request({
        url: '/api/sys/listapedidos/del',
        method: 'delete',
        params
    })
}