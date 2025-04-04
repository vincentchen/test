<template>
  <div class="system-user-container">
    <el-card class="box-card">
      <template #header>
        <div class="card-header">
          <span>用户管理</span>
          <el-button type="primary" @click="handleAddUser">新增用户</el-button>
        </div>
      </template>
      <el-table :data="userList" style="width: 100%">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="username" label="用户名" />
        <el-table-column prop="realName" label="姓名" />
        <el-table-column prop="email" label="邮箱" />
        <el-table-column prop="role" label="角色" />
        <el-table-column prop="status" label="状态">
          <template #default="scope">
            <el-tag :type="scope.row.status === '启用' ? 'success' : 'danger'">
              {{ scope.row.status }}
            </el-tag>
          </template>
        </el-table-column>
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

interface UserItem {
  id: number
  username: string
  realName: string
  email: string
  role: string
  status: string
}

const userList = ref<UserItem[]>([
  {
    id: 1,
    username: 'admin',
    realName: '管理员',
    email: 'admin@example.com',
    role: '超级管理员',
    status: '启用'
  },
  {
    id: 2,
    username: 'user1',
    realName: '张三',
    email: 'zhangsan@example.com',
    role: '普通用户',
    status: '启用'
  }
])

const handleAddUser = () => {
  console.log('新增用户')
}

const handleEdit = (row: UserItem) => {
  console.log('编辑用户', row)
}

const handleDelete = (row: UserItem) => {
  console.log('删除用户', row)
}
</script>

<style scoped>
.system-user-container {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>