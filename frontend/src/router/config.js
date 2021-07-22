import LoginPage from "@/views/LoginPage";
import Signup from "@/views/Signup";
import Home from "@/views/Home";
import Illustration from "@/views/Illustration";
import Upload from "@/views/Upload";

export default {
    mode : 'history',
    routes : [
        {
            path: '/',
            redirect: '/home'
        },
        {
            path: '/login',
            name: 'Login',
            component: LoginPage
        },
        {
            path: '/signup',
            name: 'Signup',
            component: Signup
        },
        {
            path: '/home',
            name: 'home',
            component: Home
        },
        {
            path: '/illustrations',
            name: 'illustrations',
            component: Illustration
        },
        {
            path: '/upload',
            name: 'upload',
            component: Upload
        }
    ]
}