<script setup>
import useNertcComm from "../usecase/useNertcComm.js";
import { useSocketDataStore } from '@/biz/NertcWeb/store/useSocketData';
import { useDialog } from "@/biz/Popup/usecase/useDialog";

const socketDataStore = useSocketDataStore();
const props = defineProps({
    popupKeyword: String,
});
const dialog = useDialog(props.popupKeyword);
const config = computed(() => unref(dialog.config));


const { SmallRef, LargeRef, isSilence, isStop, remoteStreams, setOrRelieveSilence, stopOrOpenVideo } = useNertcComm(unref(config));

function handleOver() {
    socketDataStore.hangupSocket({
        "tp": "Hangup",
        "data": {
            "roomId": unref(config).roomId,
        }
    })
    dialog.destroy()
}

</script>

<template>
    <div class="wrapper">
        <div class="content">
            <!--画面div-->
            <div class="main-window" ref="LargeRef"></div>
            <div class="sub-window-wrapper">
                <!--小画面div-->
                <template v-if="remoteStreams.length">
                    <div v-for="item in remoteStreams" :key="item.getId()" class="sub-window" ref="SmallRef"
                        :data-uid="item.getId()"></div>
                </template>
                <div v-else class="sub-window" ref="SmallRef">
                    <span class="loading-text">等待对方加入…</span>
                </div>
            </div>
        </div>
        <!--底层栏-->
        <ul class="tab-bar">
            <li :class="{ silence: true, isSilence }" @click="setOrRelieveSilence"></li>
            <li class="over" @click="handleOver"></li>
            <li :class="{ stop: true, isStop }" @click="stopOrOpenVideo"></li>
        </ul>
    </div>
</template>

<style scoped lang='scss'>
.wrapper {
    height: 100%;
    background-image: linear-gradient(179deg, #141417 0%, #181824 100%);
    display: flex;
    flex-direction: column;

    .content {
        flex: 1;
        display: flex;
        position: relative;

        .main-window {
            position: absolute;
            top: 16px;
            right: 16px;
            z-index: 9;
            width: 165px;
            height: 92px;
            background: #25252d;
            border: 1px solid #ffffff;
        }

        .sub-window-wrapper {
            height: 100%;
            width: 67vh;
            margin: 0 auto;
            background: #25252d;
        }

        .sub-window {
            background: #25252d;
            width: 100%;
            height: 100%;
            text-align: center;

            .loading-text {
                display: block;
                width: 100%;
                text-align: center;
                line-height: 90px;
                font-size: 12px;
                color: #fff;
                font-weight: 400;
            }
        }
    }

    .tab-bar {
        height: 54px;
        background-image: linear-gradient(180deg, #292933 7%, #212129 100%);
        box-shadow: 0 0 0 0 rgba(255, 255, 255, 0.3);
        list-style: none;
        display: flex;
        justify-content: center;
        align-items: center;
        color: #fff;

        li {
            height: 54px;
            width: 125px;
            cursor: pointer;

            //静音
            &.silence {
                background: url("@/assets/images/comm-icon/silence.png") no-repeat center;
                background-size: 60px 54px;

                &:hover {
                    background: url("@/assets/images/comm-icon/silence-hover.png") no-repeat center;
                    background-size: 60px 54px;
                }

                &:active {
                    background: url("@/assets/images/comm-icon/silence-click.png") no-repeat center;
                    background-size: 60px 54px;
                }

                &.isSilence {
                    //已经开启静音
                    background: url("@/assets/images/comm-icon/relieve-silence.png") no-repeat center;
                    background-size: 60px 54px;

                    &:hover {
                        background: url("@/assets/images/comm-icon/relieve-silence-hover.png") no-repeat center;
                        background-size: 60px 54px;
                    }

                    &:active {
                        background: url("@/assets/images/comm-icon/relieve-silence-click.png") no-repeat center;
                        background-size: 60px 54px;
                    }
                }
            }

            //结束按钮
            &.over {
                background: url("@/assets/images/comm-icon/over.png") no-repeat center;
                background-size: 68px 36px;

                &:hover {
                    background: url("@/assets/images/comm-icon/over-hover.png") no-repeat center;
                    background-size: 68px 36px;
                }

                &:active {
                    background: url("@/assets/images/comm-icon/over-click.png") no-repeat center;
                    background-size: 68px 36px;
                }
            }

            // 停止按钮
            &.stop {
                background: url("@/assets/images/comm-icon/stop.png") no-repeat center;
                background-size: 60px 54px;

                &:hover {
                    background: url("@/assets/images/comm-icon/stop-hover.png") no-repeat center;
                    background-size: 60px 54px;
                }

                &:active {
                    background: url("@/assets/images/comm-icon/stop-click.png") no-repeat center;
                    background-size: 60px 54px;
                }

                //已经是停止状态
                &.isStop {
                    background: url("@/assets/images/comm-icon/open.png") no-repeat center;
                    background-size: 60px 54px;

                    &:hover {
                        background: url("@/assets/images/comm-icon/open-hover.png") no-repeat center;
                        background-size: 60px 54px;
                    }

                    &:active {
                        background: url("@/assets/images/comm-icon/open-click.png") no-repeat center;
                        background-size: 60px 54px;
                    }
                }
            }
        }
    }
}
</style>
