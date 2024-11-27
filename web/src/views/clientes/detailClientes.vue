<template>
  <q-dialog v-model="visible" persistent>
    <q-card>
      <q-card-section>
        <div class="text-h6">{{ state.title }}</div>
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
        <q-tab name="general" label="General" />
        <q-tab name="pedidos" label="Pedidos" />
      </q-tabs>

      <q-separator />

      <!-- Tab Panels -->
      <q-tab-panels v-model="activeTab" animated>
        <!-- Panel General -->
        <q-tab-panel name="general">
          <q-form ref="formRef">
            <div class="row q-col-gutter-md">
              <!-- Primera columna -->
              <div class="col-12 col-md-6">
                <div class="column q-gutter-md">
                  <q-input
                    v-model="state.clientForm.nombre"
                    rounded 
                    outlined
                    label="Nombre"
                    dense
                    readonly
                  />
                  <q-input
                    v-model="state.clientForm.apellidos"
                    rounded 
                    outlined
                    label="Apellidos"
                    dense
                    readonly
                  />
                  <q-input
                    v-model="state.clientForm.compania"
                    rounded 
                    outlined
                    label="Compañía"
                    dense
                    readonly
                  />
                  <q-input
                    v-model="state.clientForm.correo"
                    rounded 
                    outlined
                    type="email"
                    label="Correo"
                    dense
                    readonly
                  />
                  <q-input
                    v-model="state.clientForm.cargo"
                    rounded 
                    outlined
                    label="Cargo"
                    dense
                    readonly
                  />
                  <q-input
                    v-model="state.clientForm.teltrabajo"
                    rounded 
                    outlined
                    label="Teléfono de trabajo"
                    dense
                    readonly
                  />
                </div>
              </div>
  
              <!-- Segunda columna -->
              <div class="col-12 col-md-6">
                <div class="column q-gutter-md">
                  <q-input
                    v-model="state.clientForm.telmovil"
                    rounded 
                    outlined
                    label="Teléfono Móvil"
                    dense
                    readonly
                  />
                  <q-input
                    v-model="state.clientForm.fax"
                    rounded 
                    outlined
                    label="Fax"
                    dense
                    readonly
                  />
                  <q-input
                    v-model="state.clientForm.direccion"
                    rounded 
                    outlined
                    label="Dirección"
                    dense
                    readonly
                  />
                  <q-input
                    v-model="state.clientForm.ciudad"
                    rounded 
                    outlined
                    label="Ciudad"
                    dense
                    readonly
                  />
                  <q-input
                    v-model="state.clientForm.estadoprovincia"
                    rounded 
                    outlined
                    label="Estado o provincia"
                    dense
                    readonly
                  />
                  <q-input
                    v-model="state.clientForm.postal"
                    rounded 
                    outlined
                    label="Código Postal"
                    dense
                    readonly
                  />
                  <q-input
                    v-model="state.clientForm.pais"
                    rounded 
                    outlined
                    label="País"
                    dense
                    readonly
                  />
                </div>
              </div>
            </div>
  
            <!-- Sección para campos adicionales -->
            <div class="row q-col-gutter-md q-mt-md">
              <div class="col-12 col-md-6">
                <q-input
                  v-model="state.clientForm.paginaweb"
                  rounded 
                  outlined
                  label="Página Web"
                  dense
                  readonly
                />
              </div>
              <div class="col-12 col-md-6">
                <q-input
                  v-model="state.clientForm.datosadj"
                  rounded 
                  outlined
                  label="Datos Adjuntos"
                  dense
                  readonly
                />
              </div>
              <div class="col-12">
                <q-input
                  v-model="state.clientForm.notas"
                  rounded 
                  outlined
                  type="textarea"
                  label="Notas"
                  dense
                  readonly
                />
              </div>
            </div>
            <q-btn
              flat
              label="Enviar Correo"
              color="primary"
              :disable="!state.clientForm.correo"
              @click="openEmailClient"
            />

          </q-form>
        </q-tab-panel>

        <!-- Panel Pedidos -->
        <q-tab-panel name="pedidos">
          <div class="text-center q-pa-md">
             <q-table
             flat
             bordered
             ref="tableRef1"
             title="Pedidos por el cliente"
             :rows="state.tableData"
             :columns="columns"
             row-key="id1"
             :pagination="pagination"
             :loading="loading"
             :filter="filter"
             binary-state-sort
             :rows-per-page-options="[]" 
             @request="onRequest"
             :visible-columns="visibleColumns"
             ></q-table>
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
import { getPedidosClienteTable } from '../../api/clientes/pedidoscliente'
import { reactive, computed, ref, watch, onMounted } from 'vue';
import { defineProps, defineEmits } from 'vue';
import { date } from 'quasar';

