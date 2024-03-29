package com.ytxd.service.Statistics;

import afu.org.checkerframework.checker.oigj.qual.O;
import com.ytxd.common.util.R;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public interface StbprpService {


    LinkedList<Map<String, Object>> GetData(HttpServletRequest request);


     List<HashMap<String,Object>> GetData4();


    List<HashMap<String,Object>> GetTopData();
}
