import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from '../views/ManageView.vue'

Vue.use(VueRouter)

const routes = [
    {
        // 2022年11月23日 添加路由
        path: '/',
        name: 'Manage',
        component: () => import('../views/ManageView.vue'),
        redirect: "/home",
        children: [
            {
                path: 'user',
                name: 'User',
                component: () => import('../views/UserView.vue'),
                meta: {
                    title: '用户管理'
                }
            },
            {
                path: 'home',
                name: 'Home',
                component: () => import('../views/HomeView.vue'),
                meta: {
                    title: '首页'
                }
            }
        ]
    },
    {
        path: '/about',
        name: 'about',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
    },
    {
        path: '/login',
        name: 'Login',
        component: () => import('../views/LoginView.vue'),
    },
    {
        path: '/register',
        name: 'Register',
        component: () => import('../views/RegView.vue'),
    }
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

router.beforeEach((to, from, next) => {
    localStorage.setItem("currentPathName", to.name)
    next()
})

export default router
