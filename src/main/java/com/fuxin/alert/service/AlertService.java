package com.fuxin.alert.service;


import com.alibaba.fastjson.JSONObject;
import com.fuxin.alert.domain.PlatformAlert;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author Gao
 * @date 2020/10/29 15:15
 * @description
 */
public interface AlertService {

    Boolean judge(String url, JSONObject param);
    JSONObject readOffset(String path) throws IOException;
    void wirteOffset(String path, String offset);
    String encode(String str) throws UnsupportedEncodingException;
    List getRizhiAlert() throws IOException;
    List<PlatformAlert> getSuanfaAlert() throws IOException;
}
