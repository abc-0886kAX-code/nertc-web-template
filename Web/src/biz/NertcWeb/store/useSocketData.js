/*
 * @FilePath: \前端\src\biz\NertcWeb\store\useSocketData.js
 * @Author: zhangxin
 * @Date: 2024-03-19 16:41:55
 * @LastEditors: zhangxin
 * @LastEditTime: 2024-03-26 17:52:19
 * @Description:
 */
import { defineStore } from 'pinia';
import { createEventHook } from "@vueuse/core";
const { DEV } = import.meta.env;

const WebSocketUrl = DEV ? `wss://${location.host}/trtc.io` : `wss://fxdd.ytxd.com.cn:8043/frx/websocket`;
// const WebSocketUrl = 'wss://fxdd.ytxd.com.cn:8043/frx/websocket';

const initEvent = createEventHook();
const registerEvent = createEventHook();
const calltoEvent = createEventHook();
const userlistEvent = createEventHook();
const callEvent = createEventHook();
const hangupEvent = createEventHook();
const joinEvent = createEventHook();

export const eventGroup = {
    'onOpen': initEvent,
    'register': registerEvent,
    'callto': calltoEvent,
    'userlist': userlistEvent,
    'call': callEvent,
    "Hangup": hangupEvent,
    "Join": joinEvent
}

const paths = ["userId"];

export const Namespace = 'useSocketData';

export const useSocketData = defineStore(Namespace, {
    state: () => ({
        clientId: null,
        callList: [],
        keyworld: +new Date(),
        socket: null,
        token: null,
        userId: null
    }),

    getters: {
        socketUnusable() {
            return isNil(this.socket);
        },
        socketUsable() {
            return !this.socketUnusable
        },
        filterCallList() {
            if (this.userId === null) return this.callList;
            return this.callList.filter((item) => item.userId !== this.userId);
        }
    },

    actions: {
        createSocket() {
            if (this.socketUsable) this.releaseSocket();
            this.socket = new WebSocket(WebSocketUrl);

            this.socket.onmessage = ({ data }) => {
                if (!data) return;
                const result = JSON.parse(data);
                const { tp } = result;
                if (!tp) return;
                eventGroup[tp].trigger(result);
            };
        },
        releaseSocket() {
            Object.keys(eventGroup).forEach((name) => {
                eventGroup[name].off();
            })

            if (this.socketUnusable) return;

            this.socket.close();
            this.socket = null;
        },
        registerSocket(params) {
            this.socket.send(JSON.stringify(params));
        },
        userlistSocket(params) {
            this.socket.send(JSON.stringify(params));
        },
        callSocket(params) {
            this.socket.send(JSON.stringify(params));
        },
        hangupSocket(params) {
            this.socket.send(JSON.stringify(params));
        },
        joinSocket(params) {
            this.socket.send(JSON.stringify(params));
        },
        toClientId(id) {
            this.clientId = id;
        },
        toCallList(list) {
            this.callList = list;
            this.keyworld = +new Date();
        },
        toToken(token) {
            this.token = token;
        },
        toUserId(id) {
            this.userId = id;
        }
    },
    persist: {
        key: Namespace,
        paths,
    },
});

export function useSocketDataStore() {
    return useSocketData();
}

export default {
    namespace: Namespace,
    store: useSocketData
};
