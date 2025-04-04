<template>
  <div class="workflow-instance-container">
    <el-card class="box-card">
      <template #header>
        <div class="card-header">
          <span>流程实例</span>
        </div>
      </template>
      <el-table :data="instanceList" style="width: 100%">
        <el-table-column prop="id" label="实例ID" width="180" />
        <el-table-column prop="workflowName" label="流程名称" />
        <el-table-column prop="status" label="状态">
          <template #default="scope">
            <el-tag :type="getStatusType(scope.row.status)">{{ scope.row.status }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="startTime" label="开始时间" width="180" />
        <el-table-column prop="endTime" label="结束时间" width="180" />
        <el-table-column label="操作" width="150">
          <template #default="scope">
            <el-button size="small" @click="handleViewDetail(scope.row)">查看详情</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'

interface InstanceItem {
  id: string
  workflowName: string
  status: string
  startTime: string
  endTime: string
}

const instanceList = ref<InstanceItem[]>([
  {
    id: '1001',
    workflowName: '请假审批流程',
    status: '进行中',
    startTime: '2024-01-20 09:00:00',
    endTime: '-'
  },
  {
    id: '1002',
    workflowName: '报销审批流程',
    status: '已完成',
    startTime: '2024-01-19 14:00:00',
    endTime: '2024-01-19 16:30:00'
  }
])

const getStatusType = (status: string) => {
  switch (status) {
    case '进行中':
      return 'primary'
    case '已完成':
      return 'success'
    case '已终止':
      return 'danger'
    default:
      return 'info'
  }
}

const handleViewDetail = (row: InstanceItem) => {
  console.log('查看详情', row)
}
</script>

<style scoped>
.workflow-instance-container {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>