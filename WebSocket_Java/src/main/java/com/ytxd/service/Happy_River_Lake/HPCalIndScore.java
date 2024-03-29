package com.ytxd.service.Happy_River_Lake;

import com.ytxd.common.util.SpringContextUtil;
import com.ytxd.dao.Happy_River_Lake.HPCalIndScore_Mapper;
import com.ytxd.dao.Subhall.Aquatic_Ecology.CalIndScore_Mapper;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

/**
 * 计算方法
 */
public class HPCalIndScore implements Serializable {
    private static final long serialVersionUID = -2862585049955236662L;

    private HPCalIndScore_Mapper mapper;
    public HPCalIndScore(){
        this.mapper =  SpringContextUtil.getBean(HPCalIndScore_Mapper.class);
    }

    // 计算区间
    public double lookup(Object value,Object str){
        if(value != null && StringUtils.isNotBlank(value.toString())){
            double score = Double.parseDouble(value.toString());
            if(str != null){
                String param = str.toString();
                return mapper.Select_HP_Score_Contrast(param,score);
            }else {
                return score;
            }
        }
        return 0.0;
    }
}
