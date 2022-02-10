package com.armsmart.jupiter.bs.api.controller;


import com.armsmart.jupiter.bs.api.entity.ThingPicInfo;
import com.armsmart.jupiter.bs.api.service.ThingPicInfoService;
import com.armsmart.jupiter.bs.api.dto.request.ThingPicInfoAddParam;
import com.armsmart.jupiter.bs.api.dto.request.ThingPicInfoUpdateParam;
import com.armsmart.jupiter.bs.api.dto.request.ThingPicInfoQueryParam;
import com.armsmart.jupiter.bs.api.dto.response.ThingPicInfoDetail;
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
 *  ThingPicInfo 接口
 * @author 苏礼刚
 **/
@RestController
@Api(tags = "物品图片接口")
@RequestMapping("/thingPicInfo/")
public class ThingPicInfoController {

	@Autowired
	private ThingPicInfoService thingPicInfoService;

	@PostMapping("add")
	@ApiOperation("添加")
	public CommonResult add(@Validated @RequestBody ThingPicInfoAddParam thingPicInfoAddParam) {
		return thingPicInfoService.insert(thingPicInfoAddParam);
	}

	@PostMapping("update")
	@ApiOperation("修改")
	public CommonResult update(@Validated @RequestBody ThingPicInfoUpdateParam thingPicInfoUpdateParam) {
		return thingPicInfoService.update(thingPicInfoUpdateParam);
	}

	@PostMapping("del/{id}")
	@ApiOperation("删除")
	public  CommonResult delete(@ApiParam(name = "id",value = "主键ID",required = true) @PathVariable(value = "id") Long id) {
		return thingPicInfoService.deleteById(id);
	}

	@PostMapping("batchDel")
	@ApiOperation("批量删除")
	public  CommonResult batchDel(@ApiParam(name = "ids",value = "主键ID集合",required = true) @RequestParam(value = "ids") List<Long> ids) {
		return thingPicInfoService.batchDel(ids);
	}

	@GetMapping("selectPage")
	@ApiOperation("分页查询")
	public  CommonResult<CommonPage<ThingPicInfoDetail>> selectPage(ThingPicInfoQueryParam query) {
		return CommonResult.success(thingPicInfoService.selectPage(query));
	}

	@GetMapping("selectById/{id}")
	@ApiOperation("查询详情")
	public CommonResult<ThingPicInfoDetail> selectById(@ApiParam(name = "id",value = "主键ID",required = true) @PathVariable(value = "id") Long id) {
		return thingPicInfoService.selectById(id);
	}
}

