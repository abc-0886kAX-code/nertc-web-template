package com.ytxd.config.Netty;

import com.ytxd.util.StringUtil;

import java.util.Objects;

public enum SocketEnum {
    OK("01", true), DISABLE("00", false);

    private final Object code;
    private final Boolean info;

    SocketEnum(Object code, Boolean info)
    {
        this.code = code;
        this.info = info;
    }
    public Object getCode()
    {
        return code;
    }

    public Boolean getInfo()
    {
        return info;
    }

    public static Boolean getInfo(Object code){
        if(code != null && Objects.equals(code,"01")){
            return true;
        }else {
            return false;
        }
    }
}

