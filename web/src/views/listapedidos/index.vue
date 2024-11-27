<template>
    <q-layout view="hHh lpR fFf">
      <q-page-container>       
        <div class="q-pa-md">
          <q-table
            flat
            bordered
            ref="tableRef"
            title="Lista de pedidos"
            :rows="state.tableData"
            :columns="columns"
            row-key="ListaPedidos"
            :pagination="pagination"
            :loading="loading"
            :filter="state.blurry"
            binary-state-sort
            @request="onRequest"
          >
            <template v-slot:top-right>
              <div class="q-gutter-lg">
                <q-btn @click="exportToExcel" style="float: right;" label="Imprimir" color="primary" />
                <q-btn @click="onEdit" style="float: right;"> Nuevo </q-btn>
              <q-input borderless dense debounce="300" v-model="state.blurry" placeholder="Buscar" @keyup="getListaPedidosTableFun">
                <template v-slot:append>
                  <q-icon name="search" @click="getListaPedidosTableFun" />
                </template>
              </q-input>
            </div>
            </template>
            <template v-slot:body-cell-actions="props">
              <q-td :props="props">
                <q-btn @click="detailPedidosFun(props.row)" fab-mini dense outline icon="insert_drive_file" color="orange" aria-label="Detalles" class="q-mr-sm"/>
                <q-btn @click="onEdit(JSON.parse(JSON.stringify(props.row)))" fab-mini dense  outline icon="mode_edit" color="primary" aria-label="Edit" class="q-mr-sm" />
                <q-btn @click="onDelete(props.row)" fab-mini dense  outline icon="delete" color="negative" aria-label="Delete" />
              </q-td>
            </template>

          </q-table>
           <!-- Paginación -->
          <q-pagination
            v-model="pagination.page"
            :max="Math.ceil(state.total / state.size)" 
            @update:model-value="getListaPedidosTableFun"
          />
        </div>
      </q-page-container>
       <!-- Edit y Detalles -->
       <edit-pedidos v-model:dialog-visible="dialogVisible" :pedido-obj="state.selectedPedido" @get-list="getListaPedidosTableFun"></edit-pedidos>
       <detail-pedidos v-model:dialogvisibledetailp="dialogvisibledetailp" 
  :pedidos-detail="state.detailPedidos"
  :current-index="state.currentPedidosIndex"
  :total-pedidos="state.tableData.length"
  @navigate-client="handleClientNavigation"
  @get-list="getListaPedidosTableFun"
