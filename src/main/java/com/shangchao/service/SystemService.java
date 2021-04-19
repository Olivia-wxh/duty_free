package com.shangchao.service;

import com.shangchao.entity.History;
import com.shangchao.entity.ScAndroid;

import java.util.List;

public interface SystemService {
    Boolean updateInfo(String downloadUrl, String versionCode, String updateInfo);

    ScAndroid getInfo();

    List<History> getSearchInfo(Integer userId);

    Boolean removeSearchByUserId(Integer userId);

    Boolean saveHistory(String brandName, String userId);
}
