<template>
  <q-layout view="hHh lpR fFf">
    <q-page-container>
      <div class="q-pa-md">

        <!-- Tabla de datos -->
        <q-table
          flat
          bordered
          ref="tableRef2"
          title="Lista de Clientes"
          :rows="state.tableData"
          :columns="columns"
          row-key="id"
          :pagination="pagination"
          :loading="loading"
          :filter="filter"
          binary-state-sort
          @request="onRequest"
          :visible-columns="visibleColumns"
        >
          <template v-slot:top-right>
            <!--Boton de Nuevo-->
            <div class="q-gutter-lg">
              <q-btn  @click="editClientesFun" style="float: right;"> Nuevo </q-btn>
            <!--Boton de Busqueda Search-->
            <q-input
              borderless
              dense
              debounce="300"
              v-model="state.blurry"
              placeholder="Buscar"
              @keyup.enter="getClientesListFun"
            >
              <template v-slot:append>
                <q-icon name="search" @click="getClientesListFun " />
              </template>
            </q-input>
            </div>
          </template>

          <!--Botones-->
          <template v-slot:body-cell-actions="props">
            <q-td :props="props">
              <q-btn @click="detailClientesFun(props.row)" 
              fab-mini 
              dense 
              outline 
              icon="insert_drive_file"
              color="orange" 
              aria-label="Detalles"
              class="q-mr-sm">
              </q-btn>
              <q-btn
               
                @click="editClientesFun(JSON.parse(JSON.stringify(props.row)))"
                fab-mini
                dense
                outline
                icon="mode_edit"
                color="primary"
                aria-label="Edit"
                class="q-mr-sm"
              />
              <q-btn
              
                @click="onDelete(props.row)"
                fab-mini
                dense
                outline
                icon="delete"
                color="negative"
                aria-label="Delete"
              />
            </q-td>
          </template>
        </q-table>

        <!-- Paginación -->
        <q-pagination
          v-model="pagination.page"
          :max="Math.ceil(state.total / state.size)"
          @update:model-value="getClientesListFun"
        />
      </div>
    </q-page-container>
    <edit-clientes v-model:dialog-visible="dialogVisible" :client-obj="state.selectedCliente" @get-list="getClientesListFun"></edit-clientes>
    <detail-clientes 
  v-model:dialogvisibledetail="dialogvisibledetail" 
  :clientes-detail="state.detailCliente"
  :current-index="state.currentClientIndex"
  :total-clients="state.tableData.length"
  @navigate-client="handleClientNavigation"
  @get-list="getClientesListFun"
></detail-clientes>
  </q-layout>

</template>

<script setup>
import detailClientes from './detailClientes.vue';
import editClientes from './editClientes.vue';
import { ref, reactive, onMounted } from 'vue';
import { delCliente, getClientesList } from '../../api/clientes/clientes';
import { errorMsg, infoMsg, successMsg } from '../../utils/message';
import { ElMessageBox } from 'element-plus';
import { id } from 'element-plus/es/locale/index.mjs';
// Estado reactivo
const state = reactive({
  blurry: '',
  tableData: [],
  current: 1,
  size: 7, // Tamaño fijo de página
  total: 0,
  selectedCliente: {},
  detailCliente: {},
  currentClientIndex:0,
});
const dialogvisibledetail = ref(false);
const dialogVisible = ref(false);

const columns = [
  { name: 'id', required: true, label: 'Id', align: 'center', field: 'id', sortable: true },
  { name: 'compania', required: true, label: 'Compañía', align: 'left', field: 'compania', sortable: true },
  { name: 'apellidos', required: true, label: 'Apellidos', align: 'left', field: 'apellidos', sortable: true },
  { name: 'nombre', required: true, label: 'Nombre', align: 'left', field: 'nombre', sortable: true },
  { name: 'correo', required: true, label: 'Direccion Correo', align: 'left', field: 'correo', sortable: true },
  { name: 'teltrabajo', required: true, label: 'Telefono Trabajo', align: 'left', field: 'teltrabajo', sortable: true },
  { name: 'cargo', required: true, label: 'Cargo', align: 'left', field: 'cargo', sortable: true },
  { name: 'actions', label: 'Detalles  |  Editar  |  Borrar', align: 'center', field: 'actions', sortable: false}
];

const loading = ref(false);

// Configuración inicial de la paginación
const pagination = ref({
  sortBy: 'id',
  descending: false,
  page: 1,
  rowsPerPage: state.size, // Tamaño fijo de la página
});

// Función para manejar la navegación entre clientes
const handleClientNavigation = (direction) => {
  if (direction === 'next' && state.currentClientIndex < state.tableData.length - 1) {
    state.currentClientIndex++;
    state.detailCliente = state.tableData[state.currentClientIndex];
  } else if (direction === 'previous' && state.currentClientIndex > 0) {
    state.currentClientIndex--;
    state.detailCliente = state.tableData[state.currentClientIndex];
  }
};

// Obtener lista de clientes
const getClientesListFun = () => {
  loading.value = true;

  const params = {
    blurry: state.blurry,
    size: state.size,
    currentPage: pagination.value.page,
    sortBy: pagination.value.sortBy,
    descending:pagination.value.descending
  };

  getClientesList(params)
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

// Manejar eventos de solicitud
const onRequest = (props) => {
  const { page, sortBy, descending } = props.pagination;

  pagination.value.sortBy = sortBy || '';
  pagination.value.descending = descending;
  pagination.value.page = page;

  state.current = page; // Actualizar el estado
  getClientesListFun();
};
const editClientesFun = (row) => {
  dialogVisible.value = true;
  state.selectedCliente = row.id ? row :{}
};

// Actualizar la función detailClientesFun
const detailClientesFun = (row) => {
  if (!row || !row.id) {
    console.error('Fila inválida o ID:', row);
    return;
  }
  // Encontrar el índice del cliente seleccionado
  state.currentClientIndex = state.tableData.findIndex(client => client.id === row.id);
  state.detailCliente = { ...row };
  dialogvisibledetail.value = true;
};

async function onDelete(props) {
    console.log(props.id)
    ElMessageBox.confirm('Confirmar Eliminar Al cliente ' + props.nombre +' '+ props.apellidos + '?',{
      confirmButtonText: 'Seguro',
      cancelButtonText: 'Cancelar',
      type: 'warning'
    }).then(() =>{
      delCliente({id: props.id}).then(res =>{
        if (res.success){
          successMsg(res.data)
          getClientesListFun()
        } else{
          errorMsg(res.msg)
        }
      })
    }).catch(()=>{
      infoMsg('Operacion Cancelada')
    })
  }
const visibleColumns = ref(['Id','Compañia','Apellidos','Nombre','actions']);


// Cargar datos iniciales
onMounted(() => {
  getClientesListFun();
});
</script>

<style scoped>
.custom-button {
  border: 2px solid #1976D2;
  color: white;
  background-color: #1976D2;
  border-radius: 4px;
  padding: 10px 20px;
  transition: all 0.3s ease; /* Efecto de transición suave */
}

.custom-button:hover {
  background-color: #1565C0; /* Fondo más oscuro */
  border-color: #1565C0; /* Borde más oscuro */
  color: white; /* Texto blanco */
  cursor: pointer; /* Indicador de clic */
}
</style>
