package com.armsmart.jupiter.bs.api.controller;


import com.armsmart.jupiter.bs.api.dto.response.GiveInfoDetail;
import com.armsmart.jupiter.bs.api.entity.PushInfo;
import com.armsmart.jupiter.bs.api.service.PushInfoService;
import com.armsmart.jupiter.bs.api.dto.request.PushInfoAddParam;
import com.armsmart.jupiter.bs.api.dto.request.PushInfoUpdateParam;
import com.armsmart.jupiter.bs.api.dto.request.PushInfoQueryParam;
import com.armsmart.jupiter.bs.api.dto.response.PushInfoDetail;
import com.armsmart.common.api.CommonPage;
import com.armsmart.common.api.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 *  PushInfo 接口
 * @author 苏礼刚
 **/
@RestController
@Api(tags = "推送接口")
@RequestMapping("/pushInfo/")
public class PushInfoController {

	@Autowired
	private PushInfoService pushInfoService;

	@PostMapping("add")
	@ApiOperation("添加")
	public CommonResult add(@Validated @RequestBody PushInfoAddParam pushInfoAddParam) {
		return pushInfoService.insert(pushInfoAddParam);
	}

	@PostMapping("update")
	@ApiOperation("修改")
	public CommonResult update(@Validated @RequestBody PushInfoUpdateParam pushInfoUpdateParam) {
		return pushInfoService.update(pushInfoUpdateParam);
	}

	@PostMapping("del/{id}")
	@ApiOperation("删除")
	public  CommonResult delete(@ApiParam(name = "id",value = "主键ID",required = true) @PathVariable(value = "id") Integer id) {
		return pushInfoService.deleteById(id);
	}

	@PostMapping("batchDel")
	@ApiOperation("批量删除")
	public  CommonResult batchDel(@ApiParam(name = "ids",value = "主键ID集合",required = true) @RequestParam(value = "ids") List<Integer> ids) {
		return pushInfoService.batchDel(ids);
	}

	@GetMapping("selectPage")
	@ApiOperation("分页查询")
	public  CommonResult<CommonPage<PushInfoDetail>> selectPage(PushInfoQueryParam query) {
		return CommonResult.success(pushInfoService.selectPage(query));
	}

	@GetMapping("selectById/{id}")
	@ApiOperation("查询详情")
	public CommonResult<PushInfoDetail> selectById(@ApiParam(name = "id",value = "主键ID",required = true) @PathVariable(value = "id") Integer id) {
		return pushInfoService.selectById(id);
	}


	@GetMapping("getPushInfo/{userId}")
	@ApiOperation("查询待处理信息")
	public CommonResult<GiveInfoDetail> getPushInfo(@ApiParam(name = "userId",value = "用户id",required = true) @PathVariable(value = "userId") Long userId) {
		return pushInfoService.getPushInfo(userId);
	}


}

