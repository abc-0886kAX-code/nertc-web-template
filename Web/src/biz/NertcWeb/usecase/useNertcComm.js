/*
 * @FilePath: \前端\src\biz\NertcWeb\usecase\useNertcComm.js
 * @Author: zhangxin
 * @Date: 2024-03-18 11:19:39
 * @LastEditors: zhangxin
 * @LastEditTime: 2024-03-26 17:49:13
 * @Description:
 */
import NERTC from 'nertc-web-sdk';
import { createEventHook } from "@vueuse/core";
import { getCommonToken } from "../server/token";

const appkey = '9d808098269de56dccb0b3b70cb6a5d3';
const appSecret = '40812ba7a0a1';
const max = 20;

export function useNertcComm(config) {
    const { userId: localUid, username, roomId: channelName } = config;

    const isSilence = ref(false);
    const isStop = ref(false);
    const client = ref(null);
    const localStream = ref(null);

    const remoteStreams = ref([]);

    const SmallRef = ref(null);
    const LargeRef = ref(null);

    function createClientEntity() {
        client.value = NERTC.createClient({
            appkey: appkey,
            debug: true,
        })
    }

    function bindWatchEvent() {
        //监听事件
        unref(client).on('peer-online', (evt) => {
            joinRoom(evt);
            console.warn(`${evt.uid} 加入房间`);
        });

        unref(client).on('peer-leave', (evt) => {
            console.warn(`${evt.uid} 离开房间`);
            remoteStreams.value = unref(remoteStreams).filter(
                (item) => !!item.getId() && item.getId() !== evt.uid
            );
            leaveRoom(evt);
        });

        unref(client).on('stream-added', async (evt) => {
            //收到房间中其他成员发布自己的媒体的通知，对端同一个人同时开启了麦克风、摄像头、屏幕贡献，这里会通知多次
            const stream = evt.stream;
            const userId = stream.getId();
            console.warn(`收到 ${userId} 的发布 ${evt.mediaType} 的通知`) // mediaType为：'audio' | 'video' | 'screen'
            if (unref(remoteStreams).some((item) => item.getId() === userId)) {
                console.warn('收到已订阅的远端发布，需要更新', stream);
                remoteStreams.value = unref(remoteStreams).map((item) =>
                    item.getId() === userId ? stream : item
                );
                //订阅其发布的媒体，可以渲染播放
                await subscribe(stream);
            } else if (unref(remoteStreams).length < max - 1) {
                console.warn('收到新的远端发布消息', stream);
                remoteStreams.value = unref(remoteStreams).concat(stream);
                //订阅其发布的媒体，可以渲染播放
                await subscribe(stream);
            } else {
                console.warn('房间人数已满');
            }
        });

        unref(client).on('stream-removed', (evt) => {
            const stream = evt.stream;
            const userId = stream.getId();
            console.warn(`收到 ${userId} 的停止发布 ${evt.mediaType} 的通知`) // mediaType为：'audio' | 'video' | 'screen'
            stream.stop(evt.mediaType);
            remoteStreams.value = unref(remoteStreams).map((item) =>
                item.getId() === userId ? stream : item
            );
            console.warn('远端流停止订阅，需要更新', userId, stream);
        });

        unref(client).on('stream-subscribed', (evt) => {
            const userId = evt.stream.getId();
            console.warn(`收到订阅 ${userId} 的 ${evt.mediaType} 成功的通知`) // mediaType为：'audio' | 'video' | 'screen'
            //这里可以根据mediaType的类型决定播放策略
            const remoteStream = evt.stream;
            //用于播放对方视频画面的div节点
            const div = [...unref(SmallRef)].find((item) => {
                return Number(item.dataset.uid) === Number(remoteStream.getId());
            });
            //这里可以控制是否播放某一类媒体，这里设置的是用户主观意愿
            //比如这里都是设置为true，本次通知的mediaType为audio，则本次调用的play会播放音频，如果video、screen内部已经订阅成功，则也会同时播放video、screen，反之不播放
            const playOptions = {
                audio: true,
                video: true,
                screen: true
            }
            remoteStream.play(div, playOptions).then(() => {
                console.log('播放对端的流成功: ', playOptions);
                remoteStream.setRemoteRenderMode({
                    // 这一块是被叫方的视频宽高，目前将多人通话功能隐藏掉了，所以要只考虑一个即可，后续这块要更改 20240326
                    // 设置视频窗口大小
                    width: div.clientWidth,
                    height: div.clientHeight,
                    cut: false, // 是否裁剪
                });
            }).catch((err) => {
                console.warn('播放对方视频失败了: ', err);
            });
            //这里监听一下音频自动播放会被浏览器限制的问题（https://doc.yunxin.163.com/nertc/docs/jM3NDE0NTI?platform=web）
            remoteStream.on('notAllowedError', (err) => {
                const errorCode = err.getCode();
                const id = remoteStream.getId();
                console.log('remoteStream notAllowedError: ', id);
                if (errorCode === 41030) {
                    //页面弹筐加一个按钮，通过交互完成浏览器自动播放策略限制的接触
                    const userGestureUI = document.createElement('div')
                    if (userGestureUI && userGestureUI.style) {
                        userGestureUI.style.fontSize = '20px';
                        userGestureUI.style.position = 'fixed';
                        userGestureUI.style.background = 'yellow';
                        userGestureUI.style.margin = 'auto';
                        userGestureUI.style.width = '100%';
                        userGestureUI.style.zIndex = '9999';
                        userGestureUI.style.top = '0';
                        userGestureUI.onclick = () => {
                            if (userGestureUI && userGestureUI.parentNode) {
                                userGestureUI.parentNode.removeChild(userGestureUI);
                            }
                            remoteStream.resume();
                        }
                        userGestureUI.style.display = 'block';
                        userGestureUI.innerHTML = '自动播放受到浏览器限制，需手势触发。<br/>点击此处手动播放'
                        document.body.appendChild(userGestureUI)
                    }
                }
            });
        });

        unref(client).on('uid-duplicate', () => {
            console.warn('==== uid重复，你被踢出');
            uidDuplicate();
        });

        unref(client).on('error', (type) => {
            console.error('===== 发生错误事件：', type);
            handleError(type);
            if (type === 'SOCKET_ERROR') {
                console.warn('==== 网络异常，已经退出房间');
            }
        });

        unref(client).on('accessDenied', (type) => {
            console.warn(`==== ${type}设备开启的权限被禁止`);
        });

        unref(client).on('connection-state-change', (evt) => {
            console.warn(
                `网络状态变更: ${evt.prevState} => ${evt.curState}, 当前是否在重连：${evt.reconnect}`
            );
        });
    }

    async function getToken() {
        const token = await getCommonToken({
            uid: localUid,
            appkey: appkey,
            appSecret: appSecret,
            // 通道名称
            channelName: channelName
        })
        return token;
    }

    function enterRoom(token) {
        if (!unref(client)) {
            ElMessage.error('内部错误，请重新加入房间');
            return;
        }
        unref(client)
            .join({
                channelName: channelName,
                uid: localUid,
                token,
            })
            .then((data) => {
                console.info('加入房间成功，开始初始化本地音视频流');
                initLocalStream();
            })
            .catch((error) => {
                console.error('加入房间失败：', error);
                ElMessage.error(`${error}: 请检查appkey或者token是否正确`);
            });
    }

    function initLocalStream() {
        //初始化本地的Stream实例，用于管理本端的音视频流
        localStream.value = NERTC.createStream({
            uid: localUid,
            audio: true, //是否启动mic
            video: true, //是否启动camera
            screen: false, //是否启动屏幕共享
        });

        //设置本地视频质量
        unref(localStream).setVideoProfile({
            resolution: NERTC.VIDEO_QUALITY_720p, //设置视频分辨率
            frameRate: NERTC.CHAT_VIDEO_FRAME_RATE_15, //设置视频帧率
        });
        //设置本地音频质量
        unref(localStream).setAudioProfile('speech_low_quality');
        //启动媒体，打开实例对象中设置的媒体设备
        unref(localStream)
            .init()
            .then(() => {
                console.warn('音视频开启完成，可以播放了');
                const div = unref(LargeRef);
                unref(localStream).play(div);
                unref(localStream).setLocalRenderMode({
                    // 设置视频窗口大小
                    width: div.clientWidth,
                    height: div.clientHeight,
                    cut: false, // 是否裁剪
                });
                // 发布
                publish();
            })
            .catch((err) => {
                console.warn('音视频初始化失败: ', err);
                ElMessage.error('音视频初始化失败');
                localStream.value = null;
            });
    }

    function publish() {
        console.warn('开始发布视频流');
        //发布本地媒体给房间对端
        unref(client)
            .publish(unref(localStream))
            .then(() => {
                console.warn('本地 publish 成功');
            })
            .catch((err) => {
                console.error('本地 publish 失败: ', err);
                ElMessage.error('本地 publish 失败');
            });
    }


    function subscribe(remoteStream) {
        //这里可以控制是否订阅某一类媒体，这里设置的是用户主观意愿
        //比如这里都是设置为true，如果stream-added事件中通知了是audio发布了，则本次调用会订阅音频，如果video、screen之前已经有stream-added通知，则也会同时订阅video、screen，反之会忽略
        remoteStream.setSubscribeConfig({
            audio: true,
            video: true,
            screen: true
        });
        unref(client)
            .subscribe(remoteStream)
            .then(() => {
                console.warn('本地 subscribe 成功');
            })
            .catch((err) => {
                console.warn('本地 subscribe 失败: ', err);
                ElMessage.error('订阅对方的流失败');
            });
    }

    // 进入房间
    function joinRoom(params) {

    }

    // 离开房间
    function leaveRoom() {

    }

    // id重复
    function uidDuplicate(params) {

    }

    // error
    function handleError(params) {

    }


    function setOrRelieveSilence() {
        isSilence.value = !unref(isSilence);
        if (unref(isSilence)) {
            unref(localStream)
                .close({
                    type: 'audio',
                })
                .then(() => {
                    console.warn('关闭 mic sucess');
                })
                .catch((err) => {
                    console.warn('关闭 mic 失败: ', err);
                    ElMessage.error('关闭 mic 失败');
                });
        } else {
            console.warn('打开mic');
            if (!unref(localStream)) {
                ElMessage.error('当前不能打开mic');
                return;
            }
            unref(localStream)
                .open({
                    type: 'audio',
                })
                .then(() => {
                    console.warn('打开mic sucess');
                })
                .catch((err) => {
                    console.warn('打开mic失败: ', err);
                    ElMessage.error('打开mic失败');
                });
        }
    }

    function stopOrOpenVideo() {
        isStop.value = !unref(isStop);
        if (unref(isStop)) {
            console.warn('关闭摄像头');
            unref(localStream)
                .close({
                    type: 'video',
                })
                .then(() => {
                    console.warn('关闭摄像头 sucess');
                })
                .catch((err) => {
                    console.warn('关闭摄像头失败: ', err);
                    ElMessage.error('关闭摄像头失败');
                });
        } else {
            console.warn('打开摄像头');
            if (!unref(localStream)) {
                ElMessage.error('当前不能打开camera');
                return;
            }
            unref(localStream)
                .open({
                    type: 'video',
                })
                .then(() => {
                    console.warn('打开摄像头 sucess');
                    const div = unref(LargeRef);
                    unref(localStream).play(div);
                    unref(localStream).setLocalRenderMode({
                        // 设置视频窗口大小
                        width: div.clientWidth,
                        height: div.clientHeight,
                        cut: false, // 是否裁剪
                    });
                })
                .catch((err) => {
                    console.warn('打开摄像头失败: ', err);
                    ElMessage.error('打开摄像头失败');
                });
        }
    }

    onMounted(async () => {
        createClientEntity();
        await bindWatchEvent()
        const token = await getToken()
        enterRoom(token)
    })

    onUnmounted(async () => {
        try {
            await unref(client).leave()
            unref(localStream).destroy()
            unref(client).destroy()
        } catch (e) {
            // 为了兼容低版本，用try catch包裹一下
        }
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
export default useNertcComm;
