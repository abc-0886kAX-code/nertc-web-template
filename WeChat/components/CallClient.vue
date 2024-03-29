<template>
  <view class="call-client">
  	<uni-popup ref="alertDialog" type="dialog">
			<uni-popup-dialog type="info" cancelText="挂断" confirmText="接听" title="视频通话来电" :content="content" @confirm="dialogConfirm"
				@close="dialogClose"></uni-popup-dialog>
		</uni-popup>
		
		
		<view class="call-client-mask" v-if="busy">
		    <WaitContainer v-if="wait"></WaitContainer>
		    <CallContainer v-else :userId="currentUser.userId" :roomId="currentUser.roomId"></CallContainer>
		</view>
  </view>
</template>

<script>
	import { mapState, mapActions,mapGetters,mapMutations } from 'vuex';
	import CallContainer from "@/components/CallContainer.vue";
	import WaitContainer from "@/components/WaitContainer.vue";
	export default {
		name: '',
		// import引入的组件需要注入到对象中才能使用
		components: {CallContainer,WaitContainer},
		props: {},
		data() {
			// 这里存放数据
			return {
				content:'',
				busy:false,
				currentUser:{
				    loading:false,
				},
				
				innerAudioContext:null
			}
		},
		// 监听属性 类似于data概念
		computed: {
			wait(){
			    return this.busy && this.currentUser.loading;
			}
		},
		// 监控data中的数据变化
		watch: {},
		// 生命周期 - 创建完成（可以访问当前this实例）
		onLoad(options) {
			
		},
		// 生命周期 - 页面展示（不可以访问DOM元素）
		onShow() {},
		// 生命周期 - 挂载完成（可以访问DOM元素）
		onReady() {},
		// 方法集合
		methods: {
			...mapActions([
			      'CallHangupSocket',
			]),
			dialogConfirm(){
				this.busy = true;
				
				const user = {
					"roomId": this.$store.state.lastMessageInfo.data.roomId,
					"userId": uni.getStorageSync('userid'),
				}
				this.currentUser = Object.assign(user,{loading:false});
				this.destroyAudio();
			},
			dialogClose(){
				this.CallHangupSocket({
				        "tp": "Hangup",
				        "data": {
				            "roomId": this.$store.state.lastMessageInfo.data.roomId,
				        }
				})
				this.$refs.alertDialog.close();
				uni.navigateBack({
					delta: 1
				})
			},
			createAudio(){
				this.destroyAudio();
				this.innerAudioContext = uni.createInnerAudioContext();
				this.innerAudioContext.autoplay = true;
				this.innerAudioContext.loop = true;
				this.innerAudioContext.src = 'https://fxdd.ytxd.com.cn:8043/music.mp3';
				this.innerAudioContext.onPlay(() => {
				  console.log('开始播放');
				});
			},
			destroyAudio(){
				if (this.innerAudioContext) {
				  try {
				    this.innerAudioContext.pause();
				    this.innerAudioContext.destroy()
				    this.innerAudioContext = null
				  } catch (e) {
				    //TODO handle the exception
				  }
				}
			}
		},
		onHide() {}, // 生命周期 - 监听页面隐藏
		onUnload() {}, // 生命周期 - 监听页面卸载
		mounted() {
			this.createAudio()
			this.$refs.alertDialog.open();
		},
		beforeDestroy(){
			this.destroyAudio();
		}
	}
</script>

<style lang='scss'>
	.call-client{
	    position: absolute;
	    top: 0%;
	    left: 0%;
	    z-index: 999;
	    width: 100%;
	    height: 100vh;
	    overflow: hidden;
			&-mask{
				position: absolute;
				top: 0%;
				left: 0%;
				width: 100%;
				height: 100%;
				overflow: hidden;
			}
	}
</style>