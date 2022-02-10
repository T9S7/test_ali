package com.armsmart.jupiter.bs.api.controller;


import com.armsmart.common.api.CommonPage;
import com.armsmart.common.api.CommonResult;
import com.armsmart.jupiter.bs.api.dto.request.ArtInfoAddParam;
import com.armsmart.jupiter.bs.api.dto.request.ArtInfoQueryParam;
import com.armsmart.jupiter.bs.api.dto.response.ArtInfoDetail;
import com.armsmart.jupiter.bs.api.service.ArtInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * ArtInfo 接口
 *
 * @author wei.lin
 **/
@RestController
@Api(tags = "艺术物品信息接口")
@RequestMapping("/artInfo/")
public class ArtInfoController {

    @Autowired
    private ArtInfoService artInfoService;

    @PostMapping("add")
    @ApiOperation("在线录入艺术品信息")
    public DeferredResult<CommonResult> add(@Validated @RequestBody ArtInfoAddParam artInfoAddParam) {
        return artInfoService.insert(artInfoAddParam);
    }

    @GetMapping("selectPage")
    @ApiOperation("分页查询")
    public CommonResult<CommonPage<ArtInfoDetail>> selectPage(ArtInfoQueryParam query) {
        return CommonResult.success(artInfoService.selectPage(query));
    }

    @GetMapping("selectById/{artId}")
    @ApiOperation("查询详情")
    public CommonResult<ArtInfoDetail> selectById(@ApiParam(name = "artId", value = "主键ID", required = true) @PathVariable(value = "artId") Long artId) {
        return artInfoService.selectById(artId);
    }

    @GetMapping("selectByContractAddr/{contractAddr}")
    @ApiOperation("根据合约地址查询录入信息")
    public CommonResult<ArtInfoDetail> selectByContractAddr(@ApiParam(name = "contractAddr", value = "合约地址", required = true) @PathVariable(value = "contractAddr") String contractAddr) {
        return artInfoService.selectByContractAddr(contractAddr);
    }


}

