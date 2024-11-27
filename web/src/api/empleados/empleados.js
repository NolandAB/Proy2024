import  request  from "../../utils/request";

export function getEmpleadosVentasTable(params) {
    return request ({
        url: '/api/sys/empleados/table',
        method: 'get',
        params
    })
    
}