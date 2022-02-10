package com.armsmart.jupiter.bs.api.controller;


import com.armsmart.common.api.CommonPage;
import com.armsmart.common.api.CommonResult;
import com.armsmart.jupiter.bs.api.dto.request.UserAuthenticationAddParam;
import com.armsmart.jupiter.bs.api.dto.request.UserAuthenticationBindParam;
import com.armsmart.jupiter.bs.api.dto.request.UserAuthenticationQueryParam;
import com.armsmart.jupiter.bs.api.dto.request.UserAuthenticationUpdateParam;
import com.armsmart.jupiter.bs.api.dto.response.UserAuthenticationDetail;
import com.armsmart.jupiter.bs.api.entity.UserAuthentication;
import com.armsmart.jupiter.bs.api.service.UserAuthenticationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 *  UserAuthentication 接口
 * @author wei.lin
 **/
@RestController
@Api(tags = "实人认证接口")
@RequestMapping("/userAuthentication/")
public class UserAuthenticationController {

	@Autowired
	private UserAuthenticationService userAuthenticationService;

	@PostMapping("add")
	@ApiOperation("添加")
	public CommonResult add(@Validated @RequestBody UserAuthenticationAddParam userAuthenticationAddParam) {
		return userAuthenticationService.insert(userAuthenticationAddParam);
	}

	@PostMapping("update")
	@ApiOperation("修改")
	public CommonResult update(@Validated @RequestBody UserAuthenticationUpdateParam userAuthenticationUpdateParam) {
		userAuthenticationService.update(userAuthenticationUpdateParam);
		return CommonResult.success();
	}

	@PostMapping("bind")
	@ApiOperation("公钥绑定")
	public CommonResult bind(@Validated @RequestBody UserAuthenticationBindParam userAuthenticationBindParam) {
		userAuthenticationService.bind(userAuthenticationBindParam);
		return CommonResult.success(userAuthenticationService.bind(userAuthenticationBindParam));
	}

	@PostMapping("del/{id}")
	@ApiOperation("删除")
	public CommonResult delete(@ApiParam(name = "id",value = "主键ID",required = true) @PathVariable(value = "id") Long id) {
		userAuthenticationService.deleteById(id);
		return CommonResult.success();
	}

	@PostMapping("batchDel")
	@ApiOperation("批量删除")
	public CommonResult batchDel(@ApiParam(name = "ids",value = "主键ID集合",required = true) @RequestParam(value = "ids") List<Long> ids) {
		userAuthenticationService.batchDel(ids);
		return CommonResult.success();
	}

	@GetMapping("selectPage")
	@ApiOperation("分页查询")
	public CommonResult<CommonPage<UserAuthenticationDetail>> selectPage(UserAuthenticationQueryParam query) {
		return CommonResult.success(userAuthenticationService.selectPage(query));
		}

	@PostMapping("updateCert")
	@ApiOperation("认证确认")
	public CommonResult updateCert( @ApiParam(name = "certificateNo",value = "身份证号码",required = true) @RequestParam(value = "certificateNo")String certificateNo) {
		userAuthenticationService.updateCert(certificateNo);
		return CommonResult.success();
	}

	@PostMapping("describeVerify")
	@ApiOperation("实人认证结果查询")
	public  CommonResult describeVerify(@ApiParam(name = "certificateNo",value = "身份证号码",required = true) @RequestParam(value = "certificateNo") String certificateNo) {
		return CommonResult.success(userAuthenticationService.describeVerify(certificateNo));
	}

	@PostMapping("selectByUserId")
	@ApiOperation("当前用户认证信息")
	public CommonResult<UserAuthentication> selectByUserId(@ApiParam(name = "userId",value = "用户编号",required = true) @RequestParam(value = "userId")String userId) {
		return CommonResult.success(userAuthenticationService.selectByUserId(userId));
	}


}

