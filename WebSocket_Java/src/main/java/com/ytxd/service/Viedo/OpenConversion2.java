package com.ytxd.service.Viedo;

//import org.bytedeco.javacpp.Loader;
import org.springframework.scheduling.annotation.Async;

import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.Map;

/**
 * 视频转码
 */
public class OpenConversion2 {
    /**
     * 使用单例模式
     */
    private OpenConversion2() {
    }

    private static volatile OpenConversion2 openConversion;
    /**
     * 源地址
     */
    private static String sourceUrl;
    /**
     * 目标地址
     */
    private static String targetUrl;
    /**
     * ffmpeg
     */
//    private static String ffmpeg = Loader.load(org.bytedeco.ffmpeg.ffmpeg.class);
    /**
     * 执行体
     */
    private static Map<String,Process> processMap = new HashMap<>();
    /**
     * 目标地址集合
     */
    private static Map<String,String> targetUrlMap = new HashMap<>();

    /**
     * 实例化单例 有参
     * @param sourceUrl
     * @param targetUrl
     * @return
     */
    public static OpenConversion2 getInstance(String sourceUrl, String targetUrl) {
        if (openConversion == null) {
            synchronized (OpenConversion2.class) {
                openConversion = new OpenConversion2();
            }
        }
        openConversion.sourceUrl = sourceUrl;
        openConversion.targetUrl = targetUrl;
        return openConversion;
    }
    /**
     * 开启转化
     *
     * @throws Exception
     */
    public void OpenConversionTOFlv(@NotBlank String processId) throws Exception {
        /**
         * 如果存在，先停止后在执行
         */
        destroyConversionTOFlv(processId);
        String url = targetUrl + "/" + processId;
//        ProcessBuilder processBuilder = new ProcessBuilder(ffmpeg,
//                "-rtsp_transport",
//                "tcp",
//                "-i",
//                sourceUrl,
//                "-vcodec",
//                "copy",
//                "-acodec",
//                "copy",
//                "-f",
//                "flv",
//                url);
//
//        Process process = processBuilder.inheritIO().start();
//        processMap.put(processId,process);
        targetUrlMap.put(processId,url);
    }

    /**
     * 销毁
     */
   public static void destroyConversionTOFlv(@NotBlank String processId){
       Process process = processMap.get(processId);
       if (process != null) {
           process.destroy();
           processMap.remove(processId);
           targetUrlMap.remove(processId);
       }
    }

    /**
     * 获取目标地址
     * @param processId
     * @return
     */
    public static String getTargetUrl(@NotBlank String processId){
       return targetUrlMap.get(processId);
    }

}