const state = reactive({
title: 'Detalles del Cliente',
blurry: '',
tableData: [],
current: 1,
size: 1000, // Tamaño fijo de página
total: 0,
clientForm: {
  id: null,
  nombre: '',
  apellidos: '',
  compania: '',
  correo: '',
  cargo: '',
  teltrabajo: '',
  telmovil: '',
  fax: '',
  direccion: '',
  ciudad: '',
  estadoprovincia: '',
  postal: '',
  pais: '',
  paginaweb: '',
  notas: '',
  datosadj: '',
},
});

const columns = [
{ name: 'idcliente', required: true, label: 'Cliente', align: 'left', field: 'idcliente', sortable: true },
{ name: 'idpedido', required: true, label: 'Nro Factura', align: 'left', field: 'idpedido', sortable: true },
{ name: 'fechadepedido', required: true, label: 'Fecha de pedido', align: 'left', field: 'fechadepedido', sortable:true, trueformat: val => date.formatDate(val, 'DD-MM-YYYY') },
{ name: 'fechadeenvio', required: true, label: 'Fecha de envio', align: 'left', field: 'fechadeenvio', sortable: true, format: val => date.formatDate(val, 'DD-MM-YYYY') },
{ name: 'impuestos', required: true, label: 'Impuestos', align: 'left', field: 'impuestos', sortable: true },
{ name: 'gastosenvio', required: true, label: 'Gastos Envio', align: 'left', field: 'gastosenvio', sortable: true },
{ name: 'subtotal', required: true, label: 'Subtotal', align: 'left', field: 'subtotal', sortable: true },
{ name: 'preciototal', required: true, label: 'Total', align: 'left', field: 'preciototal', sortable: true },
];
const loading = ref(false);
const visibleColumns = ref(['idcliente','idpedido','fechadepedido','fechadeenvio','impuestos','gastosenvio', 'subtotal', 'preciototal']);

const pagination = ref({
  sortBy: 'nombre',
  descending: false,
  page: 1,
  rowsPerPage: state.size,
});

const openEmailClient = () => {
  const email = state.clientForm.correo;
  if (email) {
    const subject = encodeURIComponent('Comunicado'); // Puedes personalizar el asunto
    const body = encodeURIComponent('Estimado '+state.clientForm.apellidos); // Puedes personalizar el cuerpo del correo
    const mailtoLink = `mailto:${email}?subject=${subject}&body=${body}`;
    window.location.href = mailtoLink;
  }
};

const getPedidosClienteTableFun = () => {
  loading.value = true;

  const params = {
    blurry: state.blurry,
    size: state.size,
    currentPage: pagination.value.page,
    idcliente: state.clientForm.id // Añadir esta línea
  };

  getPedidosClienteTable(params)
    .then((res) => {
      loading.value = false;
      if(res.success){
        // Filtrar los pedidos que coincidan con el ID del cliente
        state.tableData = res.data.records.filter(
          pedido => pedido.idcliente === state.clientForm.id
        );
        state.total = state.tableData.length;
      } else{
        errorMsg(res.msg);
      }
    })
    .catch(() => {
      loading.value = false
    });
};


// Manejar eventos de solicitud
const onRequest = (props) => {
  const { page, sortBy, descending } = props.pagination;

  pagination.value.sortBy = sortBy || '';
  pagination.value.descending = descending;
  pagination.value.page = page;

  state.current = page; // Actualizar el estado
  getPedidosClienteTableFun();
};

// Cargar datos iniciales
onMounted(() => {
  getPedidosClienteTableFun();
});

const props = defineProps({
dialogvisibledetail: {
  type: Boolean,
  required: true,
  default: false,
},
clientesDetail: {
  type: Object,
  required: true,
  default: () => ({}),
},
// Nuevas props para la navegación
currentIndex: {
  type: Number,
  required: true
},
totalClients: {
  type: Number,
  required: true
}
});
// Modificar el watch existente para llamar a getPedidosClienteTableFun
watch(
  () => props.clientesDetail,
  (newVal) => {
    if (newVal && Object.keys(newVal).length > 0) {
      state.clientForm = { ...newVal };
      getPedidosClienteTableFun(); // Llamar a la función cuando se carga un nuevo cliente
    }
  },
  { immediate: true, deep: true }
);

const emit = defineEmits(['update:dialogvisibledetail', 'navigate-client']);

const activeTab = ref('general');

const visible = computed({
get: () => props.dialogvisibledetail,
set: (val) => emit('update:dialogvisibledetail', val),
});

// Computed properties para la navegación
const hasPreviousClient = computed(() => props.currentIndex > 0);
const hasNextClient = computed(() => props.currentIndex < props.totalClients - 1);


// Función para navegar entre clientes
const navigateToClient = (direction) => {
emit('navigate-client', direction);
};

watch(
() => props.clientesDetail,
(newVal) => {
  if (newVal && Object.keys(newVal).length > 0) {
    state.clientForm = { ...newVal };
  }
},
{ immediate: true, deep: true }
);

const closeDialog = () => {
visible.value = false;
};
</script>

<style scoped>
.q-card {
width: 800px;
max-width: 90vw;
}

.q-tab-panels {
background-color: white;
}
</style>