<template>
    <view class="wait-container">
       
    </view>
</template>

<script>
    export default {
        name:"WaitContainer",
        data() {
            return {
                
            };
        }
    }
</script>

<style scoped lang="scss">
    .wait-container{
        position: absolute;
        top: 0%;
        left: 0%;
        z-index: 15;
        width: 100%;
        height: 100vh;
		background: url("@/static/call-banner.png");
		background-repeat: no-repeat;
		background-size: cover;
        overflow: hidden;
    }
</style>