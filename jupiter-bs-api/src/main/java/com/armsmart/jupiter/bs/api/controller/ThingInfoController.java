package com.armsmart.jupiter.bs.api.controller;

import com.armsmart.jupiter.bs.api.dao.ThingInfoMapper;
import com.armsmart.jupiter.bs.api.dto.request.*;
import com.armsmart.jupiter.bs.api.dto.response.*;
import com.armsmart.jupiter.bs.api.entity.ThingInfo;
import com.armsmart.jupiter.bs.api.service.ThingInfoService;
import com.armsmart.common.api.CommonPage;
import com.armsmart.common.api.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.security.Principal;
import java.util.List;

/**
 * ThingInfo 接口
 *
 * @author 苏礼刚
 **/
@RestController
@Api(tags = "物品详情接口(储栋)")
@RequestMapping("/thingInfo/")
public class ThingInfoController extends BaseController {

    @Autowired
    private ThingInfoService thingInfoService;

    @Autowired(required = false)
    private ThingInfoMapper thingInfoMapper;

    @PostMapping("add")
    @ApiOperation("物品上链")
    public DeferredResult<CommonResult> add(@Validated @RequestBody ThingInfoAddParam thingInfoAddParam) {
        DeferredResult<CommonResult> deferredResult = thingInfoService.insert(thingInfoAddParam);
        return deferredResult;
    }

    @PostMapping("update")
    @ApiOperation("物品信息修改")
    public CommonResult update(@Validated @RequestBody ThingInfoUpdateParam thingInfoUpdateParam) {
        return thingInfoService.update(thingInfoUpdateParam);
    }

    @PostMapping("del/{id}")
    @ApiOperation("物品删除")
    public CommonResult delete(@ApiParam(name = "id", value = "主键ID", required = true) @PathVariable(value = "id") Long id) {
        return thingInfoService.deleteById(id);
    }

    @PostMapping("batchDel")
    @ApiOperation("物品批量删除")
    public CommonResult batchDel(@ApiParam(name = "ids", value = "主键ID集合", required = true) @RequestParam(value = "ids") List<Long> ids) {
        return thingInfoService.batchDel(ids);
    }

    @GetMapping("selectById/{id}")
    @ApiOperation("查询物品详情")
    public CommonResult<ThingIdInfoDetail> selectById(@ApiParam(name = "id", value = "主键ID", required = true) @PathVariable(value = "id") Long id, @RequestParam(value = "userId", required = false) Integer userId) {
        return thingInfoService.selectById(id, userId);
    }


    @GetMapping("selectByContractAddr/{contractAddr}")
    @ApiOperation("根据NFC合约读取物品信息")
    public CommonResult<NfcReadDetail> selectByContractAddr(@ApiParam(name = "contractAddr", value = "合约地址", required = true) @PathVariable(value = "contractAddr") String contractAddr) {
        return thingInfoService.selectByContractAddr(contractAddr);
    }


    @PostMapping("thingGiveAway")
    @ApiOperation("权证管理--赠送好友")
    public DeferredResult<CommonResult> thingGiveAway(@Validated @RequestBody ModifyOwnerAddInfoParam param) {
        return thingInfoService.thingGiveAway(param);
    }

    @PostMapping("getGiveAway")
    @ApiOperation("赠送签收")
    public DeferredResult<CommonResult> getGiveAway(@Validated @RequestBody ModifyOwnerAddInfoParam param) {
        return thingInfoService.getGiveAway(param);
    }

    @PostMapping("shareNoSell/{id}")
    @ApiOperation("权证管理--非卖展示")
    public CommonResult shareNoSell(@ApiParam(name = "id", value = "主键ID", required = true) @PathVariable(value = "id") Long id) {
        return thingInfoService.shareByThing(id, -1);
    }

    @PostMapping("shareByMyself/{id}")
    @ApiOperation("权证管理--秘密展示")
    public CommonResult shareByMyself(@ApiParam(name = "id", value = "主键ID", required = true) @PathVariable(value = "id") Long id) {
        return thingInfoService.shareByThing(id, 99);
    }

    @GetMapping("selectPage")
    @ApiOperation("分页查询")
    public CommonResult<CommonPage<ThingInfoDetail>> selectPage(ThingInfoQueryParam query) {
        query.setIsOfficial(false);
        return CommonResult.success(thingInfoService.selectPage(query));
    }

    /**
     * 合约地址返回id
     */
    @GetMapping("contractGetId")
    @ApiOperation("上链完成获取当前合约对应id")
    public CommonResult contractGetId(String contractAddr) {
        return CommonResult.success(thingInfoMapper.selectByContractAddr(contractAddr).getId());
    }

    /**
     * 为您推荐
     */
    @GetMapping("recommend/{id}")
    @ApiOperation("为您推荐")
    public CommonResult<ThingSelectListResult> recommend(@ApiParam(name = "id", value = "主键ID", required = true) @PathVariable(value = "id") Long id) {
        return thingInfoService.recommend(id);
    }


    @PostMapping("nfcInfoLoad")
    @ApiOperation("WEB--读卡器和NFC信息录入")
    public CommonResult nfcInfoLoad(@Validated @RequestBody NfcInfoLoadParam param) {
        return thingInfoService.nfcInfoLoad(param);
    }


    @PostMapping("confirmOwner")
    @ApiOperation("华夏卡确权")
    public DeferredResult<CommonResult> confirmOwner(@Validated @RequestBody ModifyOwnerAddInfoParam param) {
        return thingInfoService.confirmOwner(param);
    }

}

