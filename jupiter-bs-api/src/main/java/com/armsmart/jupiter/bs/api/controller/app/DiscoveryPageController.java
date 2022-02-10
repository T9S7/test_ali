package com.armsmart.jupiter.bs.api.controller.app;


import cn.hutool.core.collection.CollectionUtil;
import com.armsmart.common.api.CommonPage;
import com.armsmart.common.api.CommonResult;
import com.armsmart.jupiter.bs.api.constants.SellType;
import com.armsmart.jupiter.bs.api.constants.ThingState;
import com.armsmart.jupiter.bs.api.dto.request.PageQueryParam;
import com.armsmart.jupiter.bs.api.dto.request.ThingInfoQueryParam;
import com.armsmart.jupiter.bs.api.dto.response.*;
import com.armsmart.jupiter.bs.api.service.AppTutorialVideoService;
import com.armsmart.jupiter.bs.api.service.ThingInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author wei.lin
 * @date 2021/12/26
 */
@Api(tags = "APP-发现页接口")
@RestController
@RequestMapping("/discovery/")
public class DiscoveryPageController {

    @Autowired
    private ThingInfoService thingInfoService;
    @Autowired
    private AppTutorialVideoService appTutorialVideoService;

    @GetMapping("thingList")
    @ApiOperation("APP发现页-推荐列表")
    public CommonResult<CommonPage<ThingSelectListResult>> selectList(PageQueryParam param) {
        ThingInfoQueryParam query = new ThingInfoQueryParam();
        query.setCurrentState(ThingState.ON_SALE.getCode());
        query.setIsDel(false);
        query.setUploadStatus(true);
        query.setIsOfficial(false);
        query.setPageNum(param.getPageNum());
        query.setPageSize(param.getPageSize());
        return CommonResult.success(thingInfoService.selectList(query));
    }

    @GetMapping("uploadToolList")
    @ApiOperation("APP发现页-上链工具列表")
    public CommonResult<CommonPage<ThingShortInfo>> selectUploadTools(PageQueryParam param) {
        ThingInfoQueryParam query = new ThingInfoQueryParam();
        query.setIsDel(false);
        query.setIsOfficial(true);
        query.setCurrentState(ThingState.ON_SALE.getCode());
        query.setPageNum(param.getPageNum());
        query.setPageSize(param.getPageSize());
        CommonPage<ThingShortInfo> commonPage = thingInfoService.selectUploadTools(query);
        if (CollectionUtil.isNotEmpty(commonPage.getList())) {
            commonPage.getList().forEach(item -> item.setSellType(SellType.BUYOUT.getCode()));
        }
        return CommonResult.success(commonPage);
    }

    @GetMapping("uploadToolDetail/{id}")
    @ApiOperation("获取芯片读卡器信息")
    public CommonResult<ThingNftInfo> getInfo(@PathVariable("id") Long id, @RequestParam(value = "userId", required = false) Integer userId) {
        return thingInfoService.selectUploadTool(id, userId);
    }

    @GetMapping("uploadToolDetail/ft")
    @ApiOperation("获取孚贴信息")
    public CommonResult<ThingNftInfo> getInfo(@RequestParam(value = "userId", required = false) Integer userId) {
        ThingInfoQueryParam query = new ThingInfoQueryParam();
        query.setIsDel(false);
        query.setIsOfficial(true);
        query.setCurrentState(ThingState.ON_SALE.getCode());
        query.setArtName("孚贴");
        CommonPage<ThingShortInfo> commonPage = thingInfoService.selectUploadTools(query);
        if (commonPage.getTotal() > 0) {
            return thingInfoService.selectUploadTool(commonPage.getList().get(0).getThingId(), userId);
        }
        return CommonResult.success();
    }

    @GetMapping("getTutorialVideo")
    @ApiOperation("获取教程视频地址")
    public CommonResult<AppTutorialVideoDetail> selectOne() {
        return CommonResult.success(appTutorialVideoService.selectOne());
    }
}
