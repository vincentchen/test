<template>
  <div class="workflow-design">
    <div class="toolbar">
      <el-button-group>
        <el-button type="primary" @click="saveProcess">
          <el-icon><Download /></el-icon>保存
        </el-button>
        <el-button type="success" @click="deployProcess">
          <el-icon><Upload /></el-icon>部署
        </el-button>
        <el-button @click="downloadXML">
          <el-icon><Document /></el-icon>下载XML
        </el-button>
      </el-button-group>
    </div>
    
    <div class="content">
      <div class="canvas" ref="bpmnContainer"></div>
      <div class="properties-panel">
        <el-form label-width="100px" size="small">
          <el-form-item label="流程名称">
            <el-input v-model="processData.name" placeholder="请输入流程名称" />
          </el-form-item>
          <el-form-item label="流程标识">
            <el-input v-model="processData.key" placeholder="请输入流程标识" />
          </el-form-item>
          <el-form-item label="流程描述">
            <el-input
              v-model="processData.description"
              type="textarea"
              rows="3"
              placeholder="请输入流程描述"
            />
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue'
import BpmnModeler from 'bpmn-js/lib/Modeler'
import 'bpmn-js/dist/assets/diagram-js.css'
import 'bpmn-js/dist/assets/bpmn-font/css/bpmn.css'
import { ElMessage } from 'element-plus'

const bpmnContainer = ref<HTMLElement | null>(null)
const bpmnModeler = ref<any>(null)

const processData = ref({
  name: '',
  key: '',
  description: ''
})

// 初始化BPMN建模器
const initBpmnModeler = async () => {
  if (!bpmnContainer.value) return
  
  bpmnModeler.value = new BpmnModeler({
    container: bpmnContainer.value,
    keyboard: {
      bindTo: window
    }
  })
  
  try {
    const newDiagram = await createNewDiagram()
    await bpmnModeler.value.importXML(newDiagram)
  } catch (error) {
    console.error('Failed to create new diagram', error)
    ElMessage.error('初始化流程图失败')
  }
}

// 创建新的流程图
const createNewDiagram = () => {
  return `<?xml version="1.0" encoding="UTF-8"?>
    <bpmn:definitions xmlns:bpmn="http://www.omg.org/spec/BPMN/20100524/MODEL"
                      xmlns:bpmndi="http://www.omg.org/spec/BPMN/20100524/DI"
                      xmlns:dc="http://www.omg.org/spec/DD/20100524/DC"
                      xmlns:di="http://www.omg.org/spec/DD/20100524/DI"
                      id="Definitions_1"
                      targetNamespace="http://bpmn.io/schema/bpmn">
      <bpmn:process id="Process_1" isExecutable="true">
        <bpmn:startEvent id="StartEvent_1" />
      </bpmn:process>
      <bpmndi:BPMNDiagram id="BPMNDiagram_1">
        <bpmndi:BPMNPlane id="BPMNPlane_1" bpmnElement="Process_1">
          <bpmndi:BPMNShape id="_BPMNShape_StartEvent_2" bpmnElement="StartEvent_1">
            <dc:Bounds x="156" y="81" width="36" height="36" />
          </bpmndi:BPMNShape>
        </bpmndi:BPMNPlane>
      </bpmndi:BPMNDiagram>
    </bpmn:definitions>`
}

// 保存流程
const saveProcess = async () => {
  try {
    const { xml } = await bpmnModeler.value.saveXML({ format: true })
    // TODO: 调用后端API保存流程
    console.log('Process XML:', xml)
    ElMessage.success('保存成功')
  } catch (error) {
    console.error('Failed to save process', error)
    ElMessage.error('保存失败')
  }
}

// 部署流程
const deployProcess = async () => {
  try {
    const { xml } = await bpmnModeler.value.saveXML({ format: true })
    // TODO: 调用后端API部署流程
    console.log('Deploy XML:', xml)
    ElMessage.success('部署成功')
  } catch (error) {
    console.error('Failed to deploy process', error)
    ElMessage.error('部署失败')
  }
}

// 下载XML
const downloadXML = async () => {
  try {
    const { xml } = await bpmnModeler.value.saveXML({ format: true })
    const blob = new Blob([xml], { type: 'application/xml' })
    const url = window.URL.createObjectURL(blob)
    const link = document.createElement('a')
    link.href = url
    link.download = `${processData.value.name || 'process'}.bpmn`
    link.click()
    window.URL.revokeObjectURL(url)
  } catch (error) {
    console.error('Failed to download XML', error)
    ElMessage.error('下载失败')
  }
}

onMounted(() => {
  initBpmnModeler()
})

onUnmounted(() => {
  if (bpmnModeler.value) {
    bpmnModeler.value.destroy()
  }
})
</script>

<style scoped lang="scss">
.workflow-design {
  height: calc(100vh - 120px);
  display: flex;
  flex-direction: column;
  background-color: #fff;
  border-radius: 4px;
  
  .toolbar {
    padding: 8px 16px;
    border-bottom: 1px solid #dcdfe6;
  }
  
  .content {
    flex: 1;
    display: flex;
    overflow: hidden;
    
    .canvas {
      flex: 1;
      height: 100%;
      border-right: 1px solid #dcdfe6;
    }
    
    .properties-panel {
      width: 300px;
      padding: 16px;
      overflow-y: auto;
    }
  }
}
</style>