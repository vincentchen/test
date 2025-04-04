import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    redirect: '/dashboard'
  },
  {
    path: '/dashboard',
    name: 'Dashboard',
    component: () => import('@/views/dashboard/index.vue'),
    meta: { title: '仪表盘' }
  },
  {
    path: '/workflow',
    name: 'Workflow',
    redirect: '/workflow/design',
    children: [
      {
        path: 'design',
        name: 'WorkflowDesign',
        component: () => import('@/views/workflow/design/index.vue'),
        meta: { title: '流程设计' }
      },
      {
        path: 'instance',
        name: 'WorkflowInstance',
        component: () => import('@/views/workflow/instance/index.vue'),
        meta: { title: '流程实例' }
      },
      {
        path: 'task',
        name: 'WorkflowTask',
        component: () => import('@/views/workflow/task/index.vue'),
        meta: { title: '任务管理' }
      }
    ]
  },
  {
    path: '/system',
    name: 'System',
    redirect: '/system/user',
    children: [
      {
        path: 'user',
        name: 'SystemUser',
        component: () => import('@/views/system/user/index.vue'),
        meta: { title: '用户管理' }
      },
      {
        path: 'role',
        name: 'SystemRole',
        component: () => import('@/views/system/role/index.vue'),
        meta: { title: '角色管理' }
      },
      {
        path: 'permission',
        name: 'SystemPermission',
        component: () => import('@/views/system/permission/index.vue'),
        meta: { title: '权限管理' }
      }
    ]
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/login/index.vue'),
    meta: { title: '登录' }
  },
  {
    path: '/:pathMatch(.*)*',
    name: 'NotFound',
    component: () => import('@/views/error/404.vue'),
    meta: { title: '404' }
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  // 设置页面标题
  document.title = `${to.meta.title} - Workflow Admin`
  
  // 检查是否需要登录
  const token = localStorage.getItem('token')
  if (to.path !== '/login' && !token) {
    next('/login')
  } else {
    next()
  }
})

export default router