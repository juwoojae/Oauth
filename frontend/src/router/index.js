import { createRouter, createWebHistory } from 'vue-router'
import MemberCreate from '@/components/MemberCreate.vue'
import MemberLogin from '@/components/MemberLogin.vue'

const routes = [

    {
        path: '/member/create',
        component: MemberCreate
    },
    {
        path: '/member/login',
        component: MemberLogin
    }
]
// localhost:3000/member/create 으로 라우팅 된다.
const router = createRouter(
    {
        history: createWebHistory(),
        routes
    }
)

export default router;