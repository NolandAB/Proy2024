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
            v-model="state.clientForm.id" 
            type="hidden"
          />

          <div class="row q-col-gutter-md">
            <!-- Primera columna -->
            <div class="col-12 col-md-6">
              <div class="column q-gutter-md">
                <!-- Nombre -->
                <q-input
                  v-model="state.clientForm.nombre"
                  rounded 
                  outlined
                  label="Nombre"
                  dense
                  :rules="[val => !!val || 'El nombre es obligatorio']"
                />
                <!-- Apellidos -->
                <q-input
                  v-model="state.clientForm.apellidos"
                  rounded 
                  outlined
                  label="Apellidos"
                  dense
                  :rules="[val => !!val || 'Los apellidos son obligatorios']"
                />
                
                <!-- Compañía -->
                <q-input
                  v-model="state.clientForm.compania"
                  rounded 
                  outlined
                  label="Compañía"
                  dense
                />
                
                <q-input
                  v-model="state.clientForm.correo"
                  rounded 
                  outlined
                  type="email"
                  label="Correo"
                  dense
                />
                <q-input
                  v-model="state.clientForm.cargo"
                  rounded 
                  outlined
                  label="Cargo"
                  dense
                />
                <q-input
                  v-model="state.clientForm.teltrabajo"
                  rounded 
                  outlined
                  label="Teléfono de trabajo"
                  dense
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
                />
                <q-input
                  v-model="state.clientForm.fax"
                  rounded 
                  outlined
                  label="Fax"
                  dense
                />
                <q-input
                  v-model="state.clientForm.direccion"
                  rounded 
                  outlined
                  label="Dirección"
                  dense
                />
                <q-input
                  v-model="state.clientForm.ciudad"
                  rounded 
                  outlined
                  label="Ciudad"
                  dense
                />
                <q-input
                  v-model="state.clientForm.estadoprovincia"
                  rounded 
                  outlined
                  label="Estado o provincia"
                  dense
                />
                <q-input
                  v-model="state.clientForm.postal"
                  rounded 
                  outlined
                  label="Código Postal"
                  dense
                />
                <q-input
                  v-model="state.clientForm.pais"
                  rounded 
                  outlined
                  label="País"
                  dense
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
                :rules="[
                  val => !val || /^(https?:\/\/)?([\da-z.-]+)\.([a-z.]{2,6})([/\w .-]*)*\/?$/.test(val) || 'URL inválida'
                ]"
              />
            </div>
            <div class="col-12 col-md-6">
              <q-input
                v-model="state.clientForm.datosadj"
                rounded 
                outlined
                label="Datos Adjuntos"
                dense
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
              />
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
import { editClientes } from '../../api/clientes/clientes';
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
  clientObj: {
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

// Función para reiniciar el formulario
const resetForm = () => {
  state.clientForm = {
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
  };
};

// Observar cambios en clientObj
watch(

  () => props.clientObj,
  (newVal) => {
    if (newVal && Object.keys(newVal).length > 0) {
      state.clientForm = { ...newVal };
    } else {
      resetForm();
    }
  },
  { immediate: true }
);



const openFun = () => {
  if (state.clientForm.id = null){
    resetForm();
    state.title = 'Nuevo'
    isLoading.value = false
  }else{
    state.title = 'Editar'
    state.clientForm = props.clientObj
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
      console.log(state.clientForm)
      isLoading.value = true;
      
      const response = await editClientes(state.clientForm);
      
      if (response.success) {
        successMsg('Cliente guardado exitosamente');
        emit('get-list');
        isLoading.value = false;
        closeDialog();
      } else {
        errorMsg(response.msg || 'Error al guardar el cliente');
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