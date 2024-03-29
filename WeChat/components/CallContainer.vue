<template>
    <view class="call-container">
        <web-view v-if="usableSrc" class="call-container-view" :src="callSrc" @message="message"></web-view>
    </view>
</template>

<script>
	import  HangupSocketMixin from "@/mixins/hangupSocket.mixin.js"
	import { mapState, mapActions,mapGetters,mapMutations } from 'vuex';
	 function setupSrc(userId,roomId){
	        return `https://fxdd.ytxd.com.cn:8043/#/h5-video-call?userId=${userId}&roomId=${roomId}`;
	        // return `http://192.168.3.7:5173/#/h5-video-call?userId=${userId}&roomId=${roomId}`;
	        // return `https://bjsw.ytxd.com.cn:3830/index.html`;
	    }
			
			
	
    export default {
        name:"CallContainer",
				mixins:[HangupSocketMixin],
        props:{
            userId: [String, Number],
            roomId: [String, Number],
        },
        data() {
            return {
							 callSrc:""
						};
        },
        computed:{
					usableSrc(){
							return this.callSrc.length > 0;
					}
				},
        methods:{
					...mapActions([
								'CallHangupSocket',
					]),
					message(e){
						console.log('结束');
					}
				},
        watch:{},
        mounted(){
					console.log(this.userId);
					console.log(this.roomId);
					this.callSrc = setupSrc(this.userId,this.roomId);
				},
        destroyed(){
					this.callSrc = "";
					this.CallHangupSocket({
					        "tp": "Hangup",
					        "data": {
					            "roomId": this.roomId,
					        }
					    })
				}
    }
</script>

<style scoped lang="scss">
    .call-container{
        position: absolute;
        top: 0%;
        left: 0%;
        z-index: 15;
        width: 100%;
        height: 100%;
        background-color: hotpink;
        overflow: hidden;
        &-view{
            width: 100%;
            height: 100%;
        }
    }
</style>