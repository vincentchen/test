<template>
  <div class="system-permission-container">
    <el-card class="box-card">
      <template #header>
        <div class="card-header">
          <span>权限管理</span>
          <el-button type="primary" @click="handleAddPermission">新增权限</el-button>
        </div>
      </template>
      <el-table :data="permissionList" style="width: 100%">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="name" label="权限名称" />
        <el-table-column prop="code" label="权限编码" />
        <el-table-column prop="type" label="类型">
          <template #default="scope">
            <el-tag :type="getTypeTag(scope.row.type)">{{ scope.row.type }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="path" label="路径" />
        <el-table-column label="操作" width="200">
          <template #default="scope">
            <el-button size="small" @click="handleEdit(scope.row)">编辑</el-button>
            <el-button
              size="small"
              type="danger"
              @click="handleDelete(scope.row)"
            >删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'

interface PermissionItem {
  id: number
  name: string
  code: string
  type: string
  path: string
}

const permissionList = ref<PermissionItem[]>([
  {
    id: 1,
    name: '用户管理',
    code: 'system:user:list',
    type: '菜单',
    path: '/system/user'
  },
  {
    id: 2,
    name: '新增用户',
    code: 'system:user:add',
    type: '按钮',
    path: ''
  }
])

const getTypeTag = (type: string) => {
  switch (type) {
    case '菜单':
      return 'success'
    case '按钮':
      return 'warning'
    default:
      return 'info'
  }
}

const handleAddPermission = () => {
  console.log('新增权限')
}

const handleEdit = (row: PermissionItem) => {
  console.log('编辑权限', row)
}

const handleDelete = (row: PermissionItem) => {
  console.log('删除权限', row)
}
</script>

<style scoped>
.system-permission-container {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>