package com.shangchao.dao;

import com.shangchao.entity.ScAndroid;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
public interface IScAndroidDAO {

    public Integer update(@RequestParam("downloadUrl") String downloadUrl,
                          @RequestParam("versionCode") String versionCode,
                          @RequestParam("updateInfo") String updateInfo);

    ScAndroid getAndroidInfo();
}
