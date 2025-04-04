<template>
  <div class="dashboard-container">
    <h1>工作流管理系统</h1>
    <div class="dashboard-content">
      <el-card class="welcome-card">
        <template #header>
          <div class="card-header">
            <span>欢迎使用</span>
          </div>
        </template>
        <div class="card-content">
          <p>这是一个基于Flowable的工作流管理系统</p>
        </div>
      </el-card>
    </div>
  </div>
</template>

<script lang="ts" setup>
// Dashboard component logic
</script>

<style scoped>
.dashboard-container {
  padding: 20px;
}

.dashboard-content {
  margin-top: 20px;
}

.welcome-card {
  max-width: 800px;
  margin: 0 auto;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.card-content {
  padding: 20px 0;
}
</style>