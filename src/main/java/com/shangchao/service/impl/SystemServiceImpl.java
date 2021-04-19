package com.shangchao.service.impl;

import com.shangchao.dao.IHistoryDAO;
import com.shangchao.dao.IScAndroidDAO;
import com.shangchao.entity.History;
import com.shangchao.entity.ScAndroid;
import com.shangchao.service.SystemService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Service
public class SystemServiceImpl implements SystemService {

    @Resource
    private IScAndroidDAO androidDAO;
    @Resource
    private IHistoryDAO historyDAO;

    @Override
    public Boolean updateInfo(String downloadUrl, String versionCode, String updateInfo) {
        Integer re = androidDAO.update(downloadUrl, versionCode, updateInfo);
        if (re == 1) {
            return true;
        }
        return false;
    }

    @Override
    public ScAndroid getInfo() {
        ScAndroid sc = androidDAO.getAndroidInfo();
        return sc;
    }

    @Override
    public List<History> getSearchInfo(Integer userId) {
        List<History> searchInfo = historyDAO.getSearchInfo(userId);
        return searchInfo;
    }

    @Override
    public Boolean removeSearchByUserId(Integer userId) {
        Integer integer = historyDAO.removeSearchByUserId(userId);
        if (integer > 0) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean saveHistory(String brandName, String userId) {
        List<History> searchInfo = historyDAO.getSearchInfo(Integer.parseInt(userId));
        int order = 0;
        if (!CollectionUtils.isEmpty(searchInfo)) {
            order = searchInfo.size() + 1;
        }
        History history = new History();
        history.setSearch(brandName);
        history.setUserId(Integer.parseInt(userId));
        history.setOrderSearch(order);
        Integer integer = historyDAO.saveHistory(history);
        if (integer > 0) {
            return true;
        }
        return false;
    }
}
