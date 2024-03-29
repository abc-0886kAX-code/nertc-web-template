import axios from "axios";
import JsSha from 'jssha'


export function getCommonToken(params) {
    const { uid, channelName, appkey, appSecret } = params;

    const Nonce = Math.ceil(Math.random() * 1e9)
    const CurTime = Math.ceil(Date.now() / 1000)
    const aaa = `${appSecret}${Nonce}${CurTime}`
    const sha1 = new JsSha('SHA-1', 'TEXT', { encoding: 'UTF8' })
    sha1.update(aaa)
    const CheckSum = sha1.getHash('HEX')


    return new Promise((resolve, reject) => {
        axios.post('https://api.netease.im/nimserver/user/getToken.action',
            `uid=${uid}&channelName=${channelName}`, {
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8',
                AppKey: appkey,
                Nonce,
                CurTime,
                CheckSum,
            }
        })
            .then(function (data) {
                var d = data.data;
                if (d.code !== 200) {
                    reject(new Error('getChecksum code: ' + d.code));
                    return
                }
                resolve(d.token)
            })
            .catch((error) => {
                reject(new Error('getChecksum error: ', error));
            });
    })
}
