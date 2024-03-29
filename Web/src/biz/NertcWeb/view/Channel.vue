<!--
 * @FilePath: \前端\src\biz\NertcWeb\view\Channel.vue
 * @Author: zhangyang
 * @Date: 2023-03-23 17:34:52
 * @LastEditors: zhangxin
 * @LastEditTime: 2024-03-24 18:58:55
 * @Description:
-->
<script setup>
import { storeToRefs } from "pinia";
import { useUserStore } from "@/store/useUser";
import { usePopup } from "@/biz/Popup/usecase/usePopup";
import { useSocketDataStore, eventGroup } from '../store/useSocketData';

const socketDataStore = useSocketDataStore();
const { clientId } = storeToRefs(socketDataStore);

const popup = usePopup();
const userStore = useUserStore();
const { tokenUsable, tokenUnusable, userInfo } = storeToRefs(userStore);

const callDialog = popup.define({
    template: defineAsyncComponent(() => import("@/biz/NertcWeb/view/CallClient.vue")),
    title: '视频通话',
    showClose: false
});
const callUser = shallowRef({
    invite: "",
    wait: "",
    roomId: 0,
    userId: "",
    roomId: "",
});
const hasCall = computed(() => {
    if (Object.keys(callUser.value).length <= 0) return false;
    const values = Object.values(unref(callUser));
    return compact(values).length >= values.length;
});

function defineSocket() {
    if (tokenUnusable.value) return;
    socketDataStore.createSocket();
}

eventGroup['onOpen'].on((result) => {
    const { data } = result;
    socketDataStore.toClientId(data.clientId)
    const params = {
        "tp": "register",
        "data": {
            clientId: unref(clientId),
            userId: unref(userInfo)?.userid,
            userName: unref(userInfo)?.truename
        }
    }
    socketDataStore.registerSocket(params)
})
eventGroup['register'].on((result) => {
    console.warn(result, '注册成功~')
})

eventGroup['callto'].on((result) => {
    const { data } = result;
    callDialog.show({
        userId: unref(userInfo).userid,
        ...data
    });
})

eventGroup['Hangup'].on((result) => {
    callDialog.destroy()
})

eventGroup['userlist'].on((result) => {
    const { list } = result;
    socketDataStore.toCallList(list)
})

watch(
    tokenUnusable,
    (state) => {
        if (state) socketDataStore.releaseSocket();
    },
    { immediate: true }
);

watch(
    tokenUsable,
    (state) => {
        if (state) defineSocket();
    },
    { immediate: true }
);

onUnmounted(() => {
    socketDataStore.releaseSocket();
});
</script>

<template>
    <div class="channel-container">
        <slot></slot>
    </div>
</template>

<style scoped lang="scss">
.channel-container {
    width: 100%;
    height: 100%;
    max-height: 100%;
    background-color: transparent;
}
</style>
