<template>
  <view class='index'>
		<view @click="videocallClick" class="mh-modular">
			<span><img src="/static/xcx-spth.png" alt="" /></span>
			<a>视频通话</a>
		</view>
  </view>
</template>

<script>
	import RegisterSocketMiXin from "@/mixins/registerSocket.mixin.js"
	import CallToSocketMiXin from "@/mixins/callToSocket.mixin.js"
	export default {
		name: 'index',
		// import引入的组件需要注入到对象中才能使用
		mixins: [RegisterSocketMiXin, CallToSocketMiXin],
		components: {},
		props: {},
		data() {
			// 这里存放数据
			return {}
		},
		// 监听属性 类似于data概念
		computed: {},
		// 监控data中的数据变化
		watch: {},
		// 生命周期 - 创建完成（可以访问当前this实例）
		onLoad(options) {},
		// 生命周期 - 页面展示（不可以访问DOM元素）
		onShow() {},
		// 生命周期 - 挂载完成（可以访问DOM元素）
		onReady() {},
		// 方法集合
		methods: {
			// 视频监控点击事件
			videocallClick() {
				uni.navigateTo({
					url: '/pages/sl-videocall/sl-videocall'
				});
			},
		},
		onHide() {}, // 生命周期 - 监听页面隐藏
		onUnload() {}, // 生命周期 - 监听页面卸载
	}
</script>

<style lang='scss'>
	.index{
		width: 100%;
		height: 100%;
	}
</style>