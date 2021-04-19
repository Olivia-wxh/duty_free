package com.shangchao.controller;

import com.alibaba.fastjson.JSONObject;
import com.shangchao.entity.History;
import com.shangchao.entity.ScAndroid;
import com.shangchao.service.SystemService;
import com.shangchao.utils.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/system")
@Api(tags = "系统基础接口", description = "如有疑问请联系王晓辉")
public class SystemController {

    @Resource private SystemService systemService;

    @GetMapping("/updateInfo")
    @ApiOperation("修改安卓版本信息")
    public boolean updateInfo(@RequestParam("downloadUrl") String downloadUrl,
                              @RequestParam("versionCode") String versionCode,
                              @RequestParam("updateInfo") String updateInfo) {
        Boolean b = systemService.updateInfo(downloadUrl, versionCode, updateInfo);
        return b;
    }

    @GetMapping("/getInfo")
    @ApiOperation("查询安卓版本信息")
    public ScAndroid getInfo() {
        ScAndroid sc = systemService.getInfo();
        return sc;
    }

    @GetMapping("/search_history")
    @ApiOperation("查询搜索记录")
    @ApiImplicitParam(name = "userId", value = "用户id", required = true)
    public List<History> getSearchInfo(String userId) {
        List<History> history = systemService.getSearchInfo(Integer.parseInt(userId));
        return history;
    }

    @GetMapping("/search_del")
    @ApiOperation("清空搜索历史")
    @ApiImplicitParam(name = "userId", value = "用户id", required = true)
    public JSONObject deleteSearchHistory(String userId) {
        Boolean history = systemService.removeSearchByUserId(Integer.parseInt(userId));
        return ResponseUtil.status(history);
    }
}
