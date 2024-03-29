import { mapState, mapActions,mapGetters,mapMutations } from 'vuex';


export default {
	data() {
		return {}
	},
	computed: {
		tokenUsable(){
			return uni.getStorageSync('token') ? true : false
		},
		...mapState(["lastMessageType"])
	},
	watch: {
		
	  lastMessageType(newType) {
			if(newType === 'onOpen'){
				uni.setStorageSync('clientId',this.$store.state.lastMessageInfo.data.clientId)
				this.registerSocket();
			}
			
			if(newType === 'userlist'){
				this.CallToCallList(this.$store.state.lastMessageInfo.list)
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
		async initSocket(){
			await this.CallCreateSocket();
			
		},
		registerSocket(){
			this.CallToUserId(uni.getStorageSync('userid'))
			const data = {
			  "tp": "register",
			  "data": {
			      clientId: uni.getStorageSync('clientId'),
			      userId: uni.getStorageSync('userid'),
			      userName: uni.getStorageSync('truename')
				}
			}
			this.CallRegisterSocket(data)
		}
	},
	onLoad() {
		if(this.tokenUsable){
			this.initSocket()
		}
	}
}
