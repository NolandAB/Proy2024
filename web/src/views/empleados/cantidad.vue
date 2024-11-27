<template>
  <q-layout view="hHh lpR fFf">
    <q-page-container>
      <div class="q-pa-md">
        <!-- Barra de búsqueda -->
        <q-table
          flat
          bordered
          ref="tableRef"
          title="Cantidad"
          :rows="state.tableData"
          :columns="columns"
          :pagination="pagination"
          :loading="loading"
          row-key="CantidadVentas"
          :filter="filter"
          :rows-per-page-options="[]" 
          binary-state-sort
          @request="onRequest"
          :visible-columns="visibleColumns"
        >
          <template v-slot:top-right>
            <q-input
              borderless
              dense
              debounce="300"
              v-model="state.blurry"
              placeholder="Buscar"
              @keyup.enter="getEmpleadosVentasTableFun"
            >
              <template v-slot:append>
                <div class="q-gutter-lg">
                  <q-btn @click="exportToExcel" style="float: right;" label="Imprimir" color="primary" />
                <q-icon name="search" @click="getEmpleadosVentasTableFun" />
                </div>
              </template>
            </q-input>
          </template>
          <template v-slot:body-cell-actions="props">
            <q-td :props="props">
              <q-btn
                @click="onEdit(props.row)"
                fab-mini
                dense
                square
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
                square
                outline
                icon="delete"
                color="negative"
                aria-label="Delete"
              />
            </q-td>
          </template>
        </q-table>
        <q-pagination
          v-model="pagination.page"
          :max="Math.ceil(state.total / state.size)"
          @update:model-value="getEmpleadosVentasTableFun"
        />
      </div>
    </q-page-container>
  </q-layout>
</template>

<script setup>
import * as XLSX from 'xlsx';
import { ref, reactive, onMounted } from 'vue';
import { getEmpleadosVentasTable } from '../../api/empleados/empleados';
import { errorMsg } from '../../utils/message';
import { date } from 'quasar';

const state = reactive({
  blurry: '',
  tableData: [],
  current: 1,
  size: 10,
  total: 0
});

const columns = [
  { name: 'fechadepedido', label: 'Fecha de pedido', align: 'center', field: 'fechadepedido', sortable: true, format: val => date.formatDate(val, 'DD-MM-YYYY') },
  { name: 'nombreDeEmpleado', label: 'Nombre de empleado', align: 'center', field: 'nombreDeEmpleado', sortable: true },
  { name: 'sumadecantidad', label: 'Importe ventas', align: 'center', field: 'sumadecantidad', sortable: true },
  { name: 'nombreDelProducto', label: 'Producto', align: 'center', field: 'nombreDelProducto', sortable: true },
  { name: 'actions', label: 'Actions', align: 'center', field: 'actions', sortable: false }
];

const exportToExcel = async () => {
  const allData = await getAllListaPedidosTable(); // Obtener todos los datos
  const ws = XLSX.utils.json_to_sheet(allData); // Convertir los datos a formato de hoja de cálculo
  const wb = XLSX.utils.book_new(); // Crear un nuevo libro de trabajo
  XLSX.utils.book_append_sheet(wb, ws, "Cantidad de ventas"); // Añadir la hoja al libro de trabajo
  
  // Generar el archivo Excel y forzar la descarga
  XLSX.writeFile(wb, "Cantidad_de_ventas.xlsx");
};
const visibleColumns = ref(['fechadepedido','nombreDeEmpleado','sumadecantidad','nombreDelProducto']);


const loading = ref(false);

const pagination = ref({
  sortBy: 'sumadecantidad',
  descending: false,
  page: 1,
  rowsPerPage: state.size,
});

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
      const res = await getEmpleadosVentasTable(params);
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
        const res = await getEmpleadosVentasTable(params);
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

const getEmpleadosVentasTableFun = () => {
  loading.value = true;

  const params = {
    blurry: state.blurry,
    size: state.size,
    currentPage: pagination.value.page,
    sortBy: pagination.value.sortBy,
    descending:pagination.value.descending
  };

  getEmpleadosVentasTable(params)
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

  state.current = page;
  getEmpleadosVentasTableFun();
};

onMounted(() => {
  getEmpleadosVentasTableFun();
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