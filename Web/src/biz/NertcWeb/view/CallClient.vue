<!--
 * @FilePath: \前端\src\biz\NertcWeb\view\CallClient.vue
 * @Author: zhangxin
 * @Date: 2023-03-23 21:01:01
 * @LastEditors: zhangxin
 * @LastEditTime: 2024-03-19 19:35:20
 * @Description:
-->
<script setup>
import CallContainer from "./CallContainer.vue";
import { useSocketDataStore } from '@/biz/NertcWeb/store/useSocketData';
import { useDialog } from "@/biz/Popup/usecase/useDialog";
import { mergeObject } from "~/shared/merge";

const socketDataStore = useSocketDataStore();
const props = defineProps({
    popupKeyword: String,
});
const dialog = useDialog(props.popupKeyword);

const call = shallowRef({});

const config = computed(() => {
    return unref(dialog.config);
});
const hasRoomId = computed(() => {
    return !isNil(unref(config)?.roomId);
});

const usableCall = computed(() => {
    return unref(hasRoomId) && unref(accept);
});
const accept = ref(false);
function toreject() {
    socketDataStore.hangupSocket({
        "tp": "Hangup",
        "data": {
            "roomId": unref(config).roomId,
        }
    })
    dialog.destroy()
}

function toaccept() {
    accept.value = true;
}
</script>

<template>
    <div class="call-client">
        <div class="call-client-control" v-if="hasRoomId">
            <div class="call-client-control-item call-client-control-refuse" @click="toreject">拒绝</div>
            <div class="call-client-control-item call-client-control-agree" @click="toaccept">同意</div>
        </div>
        <div class="call-client-container" v-if="usableCall">
            <CallContainer :popupKeyword="popupKeyword"></CallContainer>
        </div>
    </div>
</template>

<style scoped lang="scss">
.call-client {
    position: relative;
    top: 0;
    left: 0;
    z-index: 11;
    width: 100%;
    height: 100%;
    overflow: hidden;

    &-control {
        position: absolute;
        bottom: 12%;
        left: 0;
        z-index: 13;
        display: flex;
        justify-content: space-around;
        align-items: center;
        width: 100%;
        height: 48px;

        &-item {
            width: 200px;
            height: 60px;
            border-radius: 50px;
            display: flex;
            justify-content: center;
            align-items: center;
            font-size: 30px;
            color: #fff;
            font-family: "ShiShang";
        }

        &-refuse {
            background: url("@/assets/images/comm-icon/hangup.png") no-repeat;

            background-position: 10px;
            background-color: #f10537;
        }

        &-agree {
            background: url("@/assets/images/comm-icon/answer.png") no-repeat;

            background-position: 10px;
            background-color: #0bdc84;
        }
    }

    &-container {
        position: absolute;
        top: 0;
        left: 0;
        z-index: 15;
        width: 100%;
        height: 100%;
        overflow: hidden;
    }
}
</style>
