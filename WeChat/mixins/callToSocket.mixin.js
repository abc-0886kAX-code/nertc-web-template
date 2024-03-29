import { mapState, mapActions,mapGetters,mapMutations } from 'vuex';


export default {
	data() {
		return {
			isShowDialog:false,
		}
	},
	computed: {
		...mapState(["lastMessageType"]),
	},
	watch: {
	  lastMessageType(newType) {
			if(newType === 'callto'){
				this.callToSocket();
			}
	  },
	},
	methods: {
		...mapActions([
		      'CallCreateSocket',
		      'CallRegisterSocket',
					'CallToUserId',
					'CallToCallList'
		]),
		callToSocket(){
			this.isShowDialog = true;
			
			uni.navigateTo({
				url:'/pages_call/sl-videocall/sl-callclient'
			})
			// 出现弹框
		},
		closeDialog(){
			this.isShowDialog = false;
		}
	},
	onLoad() {}
}
