package com.armsmart.jupiter.bs.api.controller;



import com.armsmart.common.api.CommonPage;
import com.armsmart.common.api.CommonResult;
import com.armsmart.jupiter.bs.api.service.WechatOrderInfoService;
import com.armsmart.jupiter.bs.api.dto.request.WechatOrderInfoAddParam;
import com.armsmart.jupiter.bs.api.dto.request.WechatOrderInfoUpdateParam;
import com.armsmart.jupiter.bs.api.dto.request.WechatOrderInfoQueryParam;
import com.armsmart.jupiter.bs.api.dto.response.WechatOrderInfoDetail;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 *  WechatOrderInfo 接口
 * @author 苏礼刚
 **/
@RestController
@Api(tags = "微信支付订单接口")
@RequestMapping("/wechatOrderInfo/")
public class WechatOrderInfoController {

	@Autowired
	private WechatOrderInfoService wechatOrderInfoService;

	@PostMapping("add")
	@ApiOperation("添加")
	public CommonResult add(@Validated @RequestBody WechatOrderInfoAddParam wechatOrderInfoAddParam) {
		return wechatOrderInfoService.insert(wechatOrderInfoAddParam);
	}

	@PostMapping("update")
	@ApiOperation("修改")
	public CommonResult update(@Validated @RequestBody WechatOrderInfoUpdateParam wechatOrderInfoUpdateParam) {
		return wechatOrderInfoService.update(wechatOrderInfoUpdateParam);
	}

	@PostMapping("del/{id}")
	@ApiOperation("删除")
	public  CommonResult delete(@ApiParam(name = "id",value = "主键ID",required = true) @PathVariable(value = "id") Integer id) {
		return wechatOrderInfoService.deleteById(id);
	}

	@PostMapping("batchDel")
	@ApiOperation("批量删除")
	public  CommonResult batchDel(@ApiParam(name = "ids",value = "主键ID集合",required = true) @RequestParam(value = "ids") List<Integer> ids) {
		return wechatOrderInfoService.batchDel(ids);
	}

	@GetMapping("selectPage")
	@ApiOperation("分页查询")
	public  CommonResult<CommonPage<WechatOrderInfoDetail>> selectPage(WechatOrderInfoQueryParam query) {
		return CommonResult.success(wechatOrderInfoService.selectPage(query));
	}

	@GetMapping("selectById/{id}")
	@ApiOperation("查询详情")
	public CommonResult<WechatOrderInfoDetail> selectById(@ApiParam(name = "id",value = "主键ID",required = true) @PathVariable(value = "id") Integer id) {
		return wechatOrderInfoService.selectById(id);
	}
}

