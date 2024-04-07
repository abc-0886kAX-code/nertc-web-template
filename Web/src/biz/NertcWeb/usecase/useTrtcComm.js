/*
 * @FilePath: \前端\src\biz\NertcWeb\usecase\useTrtcComm.js
 * @Author: zhangxin
 * @Date: 2024-04-07 13:06:00
 * @LastEditors: zhangxin
 * @LastEditTime: 2024-04-07 14:14:17
 * @Description:
 */
import { ElMessage } from 'element-plus';
import TRTC from 'trtc-sdk-v5';


const sdkAppId = 1600029979;
const sdkSecretKey = 'f0a6743d4777f900864a62638775e14f84c6d4c8b558a9ade8fa844ab7111f09'

function genTestUserSig({ sdkAppId, userId, sdkSecretKey }) {
    const SDKAPPID = sdkAppId;

    const EXPIRETIME = 604800;

    const SDKSECRETKEY = sdkSecretKey;

    // a soft reminder to guide developer to configure sdkAppId/sdkSecretKey
    if (SDKAPPID == undefined || SDKSECRETKEY === '') {
        alert(
            '请先配置好您的账号信息： SDKAPPID 及 SDKSECRETKEY ' +
            '\r\n\r\nPlease configure your SDKAPPID/SDKSECRETKEY in js/debug/GenerateTestUserSig.js'
        );
    }
    const generator = new window.LibGenerateTestUserSig(SDKAPPID, SDKSECRETKEY, EXPIRETIME);
    const userSig = generator.genTestUserSig(userId);
    return {
        sdkAppId: SDKAPPID,
        userSig: userSig
    };
}

