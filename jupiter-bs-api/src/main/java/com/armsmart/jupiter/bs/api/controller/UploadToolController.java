package com.armsmart.jupiter.bs.api.controller;

import com.armsmart.common.api.CommonPage;
import com.armsmart.common.api.CommonResult;
import com.armsmart.jupiter.bs.api.dto.request.ThingInfoQueryParam;
import com.armsmart.jupiter.bs.api.dto.request.ThingInfoUpdateParam;
import com.armsmart.jupiter.bs.api.dto.request.ThingNfcAddParam;
import com.armsmart.jupiter.bs.api.dto.request.ThingNfcUpdateParam;
import com.armsmart.jupiter.bs.api.dto.response.ThingIdInfoDetail;
import com.armsmart.jupiter.bs.api.dto.response.ThingInfoDetail;
import com.armsmart.jupiter.bs.api.service.ThingInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ThingInfo 接口
 *
 * @author 苏礼刚
 **/
@RestController
@Api(tags = "上链工具管理(林伟)")
@RequestMapping("/uploadTool/")
public class UploadToolController extends BaseController {

    @Autowired
    private ThingInfoService thingInfoService;


    @PostMapping("add")
    @ApiOperation("WEB-新增")
    public CommonResult add(@Validated @RequestBody ThingNfcAddParam addParam) {
        return thingInfoService.insert(addParam);
    }

    @PostMapping("update")
    @ApiOperation("WEB-修改")
    public CommonResult update(@Validated @RequestBody ThingNfcUpdateParam thingInfoUpdateParam) {
        return thingInfoService.update(thingInfoUpdateParam);
    }

    @PostMapping("del/{id}")
    @ApiOperation("WEB-删除")
    public CommonResult delete(@ApiParam(name = "id", value = "主键ID", required = true) @PathVariable(value = "id") Long id) {
        return thingInfoService.deleteById(id);
    }

    @PostMapping("batchDel")
    @ApiOperation("WEB-批量删除")
    public CommonResult batchDel(@ApiParam(name = "ids", value = "主键ID集合", required = true) @RequestParam(value = "ids") List<Long> ids) {
        return thingInfoService.batchDel(ids);
    }


    @GetMapping("selectPage")
    @ApiOperation("WEB-分页查询")
    public CommonResult<CommonPage<ThingInfoDetail>> selectPage(ThingInfoQueryParam query) {
        query.setIsOfficial(true);
        return CommonResult.success(thingInfoService.selectPage(query));
    }


    @PostMapping("setPutOn/{id}")
    @ApiOperation("WEB-上架/下架（林伟）")
    public CommonResult setEnable(@ApiParam(name = "id", value = "物品ID", required = true) @PathVariable(value = "id") Long id, @ApiParam(name = "isPutOn", value = "是否启用", required = true) @RequestParam("isPutOn") Boolean isPutOn) {
        return thingInfoService.setPutOn(id, isPutOn);
    }


}

