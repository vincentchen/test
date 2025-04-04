# 后台管理系统

基于Java和Vue3的后台管理系统，集成Flowable工作流引擎和BPMN.js流程设计器。

## 项目结构

项目采用前后端分离架构：

- `backend/`: Spring Boot后端项目
  - 集成Flowable工作流引擎
  - RESTful API接口
  - 权限管理
  - 数据库操作

- `frontend/`: Vue3前端项目
  - 基于Element Plus的UI组件
  - 集成BPMN.js流程设计器
  - Vuex状态管理
  - 模块化的代码组织

## 主要功能

- 用户权限管理
- 工作流程设计
- 流程部署和执行
- 任务管理

## 技术栈

### 后端
- Java 11
- Spring Boot 2.7.x
- Flowable 6.x
- MySQL 8.0
- MyBatis-Plus

### 前端
- Vue 3
- Element Plus
- BPMN.js
- Vuex 4
- Vue Router 4
- Axios

## 开发环境要求

- JDK 11+
- Node.js 16+
- Maven 3.6+
- MySQL 8.0+

## 启动说明

### 后端服务
1. 进入backend目录
2. 执行 `mvn spring-boot:run`

### 前端服务
1. 进入frontend目录
2. 执行 `npm install`
3. 执行 `npm run dev`