<!--
 * @FilePath: \nertc-web-template\src\components\EmptyView.vue
 * @Author: abc-0886kAX-code
 * @Date: 2022-11-26 16:03:34
 * @LastEditors: abc-0886kAX-code
 * @LastEditTime: 2023-03-02 15:59:48
 * @Description:
-->
<script setup>
const props = defineProps({
    desc: {
        type: String,
        default: "抱歉，访问资源不存在",
    },
});
</script>

<template>
    <el-empty class="nertc-web-template-empty" :description="desc"></el-empty>
</template>

<style scoped lang="scss">
.nertc-web-template-empty {
    width: 100%;
    height: 100%;
}
</style>
