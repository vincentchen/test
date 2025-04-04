<template>
  <div class="workflow-task-container">
    <el-card class="box-card">
      <template #header>
        <div class="card-header">
          <span>任务管理</span>
        </div>
      </template>
      <el-table :data="taskList" style="width: 100%">
        <el-table-column prop="id" label="任务ID" width="180" />
        <el-table-column prop="name" label="任务名称" />
        <el-table-column prop="assignee" label="处理人" />
        <el-table-column prop="status" label="状态">
          <template #default="scope">
            <el-tag :type="getStatusType(scope.row.status)">{{ scope.row.status }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="180" />
        <el-table-column label="操作" width="150">
          <template #default="scope">
            <el-button size="small" type="primary" @click="handleProcess(scope.row)"
              :disabled="scope.row.status === '已完成'">处理</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'

interface TaskItem {
  id: string
  name: string
  assignee: string
  status: string
  createTime: string
}

const taskList = ref<TaskItem[]>([
  {
    id: 'T001',
    name: '请假申请审批',
    assignee: '张经理',
    status: '待处理',
    createTime: '2024-01-20 10:30:00'
  },
  {
    id: 'T002',
    name: '报销单审核',
    assignee: '李经理',
    status: '已完成',
    createTime: '2024-01-19 15:00:00'
  }
])

const getStatusType = (status: string) => {
  switch (status) {
    case '待处理':
      return 'warning'
    case '处理中':
      return 'primary'
    case '已完成':
      return 'success'
    default:
      return 'info'
  }
}

const handleProcess = (row: TaskItem) => {
  console.log('处理任务', row)
}
</script>

<style scoped>
.workflow-task-container {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>