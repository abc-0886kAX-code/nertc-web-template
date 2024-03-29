package com.ytxd.config.Netty;

public class SocketConstant {
    // 合并了被动接收房间和忙碌中房间的用户列表
    final public static String EMIT_UPDATE_USER_LIST = "update_user_list";
    // 通话邀请
    final public static String EMIT_INVITE = "invite";

    // 通话邀请同意
    final public static String EMIT_INVITE_ACCEPT = "invite_accept";

    // 通话邀请拒绝
    final public static String EMIT_INVITE_REJECT = "invite_reject";

    // 通信建立
    final public static String EMIT_SIGNAL_START = "signal_start";

    // 通信结束
    final public static String EMIT_SIGNAL_END = "signal_end";
}
