import  request  from "../../utils/request";

export function getPedidosClienteTable(params) {
    return request ({
        url: '/api/sys/pedidoscliente/table',
        method: 'get',
        params
    })
    
}