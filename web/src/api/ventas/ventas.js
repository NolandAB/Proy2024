import request from "../../utils/request";

export function getTituloTable(params){
    return request({
        url: '/api/sys/ventas/table',
        method: 'get',
        params
    })
}