<!--
 * @FilePath: \Web\src\layout\Home.vue
 * @Author: abc-0886kAX-code
 * @Date: 2023-04-12 13:14:28
 * @LastEditors: abc-0886kAX-code
 * @LastEditTime: 2024-03-29 10:15:47
 * @Description:
-->
<script setup>
import { useUserStore } from "@/store/useUser";
const { proxy } = getCurrentInstance();
const user = useUserStore();
</script>

<template>
    <el-container class="home">
        <RouterView v-slot="{ Component }">
            <transition name="el-fade-in-linear">
                <component :is="Component" />
            </transition>
        </RouterView>
    </el-container>
</template>

<style scoped lang="scss">
.home {
    background-color: transparent;
    width: 100%;
    height: 100%;
    overflow: hidden;
}
</style>