export function useTrtcComm(config) {
    const { userId: localUid, username, roomId: channelName } = config;

    const isSilence = ref(false);
    const isStop = ref(false);
    const client = ref(null);
    const remoteStreams = ref([]);

    const SmallRef = ref(null);
    const LargeRef = ref(null);

    const trtc = TRTC.create();


    const cameraList = ref([]);
    const microphoneList = ref([]);
    const videoDeviceId = ref('');
    const audioDeviceId = ref('');

    // 初始化设备音频以及视频
    async function updateDevice() {
        console.log('updateDevice');
        const cameraItems = await TRTC.getCameraList();
        cameraItems.forEach((item) => { item.value = item.deviceId; });
        const microphoneItems = await TRTC.getMicrophoneList();
        microphoneItems.forEach((item) => { item.value = item.deviceId; });

        cameraList.value = cameraItems;
        microphoneList.value = microphoneItems;

        if (!unref(videoDeviceId)) {
            videoDeviceId.value = cameraItems[0].deviceId;
        }

        if (!unref(audioDeviceId)) {
            audioDeviceId.value = microphoneItems[0].deviceId;
        }
    };

    navigator.mediaDevices.getUserMedia({ audio: true, video: true }).then((stream) => {
        stream.getTracks().forEach((track) => { track.stop(); });
        updateDevice();
    }).catch(() => {
        ElMessage({ message: '请授权您的麦克风和摄像头权限', type: 'error' });
    });

    navigator.mediaDevices.ondevicechange = updateDevice;


    // 进入房间
    async function handleEnter() {
        try {
            await trtc.enterRoom({
                roomId: parseInt(channelName, 10),
                sdkAppId: parseInt(sdkAppId, 10),
                userId: localUid,
                userSig: genTestUserSig({ sdkAppId: sdkAppId, userId: localUid, sdkSecretKey: sdkSecretKey }).userSig,
            });
            installEventHandlers();
            await handleStartLocalAudio();
            await handleStartLocalVideo();
        } catch (error) {
            console.error(error.message);
        }
    }

    // 采集麦克风
    async function handleStartLocalAudio() {
        try {
            await trtc.startLocalAudio({
                option: {
                    microphoneId: unref(audioDeviceId),
                },
            });
            isSilence.value = false;
            console.warn('Local audio started successfully');
        } catch (error) {
            console.error(`Local audio is failed to started. Error: ${error.message}`);
        }
    }
    // 终止采集麦克风 - 应该是不需要的
    async function handleStopLocalAudio() {
        try {
            await trtc.stopLocalAudio();
            console.warn('Local audio stopped successfully');
        } catch (error) {
            console.error(`Local audio is failed to stopped. Error: ${error.message}`);
        }
    }
    // 采集摄像头
    async function handleStartLocalVideo() {
        try {
            await trtc.startLocalVideo({
                view: unref(LargeRef),
                option: {
                    cameraId: unref(videoDeviceId),
                    profile: '1080p',
                },
            });
            isStop.value = false;
            console.warn('Local audio stopped successfully');
        } catch (error) {
            console.error(`Local audio is failed to stopped. Error: ${error.message}`);
        }
    }
    // 终止采集摄像头
    async function handleStopLocalVideo() {
        try {
            await trtc.stopLocalVideo();
            console.warn('Local audio stopped successfully');
        } catch (error) {
            console.error(`Local audio is failed to stopped. Error: ${error.message}`);
        }
    }
    // 离开房间
    async function handleExit() {
        try {
            uninstallEventHandlers();
            await trtc.exitRoom();
            await trtc.stopLocalVideo();
            await trtc.stopLocalAudio();
            remoteStreams.value = [];
            console.warn('Exit room success');
        } catch (error) {
            roomStatus.value = 'entered';
            console.error(`Exit room failed. Error: ${error.message}`);
        }
        // 判断是否开启麦克风、摄像头、屏幕共享，开启则执行终止函数
        handleStopLocalAudio();
        handleStopLocalVideo();
    }



    async function setOrRelieveSilence() {
        isSilence.value = !unref(isSilence);
        try {
            if (unref(isSilence)) {
                await trtc.updateLocalAudio({ mute: true });
                console.warn('打开mic');
            } else {
                await trtc.updateLocalAudio({ mute: false });
                console.warn('关闭mic');

            }
        } catch (error) {
            console.error(`Mute audio error: ${error.message}`);
        }
    }

    async function stopOrOpenVideo() {
        isStop.value = !unref(isStop);
        try {
            if (unref(isStop)) {
                await trtc.updateLocalVideo({ mute: true });
                console.warn('打开摄像头');
            } else {
                await trtc.updateLocalVideo({ mute: false });
                console.warn('关闭摄像头');
            }
        } catch (error) {
            console.error(`Mute video error: ${error.message}`);
        }
    }


    function installEventHandlers() {
        trtc.on(TRTC.EVENT.ERROR, handleError);
        trtc.on(TRTC.EVENT.KICKED_OUT, handleKickedOut);
        trtc.on(TRTC.EVENT.REMOTE_USER_ENTER, handleRemoteUserEnter);
        trtc.on(TRTC.EVENT.REMOTE_USER_EXIT, handleRemoteUserExit);
        trtc.on(TRTC.EVENT.REMOTE_VIDEO_AVAILABLE, handleRemoteVideoAvailable);
        trtc.on(TRTC.EVENT.REMOTE_VIDEO_UNAVAILABLE, handleRemoteVideoUnavailable);
        trtc.on(TRTC.EVENT.REMOTE_AUDIO_UNAVAILABLE, handleRemoteAudioUnavailable);
        trtc.on(TRTC.EVENT.REMOTE_AUDIO_AVAILABLE, handleRemoteAudioAvailable);
        trtc.on(TRTC.EVENT.SCREEN_SHARE_STOPPED, handleScreenShareStopped);
    }

    function uninstallEventHandlers() {
        trtc.off(TRTC.EVENT.ERROR, handleError);
        trtc.off(TRTC.EVENT.KICKED_OUT, handleKickedOut);
        trtc.off(TRTC.EVENT.REMOTE_USER_ENTER, handleRemoteUserEnter);
        trtc.off(TRTC.EVENT.REMOTE_USER_EXIT, handleRemoteUserExit);
        trtc.off(TRTC.EVENT.REMOTE_VIDEO_AVAILABLE, handleRemoteVideoAvailable);
        trtc.off(TRTC.EVENT.REMOTE_VIDEO_UNAVAILABLE, handleRemoteVideoUnavailable);
        trtc.off(TRTC.EVENT.REMOTE_AUDIO_UNAVAILABLE, handleRemoteAudioUnavailable);
        trtc.off(TRTC.EVENT.REMOTE_AUDIO_AVAILABLE, handleRemoteAudioAvailable);
        trtc.off(TRTC.EVENT.SCREEN_SHARE_STOPPED, handleScreenShareStopped);
    }

    function handleError(error) {
        console.error(`Local error: ${error.message}`);
    }

    function handleKickedOut(event) {
        console.warn(`User has been kicked out for ${event.reason}`);
    }

    function handleRemoteUserEnter(event) {
        const { userId } = event;
        console.warn(`Remote User [${userId}] entered`);
    }

    function handleRemoteUserExit(event) {
        console.warn(`Remote User [${event.userId}] exit`);
    }

    async function handleRemoteVideoAvailable(event) {
        const { userId, streamType } = event;
        try {
            console.warn(`[${userId}] [${streamType}] video available`);
            if (streamType === TRTC.TYPE.STREAM_TYPE_MAIN) {
                remoteStreams.value.push(`${userId}_main`);
                await nextTick();
                await trtc.startRemoteVideo({ userId, streamType, view: `${userId}_main` });
            } else {
                remoteStreams.value.push(`${userId}_screen`);
                await nextTick();
                trtc.startRemoteVideo({ userId, streamType, view: `${userId}_screen` });
            }
            console.warn(`Play remote video success: [${userId}]`);
        } catch (error) {
            console.error(`Play remote video failed: [${userId}], error: ${error.message}`);
        }
    }

    function handleRemoteVideoUnavailable(event) {
        console.warn(`[${event.userId}] [${event.streamType}] video unavailable`);
        const { streamType } = event;
        trtc.stopRemoteVideo({ userId: event.userId, streamType });
        if (streamType === TRTC.TYPE.STREAM_TYPE_MAIN) {
            remoteStreams.value = remoteStreams.value.filter((userId) => userId !== `${event.userId}_main`);
        } else {
            remoteStreams.value = remoteStreams.value.filter((userId) => userId !== `${event.userId}_screen`);
        }
    }

    function handleRemoteAudioUnavailable(event) {
        console.warn(`[${event.userId}] audio unavailable`);
    }

    function handleRemoteAudioAvailable(event) {
        console.warn(`[${event.userId}] audio available`);
    }

    function handleScreenShareStopped() {
        console.warn('Stop share screen success');
    }


    onMounted(() => {
        handleEnter()
    })
    onUnmounted(() => {
        handleExit()
    })

    return {
        SmallRef,
        LargeRef,
        isSilence,
        isStop,
        remoteStreams,
        setOrRelieveSilence,
        stopOrOpenVideo
    }
};
export default useTrtcComm;
