<template>
  <q-dialog v-model="visible" persistent>
    <q-card>
      <q-card-section>
        <div class="text-h6">{{ state.title }}</div>
        <div class="row q-col-gutter-md">
              <!-- Primera columna -->
              <div class="col-12 col-md-6">
                <div class="column q-gutter-md">
                  <q-input
                    v-model="state.clienteInfo.nombre"
                    rounded 
                    outlined
                    label="Nombre del cliente"
                    dense
                    readonly
                  />
                  <q-input
                    v-model="state.clienteInfo.email"
                    rounded 
                    outlined
                    label="Direccion de correo del cliente"
                    dense
                    readonly
                  />
                </div>
              </div>
  
              <!-- Segunda columna -->
              <div class="col-12 col-md-6">
                <div class="column q-gutter-md">
                  <q-input
                    v-model="state.clienteInfo.empleado"
                    rounded 
                    outlined
                    label="Empleado/vendedor"
                    dense
                    readonly
                  />
                  <q-input
                    v-model="state.clienteInfo.fechaPedido"
                    rounded 
                    outlined
                    label="Fecha de pedido"
                    dense
                    readonly
                  />
                </div>
              </div>
            </div>
      </q-card-section>
  
      <q-separator />
      
      <!-- Tabs -->
      <q-tabs
        v-model="activeTab"
        class="text-primary"
        active-color="primary"
        indicator-color="primary"
        align="left"
        narrow-indicator
      >
        <q-tab name="detalles" label="detalles" />
        <q-tab name="info" label="info" />
        <q-tab name="pago" label="pago"></q-tab>
      </q-tabs>
  
      <q-separator />
  
      <!-- Tab Panels -->
      <q-tab-panels v-model="activeTab" animated>
        <!-- Panel General -->
        <q-tab-panel name="detalles">
          <q-form ref="formRef">
            <!-- Sección para campos adicionales -->
            <div class="row q-col-gutter-md q-mt-md justify-center items-center">
              <q-table
                flat
                bordered
                ref="tableRef1"
                title="Pedidos por el cliente"
                :rows="filteredTableData"
                :columns="columns"
                row-key="id"
                :pagination="pagination"
                :loading="loading"
                :filter="filter"
                binary-state-sort
                :rows-per-page-options="[]" 
                @request="onRequest"
                :visible-columns="visibleColumns"
              ></q-table>
            </div>
          </q-form>
        </q-tab-panel>
  
        <!-- Panel Pedidos -->
        <q-tab-panel name="info">
          <div class="row q-col-gutter-md">
            <div class="col-12 col-md-6">
              <div class="column q-gutter-md">
                <q-input
                  v-model="state.envioInfo.transportista"
                  rounded 
                  outlined
                  label="Compania transporte"
                  dense
                  readonly
                />
                <q-input
                  v-model="state.envioInfo.fechaEnvio"
                  rounded 
                  outlined
                  label="Fecha de envio"
                  dense
                  readonly
                />
                <q-input
                  v-model="state.envioInfo.gastosEnvio"
                  rounded 
                  outlined
                  label="gastos de envio"
                  dense
                  readonly
                />
                <q-input
                  v-model="state.envioInfo.direccion"
                  rounded 
                  outlined
                  label="Direccion de envio"
                  dense
                  readonly
                />
              </div>
            </div>
            <div class="col-12 col-md-6">
              <div class="column q-gutter-md">
                <q-input
                  v-model="state.envioInfo.ciudad"
                  rounded 
                  outlined
                  label="Ciudad destino"
                  dense
                  readonly
                />
                <q-input
                  v-model="state.envioInfo.estado"
                  rounded 
                  outlined
                  label="Estado Provincia"
                  dense
                  readonly
                />
                <q-input
                  v-model="state.envioInfo.codigoPostal"
                  rounded 
                  outlined
                  label="Codigo postal"
                  dense
                  readonly
                />
                <q-input
                  v-model="state.envioInfo.pais"
                  rounded 
                  outlined
                  label="Pais"
                  dense
                  readonly
                />
              </div>
            </div>
          </div>
        </q-tab-panel>

        <q-tab-panel name="pago">
          <div class="row q-col-gutter-md">
            <div class="col-12 col-md-6">
              <div class="column q-gutter-md">
                <q-input
                  v-model="state.pagoInfo.tipoPago"
                  rounded 
                  outlined
                  label="Tipo de pago"
                  dense
                  readonly
                />
                <q-input
                  v-model="state.pagoInfo.fechaPago"
                  rounded 
                  outlined
                  label="Fecha de pago"
                  dense
                  readonly
                />
              </div>
            </div>
          </div>
        </q-tab-panel>
      </q-tab-panels>
  
      <!-- Botones de navegación y acciones -->
      <q-card-actions align="between" class="q-px-md">
        <div>
          <q-btn
            flat
            icon="chevron_left"
            label="Anterior"
            color="primary"
            :disable="!hasPreviousClient"
            @click="navigateToClient('previous')"
          />
          <q-btn
            flat
            icon-right="chevron_right"
            label="Siguiente"
            color="primary"
            :disable="!hasNextClient"
            @click="navigateToClient('next')"
          />
        </div>
        <q-btn flat label="Cerrar" color="primary" @click="closeDialog" />
      </q-card-actions>
    </q-card>
  </q-dialog>
