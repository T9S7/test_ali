package com.armsmart.jupiter.bs.api.controller;


import com.armsmart.jupiter.bs.api.entity.AppConfigureInfo;
import com.armsmart.jupiter.bs.api.service.AppConfigureInfoService;
import com.armsmart.jupiter.bs.api.dto.request.AppConfigureInfoAddParam;
import com.armsmart.jupiter.bs.api.dto.request.AppConfigureInfoUpdateParam;
import com.armsmart.jupiter.bs.api.dto.request.AppConfigureInfoQueryParam;
import com.armsmart.jupiter.bs.api.dto.response.AppConfigureInfoDetail;
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
 *  AppConfigureInfo 接口
 * @author 苏礼刚
 **/
@RestController
@Api(tags = "APP--配置信息接口")
@RequestMapping("/appConfigureInfo/")
public class AppConfigureInfoController {

	@Autowired(required = false)
	private AppConfigureInfoService appConfigureInfoService;

	@PostMapping("add")
	@ApiOperation("添加")
	public CommonResult add(@Validated @RequestBody AppConfigureInfoAddParam appConfigureInfoAddParam) {
		return appConfigureInfoService.insert(appConfigureInfoAddParam);
	}

	@PostMapping("update")
	@ApiOperation("修改")
	public CommonResult update(@Validated @RequestBody AppConfigureInfoUpdateParam appConfigureInfoUpdateParam) {
		return appConfigureInfoService.update(appConfigureInfoUpdateParam);
	}

	@PostMapping("del/{id}")
	@ApiOperation("删除")
	public  CommonResult delete(@ApiParam(name = "id",value = "主键ID",required = true) @PathVariable(value = "id") Integer id) {
		return appConfigureInfoService.deleteById(id);
	}

//	@PostMapping("batchDel")
//	@ApiOperation("批量删除")
//	public  CommonResult batchDel(@ApiParam(name = "ids",value = "主键ID集合",required = true) @RequestParam(value = "ids") List<Integer> ids) {
//		return appConfigureInfoService.batchDel(ids);
//	}
//
//	@GetMapping("selectPage")
//	@ApiOperation("分页查询")
//	public  CommonResult<CommonPage<AppConfigureInfoDetail>> selectPage(AppConfigureInfoQueryParam query) {
//		return CommonResult.success(appConfigureInfoService.selectPage(query));
//	}

	@GetMapping("selectById/{id}")
	@ApiOperation("查询详情")
	public CommonResult<AppConfigureInfoDetail> selectById(@ApiParam(name = "id",value = "主键ID",required = true) @PathVariable(value = "id") Integer id) {
		return appConfigureInfoService.selectById(id);
	}
}

