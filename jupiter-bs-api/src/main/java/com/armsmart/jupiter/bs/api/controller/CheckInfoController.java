package com.armsmart.jupiter.bs.api.controller;


import com.armsmart.jupiter.bs.api.dto.request.UserIdQueryParam;
import com.armsmart.jupiter.bs.api.dto.response.CheckListDetail;
import com.armsmart.jupiter.bs.api.entity.CheckInfo;
import com.armsmart.jupiter.bs.api.service.CheckInfoService;
import com.armsmart.jupiter.bs.api.dto.request.CheckInfoAddParam;
import com.armsmart.jupiter.bs.api.dto.request.CheckInfoUpdateParam;
import com.armsmart.jupiter.bs.api.dto.request.CheckInfoQueryParam;
import com.armsmart.jupiter.bs.api.dto.response.CheckInfoDetail;
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
 *  CheckInfo 接口
 * @author 苏礼刚
 **/
@RestController
@Api(tags = "鉴权记录接口（储栋）")
@RequestMapping("/checkInfo/")
public class CheckInfoController {

	@Autowired
	private CheckInfoService checkInfoService;

	@PostMapping("add")
	@ApiOperation("添加")
	public CommonResult add(@Validated @RequestBody CheckInfoAddParam checkInfoAddParam) {
		return checkInfoService.insert(checkInfoAddParam);
	}

	@GetMapping("selectPage")
	@ApiOperation("NFC 鉴权读取记录")
	public  CommonResult<CommonPage<CheckListDetail>> selectPage(UserIdQueryParam query) {
		return CommonResult.success(checkInfoService.selectPage(query));
	}

	@GetMapping("selectById/{id}")
	@ApiOperation("查询详情")
	public CommonResult<CheckInfoDetail> selectById(@ApiParam(name = "id",value = "主键ID",required = true) @PathVariable(value = "id") Integer id) {
		return checkInfoService.selectById(id);
	}
}

