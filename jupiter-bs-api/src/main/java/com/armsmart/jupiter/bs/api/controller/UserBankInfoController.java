package com.armsmart.jupiter.bs.api.controller;


import com.armsmart.jupiter.bs.api.entity.UserBankInfo;
import com.armsmart.jupiter.bs.api.service.UserBankInfoService;
import com.armsmart.jupiter.bs.api.dto.request.UserBankInfoAddParam;
import com.armsmart.jupiter.bs.api.dto.request.UserBankInfoUpdateParam;
import com.armsmart.jupiter.bs.api.dto.request.UserBankInfoQueryParam;
import com.armsmart.jupiter.bs.api.dto.response.UserBankInfoDetail;
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
 *  UserBankInfo 接口
 * @author 苏礼刚
 **/
@RestController
@Api(tags = "钱包--用户绑定银行卡接口")
@RequestMapping("/userBankInfo/")
public class UserBankInfoController {

	@Autowired
	private UserBankInfoService userBankInfoService;

	@PostMapping("add")
	@ApiOperation("添加")
	public CommonResult add(@Validated @RequestBody UserBankInfoAddParam userBankInfoAddParam) {
		return userBankInfoService.insert(userBankInfoAddParam);
	}

	@PostMapping("update")
	@ApiOperation("修改")
	public CommonResult update(@Validated @RequestBody UserBankInfoUpdateParam userBankInfoUpdateParam) {
		return userBankInfoService.update(userBankInfoUpdateParam);
	}

	@PostMapping("del/{id}")
	@ApiOperation("删除")
	public  CommonResult delete(@ApiParam(name = "id",value = "主键ID",required = true) @PathVariable(value = "id") Integer id) {
		return userBankInfoService.deleteById(id);
	}

	@PostMapping("batchDel")
	@ApiOperation("批量删除")
	public  CommonResult batchDel(@ApiParam(name = "ids",value = "主键ID集合",required = true) @RequestParam(value = "ids") List<Integer> ids) {
		return userBankInfoService.batchDel(ids);
	}

	@GetMapping("selectPage")
	@ApiOperation("分页查询")
	public  CommonResult<CommonPage<UserBankInfoDetail>> selectPage(UserBankInfoQueryParam query) {
		return CommonResult.success(userBankInfoService.selectPage(query));
	}

	@GetMapping("userBankList")
	@ApiOperation("用户绑定银行卡列表")
	public  CommonResult<CommonPage<UserBankInfoDetail>> userBankList() {
		return CommonResult.success(userBankInfoService.userBankList());
	}

	@GetMapping("selectById/{id}")
	@ApiOperation("查询详情")
	public CommonResult<UserBankInfoDetail> selectById(@ApiParam(name = "id",value = "主键ID",required = true) @PathVariable(value = "id") Integer id) {
		return userBankInfoService.selectById(id);
	}
}

