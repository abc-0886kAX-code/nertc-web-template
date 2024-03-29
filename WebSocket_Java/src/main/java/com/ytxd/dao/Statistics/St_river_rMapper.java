package com.ytxd.dao.Statistics;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.shiro.crypto.hash.Hash;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface St_river_rMapper extends BaseMapper {
    List<HashMap<String,Object>> GetData(Map<String,Object> obj);

    List<HashMap<String,Object>> GetCount();

    HashMap<String,Object> GetTotal();
}
