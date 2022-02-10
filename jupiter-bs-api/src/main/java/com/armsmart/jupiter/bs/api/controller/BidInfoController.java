package com.armsmart.jupiter.bs.api.controller;


import com.armsmart.common.api.CommonPage;
import com.armsmart.common.api.CommonResult;
import com.armsmart.jupiter.bs.api.assembler.BidInfoAssembler;
import com.armsmart.jupiter.bs.api.dto.request.BidInfoAddParam;
import com.armsmart.jupiter.bs.api.dto.request.BidInfoListQueryParam;
import com.armsmart.jupiter.bs.api.dto.request.BidInfoMyQueryParam;
import com.armsmart.jupiter.bs.api.dto.request.BidInfoQueryParam;
import com.armsmart.jupiter.bs.api.dto.response.BidInfoDetail;
import com.armsmart.jupiter.bs.api.service.BidInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * BidInfo 接口
 *
 * @author wei.lin
 **/
@RestController
@Api(tags = "出价管理接口")
@RequestMapping("/bidInfo/")
public class BidInfoController {

    @Autowired
    private BidInfoService bidInfoService;

    @PostMapping("add")
    @ApiOperation("APP-物品竞拍出价（林伟）")
    public CommonResult add(@Validated @RequestBody BidInfoAddParam bidInfoAddParam) throws Exception {
        return bidInfoService.insert(bidInfoAddParam);
    }

    @GetMapping("selectMyBidInfo")
    @ApiOperation(value = "APP-我的出价（林伟）", hidden = true)
    public CommonResult<CommonPage<BidInfoDetail>> selectMyBidInfo(@Validated BidInfoMyQueryParam query) {
        return CommonResult.success();
    }

    @GetMapping("selectBySellId")
    @ApiOperation("APP-根据拍卖ID查询出价列表（林伟）")
    public CommonResult<CommonPage<BidInfoDetail>> selectBySellId(@Validated BidInfoListQueryParam listQueryParam) {
        BidInfoQueryParam bidInfoQueryParam = BidInfoAssembler.INSTANCE.getBidInfoQueryParam(listQueryParam);
        return CommonResult.success(bidInfoService.selectPage(bidInfoQueryParam));
    }

}

