<template>
  <q-layout view="hHh lpR fFf">
    <q-page-container padding>
      <q-card class="q-pa-md" flat bordered>
        <q-card-section>
          <div class="text-h6 text-center">Informes de ventas</div>
        </q-card-section>
  
        <q-card-section>
          <div class="row q-gutter-md">
            <!-- Selection Section (Left) -->
            <div class="col-4">
              <q-card flat bordered>
                <q-card-section>
                  <div class="text-subtitle1">Seleccionar informe de ventas</div>
                </q-card-section>
                <q-list bordered dense>
                  <q-item 
                    v-for="(informe, index) in informes" 
                    :key="index" 
                    clickable
                  >
                    <q-item-section>
                      <q-radio
                        v-model="selectedInforme"
                        :val="informe.id"
                        color="primary"
                      >
                        {{ informe.titulo }}
                      </q-radio>
                    </q-item-section>
                  </q-item>
                </q-list>
                <q-card-section>
                  <div class="text-subtitle1">Seleccionar período de ventas</div>
                </q-card-section>
                <q-list bordered dense>
                  <q-item 
                    v-for="(periodo, index) in periodos" 
                    :key="index" 
                    clickable
                  >
                    <q-item-section>
                      <q-radio
                        v-model="selectedPeriodo"
                        :val="periodo.id"
                        color="primary"
                      >
                        {{ periodo.titulo }}
                      </q-radio>
                    </q-item-section>
                  </q-item>
                </q-list>
              </q-card>
            </div>

            <!-- Table Section (Right) -->
            <div class="col-4">
              <q-card flat bordered>
                <q-table
                  flat
                  bordered
                  :rows="filteredTableData"
                  :columns="columns"
                  row-key="id"
                  :pagination="pagination"
                  :loading="loading"
                  binary-state-sort
                >
                  <template v-slot:no-data>
                    <div class="full-width row flex-center text-accent q-gutter-sm">
                      <span>
                        Selecciona un informe y período para mostrar datos
                      </span>
                    </div>
                  </template>
                </q-table>
              </q-card>
            </div>
          </div>
        </q-card-section>

        <q-card-section class="text-right">
          <q-btn 
            label="Vista previa" 
            color="secondary" 
            class="q-mr-sm" 
            @click="applyFilter"
          />
          <q-btn label="Cerrar" color="negative" />
        </q-card-section>
      </q-card>
    </q-page-container>
  </q-layout>
</template>

<script setup>
import { getTituloTable } from '../../api/ventas/ventas';
import { ref, reactive, computed } from 'vue';
import { errorMsg } from '../../utils/message';

const state = reactive({
  tableData: [],
  total: 0,
  size: 7,
});

const columns = [
  { name: 'idpedido', required: true, label: 'Id pedido', align: 'center', field: 'idpedido', sortable: true },
  { name: 'nombrecliente', required: true, label: 'Nombre de cliente', align: 'left', field: 'nombrecliente', sortable: true },
  { name: 'apellidoscliente', required: true, label: 'Apellidos de cliente', align: 'left', field: 'apellidoscliente', sortable: true },
  { name: 'nombremepleado', required: true, label: 'Nombre de empleado', align: 'left', field: 'nombremepleado', sortable: true },
  { name: 'apellidosempleado', required: true, label: 'Apellidos de empleado', align: 'left', field: 'apellidosempleado', sortable: true },
  { name: 'fechapedido', required: true, label: 'Fecha de pedido', align: 'left', field: 'fechapedido', sortable: true },
  { name: 'subtotal', required: true, label: 'Subtotal', align: 'left', field: 'subtotal', sortable: true },
  { name: 'gastosenv', label: 'Gastos env', align: 'center', field: 'gastosenv', sortable: false},
  { name: 'totalpedido', label: 'Total Pedido', align: 'center', field: 'totalpedido', sortable: false},
  { name: 'pais', label: 'pais', align: 'center', field: 'pais', sortable: false},
  { name: 'nombreproducto', label: 'Producto', align: 'center', field: 'nombreproducto', sortable: false}

];

const loading = ref(false);
const pagination = ref({
  sortBy: 'idpedido',
  descending: false,
  page: 1,
  rowsPerPage: state.size,
});

const informes = [
  { id: 1, titulo: "Ventas por categoría"},
  { id: 2, titulo: "Ventas por cliente"},
  { id: 3, titulo: "Ventas por empleado"},
  { id: 4, titulo: "Ventas por país"},
  { id: 5, titulo: "Ventas por producto"},
];

const periodos = [
  { id: 1, titulo: "Ventas mensuales"}, 
  { id: 2, titulo: "Ventas trimestrales"}, 
  { id: 3, titulo: "Ventas anuales"}
];

const selectedInforme = ref(null);
const selectedPeriodo = ref(null);

const filteredTableData = computed(() => {
  if (!selectedInforme.value || !selectedPeriodo.value) return [];
  return state.tableData;
});

const applyFilter = () => {
  if (!selectedInforme.value || !selectedPeriodo.value) {
    errorMsg('Selecciona un informe y un período');
    return;
  }

  loading.value = true;
  const params = {
    informeId: selectedInforme.value,
    periodoId: selectedPeriodo.value,
    size: state.size,
    currentPage: pagination.value.page
  };

  getTituloTable(params)
    .then((res) => {
      loading.value = false;
      if (res.success) {
        state.tableData = res.data.records;
        state.total = res.data.total;
      } else {
        errorMsg(res.msg || "Error al cargar los datos");
      }
    })
    .catch(() => {
      loading.value = false;
      errorMsg("No se pudo conectar al servidor");
    });
};
</script>

<style scoped>
.text-subtitle1 {
  font-weight: bold;
}
</style>