></detail-pedidos>
    </q-layout>
  </template>
  <script setup>
  import * as XLSX from 'xlsx';
  import editPedidos from './editPedidos.vue'
  import detailPedidos from './detailPedidos.vue';
  import { ref, reactive, onMounted } from 'vue';
  import { delPedidos, getListaPedidosTable } from '../../api/listapedidos/listapedidos';
  import { errorMsg, infoMsg, successMsg } from '../../utils/message'
  import { ElMessageBox } from 'element-plus';;
  import { date } from 'quasar';

  const state = reactive({
    blurry: '',
    tableData: [],
    current: 1,
    size: 7,
    total: 0,
    detailPedidos: {},
    selectedPedido: {},
    currentPedidosIndex:0,
  });
  
  const dialogvisibledetailp = ref(false);
  const dialogVisible = ref(false);

  const columns = [
    { name: 'id', required: true, label: 'Id de  Pedido', align: 'center', field: 'id', sortable: true },
    { name: 'fechadepedido', required: true, label: 'Fecha de pedido', align: 'left', field: 'fechadepedido', sortable: true, format: val => date.formatDate(val, 'DD-MM-YYYY')   },
    { name: 'nombreestado', required: true, label: 'estado', align: 'left', field: 'nombreestado', sortable: true },
    { name: 'nombreempleado', required: true, label: 'vendedor', align: 'left', field: 'nombreempleado', sortable: true },
    { name: 'clientenombre', required: true, label: 'cliente', align: 'left', field: 'clientenombre', sortable: true },
    { name: 'fechadeenvio', required: true, label: 'fecha de envio', align: 'left', field: 'fechadeenvio', sortable: true, format: val => date.formatDate(val, 'DD-MM-YYYY')  },
    { name: 'gastosenvio', required: true, label: 'envio', align: 'left', field: 'gastosenvio', sortable: true },
    { name: 'total', required: true, label: 'Total', align: 'left', field: 'total', sortable: true },
    {name: 'actions', label: 'Detalles | Editar | Borrar', align: 'center', field: 'actions', sortable: false},
  ];

  
  const loading = ref(false);
  const pagination = ref({
    sortBy: 'id',
    descending: false,
    page: 1,
    rowsPerPage: state.size,
  });

  const exportToExcel = async () => {
  const allData = await getAllListaPedidosTable(); // Obtener todos los datos
  const ws = XLSX.utils.json_to_sheet(allData); // Convertir los datos a formato de hoja de cálculo
  const wb = XLSX.utils.book_new(); // Crear un nuevo libro de trabajo
  XLSX.utils.book_append_sheet(wb, ws, "Pedidos"); // Añadir la hoja al libro de trabajo
  
  // Generar el archivo Excel y forzar la descarga
  XLSX.writeFile(wb, "Lista_Pedidos.xlsx");
};

  const handleClientNavigation = (direction) => {
  if (direction === 'next' && state.currentPedidosIndex < state.tableData.length - 1) {
    state.currentPedidosIndex++;
    state.detailPedidos = state.tableData[state.currentPedidosIndex];
  } else if (direction === 'previous' && state.currentPedidosIndex > 0) {
    state.currentPedidosIndex--;
    state.detailPedidos = state.tableData[state.currentPedidosIndex];
  }
};
const getAllListaPedidosTable = async () => {
    loading.value = true;
    let allRecords = [];
    let currentPage = 1;
    let totalRecords = 0;

    // Obtenemos el total de registros
    const params = {
      blurry: state.blurry,
      size: state.size,
      currentPage: currentPage,
      sortBy: pagination.value.sortBy,
      descending: pagination.value.descending
    };

    try {
      const res = await getListaPedidosTable(params);
      if (res.success) {
        totalRecords = res.data.total;
        allRecords = allRecords.concat(res.data.records);
      } else {
        errorMsg(res.msg);
      }
    } catch (error) {
      console.error(error);
    }

    // Obtenemos todas las demás páginas
    const totalPages = Math.ceil(totalRecords / state.size);
    for (let page = 2; page <= totalPages; page++) {
      params.currentPage = page;
      try {
        const res = await getListaPedidosTable(params);
        if (res.success) {
          allRecords = allRecords.concat(res.data.records);
        }
      } catch (error) {
        console.error(error);
      }
    }

    loading.value = false;
    return allRecords;
};
  
  const getListaPedidosTableFun = () => {
    loading.value = true;
    const params = {
      // texto a buscar
      blurry: state.blurry,
      // tamaño 
      size: state.size,
      // Usa la página actual de la paginación
      currentPage: pagination.value.page,
      sortBy: pagination.value.sortBy,
      descending: pagination.value.descending
    };
  
    getListaPedidosTable(params)
      .then((res) => {
        loading.value = false;
        if (res.success) {
          state.tableData = res.data.records;
          state.total = res.data.total;
        } else {
          errorMsg(res.msg);
        }
      })
      .catch(() => {
        loading.value = false;
      });
  };
  
  const onRequest = (props) => {
    const { page, sortBy, descending } = props.pagination;
    pagination.value.sortBy = sortBy || '';
    pagination.value.descending = descending;
    pagination.value.page = page;
  
    state.current = page; // Actualiza el estado actual
    console.log('estado lista de pedidos');
    console.log(state);
    getListaPedidosTableFun();
    
  };

  const onEdit = (row) => {
  dialogVisible.value = true;
  state.selectedPedido = row.id ? {
    id: row.id,
    idcliente: row.idcliente,
    idempleado: row.idempleado,
    idtransportista: row.idtransportista,
    transportista: row.transportista,
    fechadepedido: row.fechadepedido,
    fechadeenvio: row.fechadeenvio,
    gastosenvio: row.gastosenvio,
    direccioncliente: row.direccioncliente,
    ciudaddest: row.ciudaddest,
    edoprov: row.edoprov,
    cpostal: row.cpostal,
    pais: row.pais,
    tipopago: row.tipopago,
    fechapago: row.fechapago,
    estadoimp: row.estadoimp
  } : {};
};


  // Actualizar la función detailClientesFun
  const detailPedidosFun = (row) => {
  if (!row || !row.id) {
    console.error('Fila inválida o ID:', row);
    return;
  }
  // Encontrar el índice del cliente seleccionado
  state.currentPedidosIndex = state.tableData.findIndex(pedido => pedido.id === row.id);
  state.detailPedidos = { ...row };
  dialogvisibledetailp.value = true;
};

async function onDelete(props) {
    ElMessageBox.confirm('Confirmar Eliminar el pedido ' + props.id + '?', {
      confirmButtonText: 'Seguro',
      cancelButtonText: 'Cancelar',
      type: 'warning'
    }).then(() => {
      delPedidos({
        id: props.id,
        idcliente: props.idcliente,
        idempleado: props.idempleado,
        idtransportista: props.idtransportista,
        transportista: props.transportista,
        fechadepedido: props.fechadepedido,
        fechadeenvio: props.fechadeenvio,
        gastosenvio: props.gastosenvio,
        direccioncliente: props.direccioncliente,
        ciudaddest: props.ciudaddest,
        edoprov: props.edoprov,
        cpostal: props.cpostal,
        pais: props.pais,
        tipopago: props.tipopago,
        fechapago: props.fechapago,
        estadoimp: props.estadoimp
      }).then(res => {
        if (res.success) {
          successMsg(res.data)
          getListaPedidosTableFun();
        } else {
          errorMsg(res.msg)
        }
      })
    }).catch(() => {
      infoMsg('Operacion Cancelada')
    })
  }
  onMounted(() => {
    getListaPedidosTableFun();
    console.log('estado pedidos');
    console.log(state);
  });
  </script>
  