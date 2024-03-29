package com.ytxd.common.util;

/**
 * 常量
 * 
 * @author ytxd
 * @email 2625100545@qq.com
 * @date 2019年11月15日 下午1:23:52
 */
public class Constant {

    public static final String NUMBER_CODE_KEY = "x_springboot:number:code:";
    public static final String MOBILE_CODE_KEY = "x_springboot:mobile:code:";

	/** 超级管理员ID */
	public static final int SUPER_ADMIN = 1;
	/** 验证码长度	*/
	public static final int CODE_SIZE = 4;

	/**
	 * 菜单类型
	 * 
	 * @author ytxd
	 * @email 2625100545@qq.com
	 * @date 2019年11月15日 下午1:24:29
	 */
    public enum MenuType {
        /**
         * 目录
         */
    	CATALOG(0),
        /**
         * 菜单
         */
        MENU(1),
        /**
         * 按钮
         */
        BUTTON(2);

        private int value;

        MenuType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
    
    /**
     * 定时任务状态
     * 
     * @author ytxd
     * @email 2625100545@qq.com
     * @date 2019年12月3日 上午12:07:22
     */
    public enum ScheduleStatus {
        /**
         * 正常
         */
    	NORMAL(0),
        /**
         * 暂停
         */
    	PAUSE(1);

        private int value;

        ScheduleStatus(int value) {
            this.value = value;
        }
        
        public int getValue() {
            return value;
        }
    }

    /**
     * 云服务商
     */
    public enum CloudService {
        /**
         * 七牛云
         */
        QINIU(1),
        /**
         * 阿里云
         */
        ALIYUN(2),
        /**
         * 腾讯云
         */
        QCLOUD(3),
        /**
         * minio
         */
        MINIO(4);

        private int value;

        CloudService(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
    /**
     * 编辑状态
     */
    public enum InputState {
    	/**
         * 增加
         */
        ADD(1),
        /**
         * 增加和修改
         */
        EDIT(2),
        /**
         * 查看
         */
        VIEW(3);

        private int value;

    	InputState(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
    /**
     * 登录类型
     */
    public enum LoginType {
    	/**
         * easyui
         */
    	EASYUI(1),
        /**
         * app
         */
        APP(2),
        /**
         * vue
         */
        VUE(3),

        SSO(4);

        private int value;

        LoginType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 登录状态
     */
    public enum LoginState {
        /**
        * 正常状态
        */
        NORMAL(1),
        /**
         * 异常状态
         */
        ABNORMAL(2),
        /**
         * 挤出
         */
        EXTRUSION(3);

        private int value;
        LoginState(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
