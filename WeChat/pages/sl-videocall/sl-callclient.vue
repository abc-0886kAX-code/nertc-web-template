<template>
	<view class="videocall">
		<CallClient></CallClient>
	</view>
</template>

<script>
		import CallClient from "@/components/CallClient.vue"
		export default {
			mixins:[],
			components:{CallClient}
		}
</script>

<style>
	.videocall{
			position: relative;
			top: 0%;
			left: 0%;
			z-index: 11;
			width: 100%;
			height: 100vh;
			overflow: hidden;
	}
</style>