<template>
    <q-dialog
      :close-on-click-modal="false" 
      @opened="openFun" 
      v-model="visible" 
      persistent
    >
      <q-card>
        <q-card-section>
          <div class="text-h6">{{ state.title }}</div>
        </q-card-section>
    
        <q-card-section>
          <q-form 
            ref="formRef" 
            @submit.prevent="submitForm"
            :loading="isLoading"
          >
            <!-- Input de ID oculto -->
            <input 
              v-model="state.pedidoForm.id" 
              type="hidden"
            />
  
            <div class="row q-col-gutter-md">
              <!-- Primera columna -->
              <div class="col-12 col-md-6">
                <div class="column q-gutter-md">
                  <!-- Nombre -->
                  <q-input
                    v-model="state.pedidoForm.idcliente"
                    rounded 
                    outlined
                    label="Id de cliente"
                    dense
                  />
                  <!-- Apellidos -->
                  <q-input
                    v-model="state.pedidoForm.fechadepedido"
                    rounded 
                    outlined
                    label="fecha pedido"
                    dense
                  />
                  
                  <!-- Compañía -->
                  <q-input
                    v-model="state.pedidoForm.fechadeenvio"
                    rounded 
                    outlined
                    label="Fechad e envio"
                    dense
                  />
                  
                  <q-input
                    v-model="state.pedidoForm.gastosenvio"
                    rounded 
                    outlined
                    label="Gastos de envio"
                    dense
                  />
                  <q-input
                    v-model="state.pedidoForm.direccioncliente"
                    rounded 
                    outlined
                    label="Direccion del cliente"
                    dense
                  />
                  <q-input
                    v-model="state.pedidoForm.ciudaddest"
                    rounded 
                    outlined
                    label="Ciudad de destino"
                    dense
                  />
                </div>
              </div>
  
              <!-- Segunda columna -->
              <div class="col-12 col-md-6">
                <div class="column q-gutter-md">
                  <q-input
                    v-model="state.pedidoForm.edoprov"
                    rounded 
                    outlined
                    label="Estado provincia"
                    dense
                  />
                  <q-input
                    v-model="state.pedidoForm.cpostal"
                    rounded 
                    outlined
                    label="Correo Postal"
                    dense
                  />
                  <q-input
                    v-model="state.pedidoForm.pais"
                    rounded 
                    outlined
                    label="Pais"
                    dense
                  />
                  <q-input
                    v-model="state.pedidoForm.tipopago"
                    rounded 
                    outlined
                    label="Tipo de pago"
                    dense
                  />
                  <q-input
                    v-model="state.pedidoForm.fechapago"
                    rounded 
                    outlined
                    label="Fecha de pago"
                    dense
                  />
                  <q-input
                    v-model="state.pedidoForm.estadoimp"
                    rounded 
                    outlined
                    label="Estado de impuestos"
                    dense
                  />
                  <q-input
                    v-model="state.pedidoForm.idcliente"
                    rounded 
                    outlined
                    label="Id cliente"
                    dense
                  />
                </div>
              </div>
            </div>
          </q-form>
        </q-card-section>
  
        <q-card-actions align="right" class="q-pa-md">
          <q-btn 
            flat 
            label="Cancelar" 
            color="negative" 
            @click="closeDialog" 
            :disable="isLoading"
          />
          <q-btn 
            flat 
            label="Guardar" 
            color="primary" 
            @click="submitForm" 
            :loading="isLoading"
          />
        </q-card-actions>
      </q-card>
    </q-dialog>
  </template>
  
  <script setup>
  import { editPedidos } from '../../api/listapedidos/listapedidos';
  import { reactive, computed, ref, watch } from 'vue';
  import { defineProps, defineEmits } from 'vue';
  import { errorMsg, successMsg } from '../../utils/message';
  
  const isLoading = ref(false);
  const formRef = ref();
  
  const props = defineProps({
    dialogVisible: {
      type: Boolean,
      required: true,
      default: false,
    },
    pedidoObj: {
      type: Object,
      default: () => ({}),
    },
  });
  
  const emit = defineEmits(['update:dialogVisible', 'get-list']);
  
  const visible = computed({
    get: () => props.dialogVisible,
    set: (val) => emit('update:dialogVisible', val),
  });
  
  const state = reactive({
    title: 'Editar',
    pedidoForm: {
      id: null,
      idcliente: '',
      idtransportista: '',
      fechadepedido: '',
      fechadeenvio: '',
      gastosenvio: '',
      direccioncliente: '',
      ciudaddest: '',
      edoprov: '',
      cpostal: '',
      pais: '',
      tipopago: '',
      fechapago: '',
      estadoimp: '',
      transportista: '',
      nombreempleado: '',
      nombreestado: '',
    },
  });
  
  // Función para reiniciar el formulario
  const resetForm = () => {
    state.pedidoForm = {
      id: null,
      idcliente: '',
      idtransportista: '',
      fechadepedido: '',
      fechadeenvio: '',
      gastosenvio: '',
      direccioncliente: '',
      ciudaddest: '',
      edoprov: '',
      cpostal: '',
      pais: '',
      tipopago: '',
      fechapago: '',
      estadoimp: '',
      transportista: '',
      nombreempleado: '',
      nombreestado: '',
    };
  };
  
  // Observar cambios en pedidoObj
  watch(
  
    () => props.pedidoObj,
    (newVal) => {
      console.log(newVal)
      if (newVal && Object.keys(newVal).length > 0) {
        state.pedidoForm = { ...newVal };
      } else {
        resetForm();
      }
    },
    { immediate: true }
  );
  
  
  
  const openFun = () => {
    if (state.pedidoForm.id = null){
      resetForm();
      state.title = 'Nuevo'
      isLoading.value = false
    }else{
      state.title = 'Editar'
      state.pedidoForm = props.pedidoObj
    }
  }
  
  
  const closeDialog = () => {
    if (!isLoading.value) {
      visible.value = false;
      resetForm();
    }
  };
  
  const submitForm = async () => {
  try {
    const isValid = await formRef.value.validate();

    if (isValid) {
      console.log(state.pedidoForm);
      isLoading.value = true;

      // Eliminar 'cantidad' si está presente en el formulario antes de enviarlo
      const pedidoData = { ...state.pedidoForm };
      delete pedidoData.cantidad;  // Eliminar 'cantidad' si está presente

      const response = await editPedidos(pedidoData);

      if (response.success) {
        successMsg('Pedido guardado exitosamente');
        emit('get-list');
        isLoading.value = false;
        closeDialog();
      } else {
        errorMsg(response.msg || 'Error al guardar el pedido');
      }
    }
  } catch (error) {
    errorMsg(error.message || 'Error en el proceso de guardado');
  } finally {
    isLoading.value = false;
  }
};
  </script>
  
  <style scoped>
  .q-card {
    width: 800px;
    max-width: 90vw;
  }
  
  .q-form {
    width: 100%;
  }
  
  .q-card-section {
    padding: 20px;
  }
  </style>