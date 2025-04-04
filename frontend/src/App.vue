<template>
  <el-container class="app-wrapper">
    <el-aside width="200px" class="sidebar-container">
      <div class="logo-container">
        <img src="/logo.svg" alt="logo" class="logo-img">
        <span class="logo-text">Workflow Admin</span>
      </div>
      <el-menu
        :default-active="route.path"
        class="el-menu-vertical"
        :collapse="false"
        background-color="#304156"
        text-color="#bfcbd9"
        active-text-color="#409EFF"
        router
      >
        <el-menu-item index="/dashboard">
          <el-icon><Odometer /></el-icon>
          <template #title>仪表盘</template>
        </el-menu-item>
        
        <el-sub-menu index="/workflow">
          <template #title>
            <el-icon><Connection /></el-icon>
            <span>工作流管理</span>
          </template>
          <el-menu-item index="/workflow/design">流程设计</el-menu-item>
          <el-menu-item index="/workflow/instance">流程实例</el-menu-item>
          <el-menu-item index="/workflow/task">任务管理</el-menu-item>
        </el-sub-menu>
        
        <el-sub-menu index="/system">
          <template #title>
            <el-icon><Setting /></el-icon>
            <span>系统管理</span>
          </template>
          <el-menu-item index="/system/user">用户管理</el-menu-item>
          <el-menu-item index="/system/role">角色管理</el-menu-item>
          <el-menu-item index="/system/permission">权限管理</el-menu-item>
        </el-sub-menu>
      </el-menu>
    </el-aside>
    
    <el-container>
      <el-header height="50px" class="header-container">
        <div class="header-left">
          <el-icon class="fold-btn" @click="toggleSidebar"><Fold /></el-icon>
          <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>{{ route.meta.title }}</el-breadcrumb-item>
          </el-breadcrumb>
        </div>
        <div class="header-right">
          <el-dropdown>
            <span class="user-dropdown">
              <el-avatar size="small" src="https://avatars.githubusercontent.com/u/1?v=4" />
              <span class="user-name">Admin</span>
            </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item>个人信息</el-dropdown-item>
                <el-dropdown-item>修改密码</el-dropdown-item>
                <el-dropdown-item divided>退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </el-header>
      
      <el-main>
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()
const isCollapse = ref(false)

const toggleSidebar = () => {
  isCollapse.value = !isCollapse.value
}
</script>

<style scoped lang="scss">
.app-wrapper {
  height: 100vh;
  
  .sidebar-container {
    background-color: #304156;
    transition: width 0.3s;
    
    .logo-container {
      height: 50px;
      padding: 10px;
      display: flex;
      align-items: center;
      background: #2b2f3a;
      
      .logo-img {
        width: 32px;
        height: 32px;
        margin-right: 10px;
      }
      
      .logo-text {
        color: #fff;
        font-size: 16px;
        font-weight: 600;
      }
    }
    
    .el-menu {
      border-right: none;
    }
  }
  
  .header-container {
    background-color: #fff;
    border-bottom: 1px solid #e6e6e6;
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 0 15px;
    
    .header-left {
      display: flex;
      align-items: center;
      
      .fold-btn {
        font-size: 20px;
        cursor: pointer;
        margin-right: 15px;
      }
    }
    
    .header-right {
      .user-dropdown {
        display: flex;
        align-items: center;
        cursor: pointer;
        
        .user-name {
          margin-left: 8px;
          font-size: 14px;
        }
      }
    }
  }
  
  .el-main {
    background-color: #f0f2f5;
    padding: 15px;
  }
}
</style>