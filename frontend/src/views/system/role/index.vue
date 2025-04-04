<template>
  <div class="system-role-container">
    <el-card class="box-card">
      <template #header>
        <div class="card-header">
          <span>角色管理</span>
          <el-button type="primary" @click="handleAddRole">新增角色</el-button>
        </div>
      </template>
      <el-table :data="roleList" style="width: 100%">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="name" label="角色名称" />
        <el-table-column prop="code" label="角色编码" />
        <el-table-column prop="description" label="描述" />
        <el-table-column prop="createTime" label="创建时间" width="180" />
        <el-table-column label="操作" width="250">
          <template #default="scope">
            <el-button size="small" @click="handleEdit(scope.row)">编辑</el-button>
            <el-button size="small" type="success" @click="handlePermission(scope.row)">权限配置</el-button>
            <el-button size="small" type="danger" @click="handleDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'

interface RoleItem {
  id: number
  name: string
  code: string
  description: string
  createTime: string
}

const roleList = ref<RoleItem[]>([
  {
    id: 1,
    name: '超级管理员',
    code: 'SUPER_ADMIN',
    description: '系统最高权限角色',
    createTime: '2024-01-01 00:00:00'
  },
  {
    id: 2,
    name: '普通用户',
    code: 'NORMAL_USER',
    description: '普通用户角色',
    createTime: '2024-01-01 00:00:00'
  }
])

const handleAddRole = () => {
  console.log('新增角色')
}

const handleEdit = (row: RoleItem) => {
  console.log('编辑角色', row)
}

const handlePermission = (row: RoleItem) => {
  console.log('配置权限', row)
}

const handleDelete = (row: RoleItem) => {
  console.log('删除角色', row)
}
</script>

<style scoped>
.system-role-container {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>