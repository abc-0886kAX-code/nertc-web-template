package com.ytxd.service.Subhall.Aquatic_Ecology;

import com.ytxd.common.util.SpringContextUtil;
import com.ytxd.common.util.SpringContextUtils;
import com.ytxd.dao.Subhall.Aquatic_Ecology.CalIndScore_Mapper;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

/**
 * 计算方法
 */
public class CalIndScore implements Serializable {
    private static final long serialVersionUID = -2862585049955236662L;

    private CalIndScore_Mapper mapper;
    public CalIndScore(){
        this.mapper = (CalIndScore_Mapper) SpringContextUtil.getBean("calIndScore_Mapper");
    }

    // 计算区间
    public double lookup(Object value,Object str){
        if(value != null && StringUtils.isNotBlank(value.toString())){
            double score = Double.parseDouble(value.toString());
            if(str != null){
                String param = str.toString();
                return mapper.Select_Eval_Score_Contrast(param,score);
            }else {
                return score;
            }
        }
        return 0.0;
    }
}
