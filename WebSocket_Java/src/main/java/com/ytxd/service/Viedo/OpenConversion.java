package com.ytxd.service.Viedo;

//import org.bytedeco.javacpp.Loader;
import org.springframework.scheduling.annotation.Async;

/**
 * 视频转码
 */
public class OpenConversion  {
    /**
     * 使用单例模式
     */
    private OpenConversion() {
    }

    private static volatile OpenConversion openConversion;

    private String sourceUrl;
    private String targetUrl;
//    private static String ffmpeg = Loader.load(org.bytedeco.ffmpeg.ffmpeg.class);
    private ProcessBuilder pb;
    private  Process process ;

    public static OpenConversion getInstance(String sourceUrl, String targetUrl) {
        if (openConversion == null) {
            synchronized (OpenConversion.class) {
                openConversion = new OpenConversion();
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
    @Async
    void OpenConversionTOFlv() throws Exception {
        if (process != null) {
            process.destroy();
            process = null;
        }
//        pb = new ProcessBuilder(ffmpeg,
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
//                targetUrl);
        process = pb.inheritIO().start();
    }

    /**
     * 销毁
     */
   public void destroyConversionTOFlv(){
        if (process != null) {
            process.destroy();
            process = null;
        }
    }
}
