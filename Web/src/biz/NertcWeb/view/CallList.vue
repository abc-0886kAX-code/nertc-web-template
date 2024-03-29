<!--
 * @FilePath: \前端\src\biz\NertcWeb\view\CallList.vue
 * @Author: zhangxin
 * @Date: 2023-11-16 15:34:41
 * @LastEditors: zhangxin
 * @LastEditTime: 2024-03-25 10:27:03
 * @Description:
-->
<script setup>
import { useSocketDataStore, eventGroup } from '@/biz/NertcWeb/store/useSocketData';
import { useUserStore } from "@/store/useUser";
const userStore = useUserStore();
const { userInfo } = storeToRefs(userStore);

const socketDataStore = useSocketDataStore();
const { filterCallList, keyworld, callSocket, token } = storeToRefs(socketDataStore);

import { usePopup } from "@/biz/Popup/usecase/usePopup";
const popup = usePopup();
const dialog = popup.define({
    width: "50%",
    template: defineAsyncComponent(() => import("./CallContainer.vue")),
    title: '视频通话',
    showClose: false
});

const tableColumn = [
    {
        prop: 'userId',
        label: 'id',
    },
    {
        prop: 'userName',
        label: '用户名',
    },
    {
        prop: 'call',
        label: '通话',
    }
]



const multipleTableRef = ref(null);
const multipleSelection = ref([])
function toggleSelection() {
    if (multipleSelection.value.length === 0) {
        ElMessage.error('当前没有选择用户')
        return;
    }
    unref(multipleTableRef).clearSelection()
}
function handleSelectionChange(val) {
    multipleSelection.value = val
}

function multiPersonCall() {
    if (multipleSelection.value.length <= 0) {
        ElMessage.error('请选择用户')
        return;
    }
    handleEdit(0, unref(multipleSelection)[0])
}

eventGroup['call'].on((result) => {
    const { data } = result;

    if (multipleSelection.value.length > 1) {
        const sliceArr = unref(multipleSelection).slice(1);
        sliceArr.forEach((item) => {
            socketDataStore.joinSocket({
                "tp": "Join",
                "data": {
                    "roomId": data.roomId,
                    "clientId": item.clientId
                }
            })
        })
    }

    dialog.show({
        userId: unref(userInfo).userid,
        ...data,
        token: unref(token)
    });
})

function handleEdit(index, row) {
    const { userId, userName, clientId, token } = row;
    if (!userId) return;

    socketDataStore.callSocket({
        "tp": "call",
        "data": {
            "userId": userId,
            "userName": userName,
            "clientId": clientId
        }
    })
    socketDataStore.toToken(token)
}


// 这个应该是没有用了
eventGroup['Hangup'].on((result) => {
    dialog.destroy()
})
</script>

<template>
    <div class="call-list">
        <!-- <div class="call-list-console">
            <el-button type="primary" @click="toggleSelection">清空</el-button>
            <el-button type="primary" @click="multiPersonCall">多人通话</el-button>
        </div> -->
        <el-table class="call-list-body" ref="multipleTableRef" :key="keyworld" :data="filterCallList"
            @selection-change="handleSelectionChange">
            <!-- <el-table-column type="selection" width="55" /> -->
            <template v-for="item in tableColumn">
                <el-table-column v-if="item.prop !== 'call'" :prop="item.prop" :label="item.label" align="center" />
                <el-table-column v-else align="center" label="操作">
                    <template #default="scope">
                        <IconEpPhone class="phone" @click="handleEdit(scope.$index, scope.row)" />
                    </template>
                </el-table-column>
            </template>

        </el-table>
    </div>
</template>

<style scoped lang="scss">
@import "@/assets/style/table.scss";

.call-list {
    width: 100%;
    height: 100%;

    &-console {
        width: 100%;
        height: 50px;
    }

    &-body {
        width: 100%;
        // height: calc(100% - 50px);
        height: 100%;
    }
}

.phone {
    font-size: 24px;
}

.phone:hover {
    cursor: pointer;
    color: #409EFF;
    font-size: 28px;
}
</style>
