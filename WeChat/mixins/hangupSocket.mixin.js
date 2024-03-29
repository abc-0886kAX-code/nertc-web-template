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
			if(newType === 'Hangup'){
				uni.navigateBack({
					delta: 1
				})
			}
	  },
	},
	methods: {},
	onLoad() {}
}
