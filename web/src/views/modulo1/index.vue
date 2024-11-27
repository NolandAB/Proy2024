<template>
    <q-layout view="hHh lpR fFf">
      <q-page-container>       
        <div class="q-pa-md">
          <q-table
          color="primary"
      card-class="bg-amber-5 text-brown"
      table-class="text-grey-8"
      table-header-class="text-brown"
            flat
            bordered
            ref="tableRef"
            title="Diez Mejores Pedidos por importe de ventas"
            :rows="state.tableData"
            :columns="columns"
            row-key="Pedido"
            :pagination="pagination"
            :loading="loading"
            :filter="state.blurry"
            binary-state-sort
            @request="onRequest"
           
          >
            <template v-slot:top-right>
              <div class="q-gutter-lg">
                <q-btn @click="exportToExcel" style="float: right;" label="Imprimir" color="primary" />

              <q-input borderless dense debounce="300" v-model="state.blurry" placeholder="Buscar">
                <template v-slot:append>
                  <q-icon name="search" @click="getModulo1TableFun" />
                </template>
              </q-input>
              </div>
            </template>
            <template v-slot:body-cell-actions="props">
              <q-td :props="props">
                <q-btn @click="onDelete(props.row)" fab-mini dense square outline icon="delete" color="negative" aria-label="Delete" />
              </q-td>
            </template>

          </q-table>
          <q-pagination
            v-model="pagination.page"
            :max="Math.ceil(state.total / pagination.rowsPerPage)" 
            @update:model-value="getModulo1TableFun"
          />
        </div>
      </q-page-container>
    </q-layout>
  </template>
  <script setup>
    import * as XLSX from 'xlsx';
  import { ref, reactive, onMounted } from 'vue';
  import {  delModulo1, getModulo1Table } from '../../api/modulo1/modulo1';
  import { errorMsg, infoMsg, successMsg } from '../../utils/message';
  import { ElMessageBox } from 'element-plus';

  import { date } from 'quasar';
 


  const state = reactive({
    blurry: '',
    tableData: [],
    current: 1,
    size: 10,
    total: 0
  });
  

  const columns = [
    { name: 'idpedido', 
    required: true, 
    label: 'Mi Id de  Pedido', 
    align: 'center', 
    field: 'idpedido', 
    sortable: true },

    { name: 'Fecha de pedido', 
    required: true, 
    label: 'Fecha de pedido', 
    align: 'left', 
    field: 'fechapedido', 
    sortable: true, format: val => date.formatDate(val, 'DD-MM-YYYY')   },

    { name: 'fechaenvio', required: true, label: 'fechaenvio', align: 'left', field: 'fechaenvio', sortable: true, format: val => date.formatDate(val, 'DD-MM-YYYY')  },
    { name: 'nombrecompania', required: true, label: 'Nombre de \la Compañia', align: 'left', field: 'nombrecompania', sortable: true },
    { name: 'importeventas', required: true, label: 'importeventas', align: 'left', field: 'importeventas', sortable: true },
  ];

  
  const loading = ref(false);
  const pagination = ref({
    sortBy: 'nombre',
    descending: false,
    page: 1,
    rowsPerPage: 10,
  });
  const exportToExcel = () => {
  // Crea un libro de trabajo
  const wb = XLSX.utils.book_new();

  // Convierte los datos de la tabla en un formato adecuado para Excel
  const ws = XLSX.utils.json_to_sheet(state.tableData);

  // Agrega la hoja de trabajo al libro de trabajo
  XLSX.utils.book_append_sheet(wb, ws, "Diez mejores pedidos");

  // Genera y descarga el archivo Excel
  XLSX.writeFile(wb, "Diez_mejores_pedidos.xlsx");
};
  
  const getModulo1TableFun = () => {
    loading.value = true;
    const params = {
      // texto a buscar
      blurry: state.blurry,
      // tamaño 
      size: state.size,
      // Usa la página actual de la paginación
      currentPage: pagination.value.page 
    };
  
    getModulo1Table(params)
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
    const { page, rowsPerPage, sortBy, descending } = props.pagination;
    pagination.value.sortBy = sortBy || '';
    pagination.value.descending = descending;
    pagination.value.page = page;
    pagination.value.rowsPerPage = rowsPerPage;
  
    state.current = page; // Actualiza el estado actual
    state.size = rowsPerPage; // Actualiza el tamaño de la página
    console.log('estado current cliente');
    console.log(state);
    getModulo1TableFun();
    
  };

  async function onDelete(props) {
    console.log(props.idpedido)
    ElMessageBox.confirm('Confirmar Eliminar el mejor pedido de ' + props.nombrecompania +'?',{
      confirmButtonText: 'Seguro',
      cancelButtonText: 'Cancelar',
      type: 'warning'
    }).then(() =>{
      delModulo1({idpedido: props.idpedido, fechapedido: props.fechapedido, 
        fechaenvio: props.fechaenvio, nombrecompania: props.nombrecompania, importeventas: props.importeventas
      }).then(res =>{
        if (res.success){
          successMsg(res.data)
          getModulo1TableFun()
        } else{
          errorMsg(res.msg)
        }
      })
    }).catch(()=>{
      infoMsg('Operacion Cancelada')
    })
  }
  
  onMounted(() => {
    getModulo1TableFun();
    console.log('estado modulo1');
    console.log(state);
  });
  </script>
  