</template>

<script setup>
import { reactive, computed, ref, watch, onMounted } from 'vue';
import { defineProps, defineEmits } from 'vue';
import { date } from 'quasar';
import { getListaPedidos2Table } from '../../api/listapedidos/listapedidos';

const state = reactive({
  title: 'Detalles de pedido',
  clienteInfo: {
    nombre: '',
    email: '',
    empleado: '',
    fechaPedido: ''
  },
  envioInfo: {
    transportista: '',
    fechaEnvio: '',
    gastosEnvio: '',
    direccion: '',
    ciudad: '',
    estado: '',
    codigoPostal: '',
    pais: ''
  },
  pagoInfo: {
    tipoPago: '',
    fechaPago: ''
  },
  tableData: [],
  current: 1,
  size: 100,
  total: 0,
  blurry: ''
});

const columns = [
  { name: 'productonombre', required: true, label: 'Producto', align: 'left', field: 'productonombre', sortable: true },
  { name: 'cantidad', required: true, label: 'Cantidad', align: 'left', field: 'cantidad', sortable: true },
  { name: 'total', required: true, label: 'Total', align: 'left', field: 'total', sortable: true},
  { name: 'nombreestado', required: true, label: 'Estado', align: 'left', field: 'nombreestado', sortable: true },
];

const loading = ref(false);
const visibleColumns = ref(['productonombre', 'cantidad', 'total']);
const activeTab = ref('detalles');

const pagination = ref({
  sortBy: 'nombre',
  descending: false,
  page: 1,
  rowsPerPage: state.size,
});

const props = defineProps({
  dialogvisibledetailp: {
    type: Boolean,
    required: true,
    default: false,
  },
  pedidosDetail: {
    type: Object,
    required: true,
    default: () => ({}),
  },
  currentIndex: {
    type: Number,
    required: true
  },
  totalPedidos: {
    type: Number,
    required: true
  }
});

const emit = defineEmits(['update:dialogvisibledetailp', 'navigate-client']);

// Función para actualizar los detalles del pedido
const updatePedidoDetails = (details) => {
  if (details) {
    // Mapeo de campos para cliente
    state.clienteInfo = {
      nombre: details.clientenombre || '',
      email: details.clienteemail || '',
      empleado: details.nombreempleado || '',
      fechaPedido: details.fechadepedido || ''
    };

    // Mapeo de campos para envío
    state.envioInfo = {
      transportista: details.transportista || '',
      fechaEnvio: details.fechadeenvio || '',
      gastosEnvio: details.gastosenvio || '',
      direccion: details.direccioncliente || '',
      ciudad: details.ciudaddest || '',
      estado: details.edoprov || '',
      codigoPostal: details.cpostal || '',
      pais: details.pais || ''
    };

    // Mapeo de campos para pago
    state.pagoInfo = {
      tipoPago: details.tipopago || '',
      fechaPago: details.fechapago || ''
    };
  }
};

// Watch para observar cambios en los detalles del pedido
watch(
  () => props.pedidosDetail, 
  (newDetails) => updatePedidoDetails(newDetails),
  { immediate: true }
);

// Computed para visibilidad del diálogo
const visible = computed({
  get: () => props.dialogvisibledetailp,
  set: (val) => emit('update:dialogvisibledetailp', val),
});

// Computed para navegación
const hasPreviousClient = computed(() => props.currentIndex > 0);
const hasNextClient = computed(() => props.currentIndex < props.totalPedidos - 1);

// Función para navegar entre clientes
const navigateToClient = (direction) => {
  emit('navigate-client', direction);
};

// Función para cerrar el diálogo
const closeDialog = () => {
  visible.value = false;
};
// Computed: Filtrar datos según el ID actual
const filteredTableData = computed(() => {
  const currentId = props.pedidosDetail.id; // Suponiendo que el ID viene en `pedidosDetail`
  return state.tableData.filter((item) => item.id == currentId);
});
// Funciones para obtener lista de pedidos
const getListaPedidosTableFun = () => {
  loading.value = true;

  const params = {
    blurry: state.blurry,
    size: state.size,
    currentPage: pagination.value.page,
  };

  getListaPedidos2Table(params)
    .then((res) => {
      loading.value = false;
      if (res.success) {
        state.tableData = res.data.records;
        state.total = res.data.total;
      } else {
        // Manejar error
        console.error(res.msg);
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

  state.current = page;
  getListaPedidosTableFun();
};

// Cargar datos iniciales
onMounted(() => {
  getListaPedidosTableFun();
});
</script>

<style scoped>
.q-card {
  width: 800px;
  max-width: 90vw;
}

.q-tab-panels {
  background-color: white;
}
.row {
  display: flex;
  flex-wrap: wrap;
}

.col-12 {
  flex: 0 0 100%;
}

.col-md-6 {
  flex: 0 0 50%;
  max-width: 50%;
}

</style>