<template>
	<view class="videocall">
		<view class="videocall-head">
		      <view class="videocall-head-item videocall-head-name">
			<image src="@/static/call-wx.png" alt="">
			<a>微信通话</a>
		</view>
		      <view class="videocall-head-item videocall-head-size">
			<image src="@/static/call-zx.png" alt="">
			<a>在线：{{userSize}}</a>
		</view>
		  </view>
		  <view class="videocall-body">
		      <template v-for="(user,index) in filterCallList">
		          <view :key="index" class="videocall-body-user">
		              <view class="videocall-body-user-name">
		                  {{user.userName}}
		              </view>
		              <view class="videocall-body-user-state">
		                  {{user.userId}}
		              </view>
		              <u-button class="videocall-body-user-call" @click="toCall(user)">通话</u-button>
		          </view>
		      </template>
		  </view>
			<view class="videocall-mask" v-if="busy">
			    <CallContainer :userId="currentUser.userId" :roomId="currentUser.roomId"></CallContainer>
			</view>
    </view>
</template>

<script>
		import { mapState, mapActions,mapGetters,mapMutations } from 'vuex';
		import CallContainer from "@/components/CallContainer.vue";
		import WaitContainer from "@/components/WaitContainer.vue";
    export default {
        components:{CallContainer,WaitContainer},
        data(){
            return {
							busy:false,
							currentUser:{
							    loading:false,
							},
						}
        },
        computed:{
					...mapGetters(['filterCallList']),
					...mapState(["lastMessageType"]),
					userSize(){
						return this.filterCallList.length ?? 0
					},
					wait(){
					    return this.busy && this.currentUser.loading;
					},
				},
				watch: {
				  lastMessageType(newType) {
						if(newType === 'userlist'){
							this.CallToCallList(this.$store.state.lastMessageInfo.list)
						}
						
						if(newType === 'call'){
							this.createRoom(this.$store.state.lastMessageInfo)
						}
				  },
				},
        methods:{
					...mapActions([
								'CallToCallList',
								'CallCallSocket',
								'CallJoinSocket'
					]),
					toCall(user){
								const { userId, userName, clientId, token } = user;
						    if (!userId) return;
								
						    this.CallCallSocket({
						        "tp": "call",
						        "data": {
						            "userId": userId,
						            "userName": userName,
						            "clientId": clientId
						        }
						    })
								
						    this.currentUser = {
									loading:false,
									userId:uni.getStorageSync('userid')
								};
					},
					createRoom(result){
						const { data } = result;
						// this.CallJoinSocket({
						// 		"tp": "Join",
						// 		"data": {
						// 				"roomId": data.roomId,
						// 				"clientId": this.currentUser.clientId
						// 		}
						// })
						this.currentUser = Object.assign(this.currentUser,{roomId:data.roomId,loading:true});
						this.busy = true;
					}
				},
        onShow:function(){},
        onLoad:function(){},
        onUnload:function(){}
    }
</script>

<style scoped lang="scss">
	.videocall{
			position: relative;
			top: 0%;
			left: 0%;
			z-index: 11;
			width: 100%;
			height: 100vh;
			overflow: hidden;
			&-head{
					display: flex;
					justify-content: space-around;
					align-items: center;
					width: 100%;
					height: 164rpx;
					margin-top: 20rpx;
					margin-bottom: 50rpx;
					overflow: hidden;
				& image{
					width: 70rpx;
					height: 70rpx;
					display: block;
					margin: auto;
					margin-top: 20rpx;
					margin-bottom: 10rpx;
				}
				&-item{
					height: 100%;
					font-size: 28rpx;
					background: linear-gradient(to bottom, #07D078, #0EB26A);
					padding-left: 30rpx;
					padding-right: 30rpx;
					color: #fff;
					border-radius: 50%;
				}
      }
			&-body{
					width: 100%;
					height: calc(100% - 250rpx);
					overflow-y:auto;
					color: #fff;
				&-user{
					border: 1px solid #07D078;
					width: 90%;
					margin: auto;
					margin-bottom: 30rpx;
					border-radius: 20rpx;
					padding: 10rpx;
					background: linear-gradient(to bottom, #07D078, #0EB26A);
					
					&-name{
						height: 70rpx;
						line-height: 70rpx;
						background-image: url("@/static/call-ry.png");
						background-repeat: no-repeat;
						background-position: 10rpx;
						padding-left: 70rpx;
					}
					&-state{
						height: 70rpx;
						line-height: 70rpx;
						background-image: url("@/static/id.png");
						background-repeat: no-repeat;
						background-position: 10rpx;
						padding-left: 70rpx;
					}
					}
        }
        &-mask{
            position: absolute;
            top: 0%;
            left: 0%;
            z-index: 13;
            width: 100%;
            height: 100%;
            overflow: hidden;
        }
	}
</style>