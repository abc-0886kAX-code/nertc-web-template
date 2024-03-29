package com.ytxd.service.common;

import com.ytxd.service.impl.BaseServiceImpl;
import com.ytxd.model.SysUser;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("defaultValue")
public class DefaultValue extends BaseServiceImpl {
	
	public String DefaultValue(String DefaultValue,SysUser user)
    {
		if(DefaultValue.indexOf("+")==-1){
        	return DefaultValue;
        }
        if(DefaultValue.indexOf("+DateTime+")>-1){
        	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        	DefaultValue = DefaultValue.replace("+DateTime+", formatter.format(new Date()));
        }
        else if(DefaultValue.indexOf("+DateTime6+")>-1){
        	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
        	DefaultValue = DefaultValue.replace("+DateTime6+", formatter.format(new Date()));
        	if(DefaultValue.indexOf(formatter.format(new Date())+"-31")>-1){
        		Calendar calendar = Calendar.getInstance();
        		calendar.set(Calendar.DATE, calendar.getMaximum(Calendar.DATE));
        		SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd");
        		DefaultValue = DefaultValue.replace(formatter.format(new Date())+"-31", DateFormat.format(calendar.getTime()));
        	}
        }
        else if(DefaultValue.indexOf("+DateAndTime+")>-1){
        	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        	DefaultValue = DefaultValue.replace("+DateAndTime+", formatter.format(new Date()));
        }
        else if(DefaultValue.indexOf("+Year+")>-1){
        	SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        	DefaultValue = DefaultValue.replace("+Year+", formatter.format(new Date()));
        }
        else if(DefaultValue.indexOf("+DepartmentId+")>-1){
        	DefaultValue = DefaultValue.replace("+DepartmentId+", user.getDeptId());
        }
        else if(DefaultValue.indexOf("+UserId+")>-1){
        	DefaultValue = DefaultValue.replace("+UserId+", user.getUserId().toString());
        }
        else if(DefaultValue.indexOf("+TrueName+")>-1){
        	DefaultValue = DefaultValue.replace("+TrueName+", user.getTrueName());
        }
        if(DefaultValue.indexOf("+JS+")>-1){
        	DefaultValue = finalResult(DefaultValue.replace("+JS+", ""));
        	if(DefaultValue.substring(DefaultValue.length()-2).equals(".0")){
        		DefaultValue=DefaultValue.substring(0,DefaultValue.length()-2);
        	}
        }
        return DefaultValue;
    }

	public String finalResult(String original) {
		try {  
			ScriptEngine jse = new ScriptEngineManager().getEngineByName("JavaScript");
			Object temp = jse.eval(original);
            String result =temp.toString();
            return result;
		} catch (Exception t) {  
	        return null;
		}
	}
}