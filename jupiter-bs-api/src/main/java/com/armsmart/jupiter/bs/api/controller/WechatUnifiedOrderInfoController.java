package com.armsmart.jupiter.bs.api.controller;

import com.armsmart.common.api.CommonPage;
import com.armsmart.common.api.CommonResult;
import com.armsmart.jupiter.bs.api.service.WechatUnifiedOrderInfoService;
import com.armsmart.jupiter.bs.api.dto.request.WechatUnifiedOrderInfoAddParam;
import com.armsmart.jupiter.bs.api.dto.request.WechatUnifiedOrderInfoUpdateParam;
import com.armsmart.jupiter.bs.api.dto.request.WechatUnifiedOrderInfoQueryParam;
import com.armsmart.jupiter.bs.api.dto.response.WechatUnifiedOrderInfoDetail;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 *  WechatUnifiedOrderInfo 接口
 * @author 苏礼刚
 **/
@RestController
@Api(tags = "微信支付相关接口")
@RequestMapping("/wechatUnifiedOrderInfo/")
public class WechatUnifiedOrderInfoController {

	@Autowired
	private WechatUnifiedOrderInfoService wechatUnifiedOrderInfoService;

	@PostMapping("add")
	@ApiOperation("添加")
	public CommonResult add(@Validated @RequestBody WechatUnifiedOrderInfoAddParam wechatUnifiedOrderInfoAddParam) {
		return wechatUnifiedOrderInfoService.insert(wechatUnifiedOrderInfoAddParam);
	}

	@PostMapping("update")
	@ApiOperation("修改")
	public CommonResult update(@Validated @RequestBody WechatUnifiedOrderInfoUpdateParam wechatUnifiedOrderInfoUpdateParam) {
		return wechatUnifiedOrderInfoService.update(wechatUnifiedOrderInfoUpdateParam);
	}

	@PostMapping("del/{id}")
	@ApiOperation("删除")
	public  CommonResult delete(@ApiParam(name = "id",value = "主键ID",required = true) @PathVariable(value = "id") Integer id) {
		return wechatUnifiedOrderInfoService.deleteById(id);
	}

	@PostMapping("batchDel")
	@ApiOperation("批量删除")
	public  CommonResult batchDel(@ApiParam(name = "ids",value = "主键ID集合",required = true) @RequestParam(value = "ids") List<Integer> ids) {
		return wechatUnifiedOrderInfoService.batchDel(ids);
	}

	@GetMapping("selectPage")
	@ApiOperation("分页查询")
	public  CommonResult<CommonPage<WechatUnifiedOrderInfoDetail>> selectPage(WechatUnifiedOrderInfoQueryParam query) {
		return CommonResult.success(wechatUnifiedOrderInfoService.selectPage(query));
	}

	@GetMapping("selectById/{id}")
	@ApiOperation("查询详情")
	public CommonResult<WechatUnifiedOrderInfoDetail> selectById(@ApiParam(name = "id",value = "主键ID",required = true) @PathVariable(value = "id") Integer id) {
		return wechatUnifiedOrderInfoService.selectById(id);
	}
}

