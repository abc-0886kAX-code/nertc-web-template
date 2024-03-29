<script setup>
import CallList from "@/biz/NertcWeb/view/CallList.vue";
</script>

<template>
    <div class="communication">
        <CallList class="communication-body"></CallList>
    </div>
</template>

<style scoped lang='scss'>
.communication {
    width: 100%;
    height: 100%;
    overflow: hidden;
    background: url('@/assets/images/background.webp') no-repeat;
    background-size: 100% 100%;

    &-body {
        width: 70%;
        height: 60%;
        margin: auto;
    }
}
</style>
