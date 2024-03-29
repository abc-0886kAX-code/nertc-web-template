import Vue from 'vue'
import Vuex from 'vuex'
import wsRequest from '../untils/websocket.js'

const WebSocketUrl = "wss://fxdd.ytxd.com.cn:8043/frx/websocket"
// const WebSocketUrl = "ws://192.168.3.6:8006/frx/websocket"

Vue.use(Vuex)

const store = new Vuex.Store({
	state: {
		clientId: null,
		callList: [],
		keyworld: +new Date(),
		socket: null,
		token: null,
		userId: null,
		lastMessageType:null,
		lastMessageInfo:{}
			//公共的变量，这里的变量不能随便修改，只能通过触发mutations的方法才能改变
	},
	getters:{
		socketUsable:(state) =>{
				return state.socket ? true : false;
		},
		filterCallList:(state) =>{
				if (state.userId === null) return state.callList;
				return state.callList.filter((item) => item.userId !== state.userId);
		}
		    
	},
	mutations: {
		createSocket(state) {
			if (state.socket) this.commit('releaseSocket');
			state.socket = uni.connectSocket({
				url: WebSocketUrl,
				success:()=>{
					console.log("正准备建立websocket中...");
					// 返回实例
					return state.socket
				},
			});
			
			
			
			
			state.socket.onMessage(({data})=>{
				if (!data) return;
				const result = JSON.parse(data);
				const { tp } = result;
				if (!tp) return;
				if(state.lastMessageType === tp){
					
					state.lastMessageType = null;
					
					Vue.nextTick(() => {
					  state.lastMessageType = tp;
					});
				}else{
					state.lastMessageType = tp;
				}
				
				state.lastMessageInfo = result;
				// eventGroup[tp].trigger(result);
				// 将数据存储到state 并改变指定tp的key 外部vue监听这个key
			})
		},
		releaseSocket(state) {
				if (!state.socket) return;
				state.socket.close();
				state.socket = null;
		},
		registerSocket(state,params) {
				state.socket.send({
					data: JSON.stringify(params),
					async success() {},
				});
		},
		userlistSocket(state,params) {
				state.socket.send({
					data: JSON.stringify(params),
					async success() {},
				});
		},
		callSocket(state,params) {
				state.socket.send({
					data: JSON.stringify(params),
					async success() {},
				});
		},
		hangupSocket(state,params) {
				state.socket.send({
					data: JSON.stringify(params),
					async success() {},
				});
		},
		joinSocket(state,params) {
				state.socket.send({
					data: JSON.stringify(params),
					async success() {},
				});
		},
		toClientId(state,id) {
				state.clientId = id;
		},
		toCallList(state,list) {
				state.callList = list;
				state.keyworld = +new Date();
		},
		toToken(state,token) {
				state.token = token;
		},
		toUserId(state,id) {
				state.userId = id;
		},
		destorySocket(state){
			if(state.socket){
				state.socket.close();
				state.socket = null;
			}
		}
	},
	actions: {
		//相当于异步的操作,不能直接改变state的值，只能通过触发mutations的方法才能改变
		CallCreateSocket (context) {
				context.commit('createSocket')
		},
		CallReleaseSocket(context) {
				context.commit('releaseSocket')
		},
		CallRegisterSocket(context,params) {
				context.commit('registerSocket',params)
		},
		CallUserlistSocket(context,params) {
				context.commit('userlistSocket',params)
		},
		CallCallSocket(context,params) {
				context.commit('callSocket',params)
		},
		CallHangupSocket(context,params) {
				context.commit('hangupSocket',params)
		},
		CallJoinSocket(context,params) {
				context.commit('joinSocket',params)
		},
		CallToClientId(context,params) {
				context.commit('toClientId',params)
		},
		CallToCallList(context,params) {
				context.commit('toCallList',params)
		},
		CallToToken(context,params) {
				context.commit('toToken',params)
		},
		CallToUserId(context,params) {
				context.commit('toUserId',params)
		},
		DestorySocket(context){
			context.commit('destorySocket')
		}
	}
})

export default store;