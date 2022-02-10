package com.armsmart.jupiter.bs.api.controller;


import com.armsmart.jupiter.bs.api.dto.request.UserIdQueryParam;
import com.armsmart.jupiter.bs.api.dto.response.CollectSelectListResult;
import com.armsmart.jupiter.bs.api.dto.response.ThingInfoBidDetail;
import com.armsmart.jupiter.bs.api.dto.response.ThingSelectListResult;
import com.armsmart.jupiter.bs.api.entity.UserCollect;
import com.armsmart.jupiter.bs.api.service.UserCollectService;
import com.armsmart.jupiter.bs.api.dto.request.UserCollectAddParam;
import com.armsmart.jupiter.bs.api.dto.request.UserCollectUpdateParam;
import com.armsmart.jupiter.bs.api.dto.request.UserCollectQueryParam;
import com.armsmart.jupiter.bs.api.dto.response.UserCollectDetail;
import com.armsmart.common.api.CommonPage;
import com.armsmart.common.api.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * UserCollect 接口
 *
 * @author 苏礼刚
 **/
@RestController
@Api(tags = "收藏接口（储栋）")
@RequestMapping("/userCollect/")
public class UserCollectController {

    @Autowired
    private UserCollectService userCollectService;

    @PostMapping("add/{thingId}")
    @ApiOperation("APP-添加收藏")
    public CommonResult add(@ApiParam(name = "thingId", value = "物品Id", required = true) @PathVariable(value = "thingId") Long thingId) {
        return userCollectService.insert(thingId);
    }

    @PostMapping("center/{thingId}")
    @ApiOperation("APP-取消收藏")
    public CommonResult center(@ApiParam(name = "thingId", value = "物品Id", required = true) @PathVariable(value = "thingId") Long thingId) {
        return userCollectService.center(thingId);
    }


    @PostMapping("del/{id}")
    @ApiOperation("WEB-删除")
    public CommonResult delete(@ApiParam(name = "id", value = "主键ID", required = true) @PathVariable(value = "id") Long id) {
        return userCollectService.deleteById(id);
    }

    @PostMapping("batchDel")
    @ApiOperation("WEB-批量删除")
    public CommonResult batchDel(@ApiParam(name = "ids", value = "主键ID集合", required = true) @RequestParam(value = "ids") List<Long> ids) {
        return userCollectService.batchDel(ids);
    }

    @GetMapping("selectPage")
    @ApiOperation("WEB-分页查询")
    public CommonResult<CommonPage<UserCollectDetail>> selectPage(UserCollectQueryParam query) {
        return CommonResult.success(userCollectService.selectPage(query));
    }

    @GetMapping("selectById/{id}")
    @ApiOperation("WEB-查询详情")
    public CommonResult<UserCollectDetail> selectById(@ApiParam(name = "id", value = "主键ID", required = true) @PathVariable(value = "id") Long id) {
        return userCollectService.selectById(id);
    }

    @GetMapping("countByUserId/{userId}")
    @ApiOperation("用户收藏数")
    public CommonResult<Integer> countByUserId(@ApiParam(name = "userId", value = "用户id", required = true) @PathVariable(value = "userId") Long userId) {
        return userCollectService.countByUserId(userId);
    }

    @GetMapping("selectCollectList")
    @ApiOperation("收藏列表")
    public CommonResult<CommonPage<CollectSelectListResult>> selectCollectList(UserIdQueryParam param) {
        return CommonResult.success(userCollectService.selectCollectList(param));
    }

}

