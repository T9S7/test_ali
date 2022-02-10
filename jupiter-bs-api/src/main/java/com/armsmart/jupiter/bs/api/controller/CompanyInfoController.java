package com.armsmart.jupiter.bs.api.controller;


import com.armsmart.jupiter.bs.api.dto.request.*;
import com.armsmart.jupiter.bs.api.dto.response.UserCompanyResult;
import com.armsmart.jupiter.bs.api.entity.CompanyInfo;
import com.armsmart.jupiter.bs.api.entity.UserCompany;
import com.armsmart.jupiter.bs.api.service.CompanyInfoService;
import com.armsmart.jupiter.bs.api.dto.response.CompanyInfoDetail;
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
 *  CompanyInfo 接口
 * @author 苏礼刚
 **/
@RestController
@Api(tags = "企业信息接口")
@RequestMapping("/companyInfo/")
public class CompanyInfoController {

	@Autowired
	private CompanyInfoService companyInfoService;

	@PostMapping("add")
	@ApiOperation("添加")
	public CommonResult add(@Validated @RequestBody CompanyInfoAddParam companyInfoAddParam) {
		return companyInfoService.insert(companyInfoAddParam);
	}

	@PostMapping("update")
	@ApiOperation("修改")
	public CommonResult update(@Validated @RequestBody CompanyInfoUpdateParam companyInfoUpdateParam) {
		return companyInfoService.update(companyInfoUpdateParam);
	}

	@PostMapping("del/{id}")
	@ApiOperation("删除")
	public  CommonResult delete(@ApiParam(name = "id",value = "主键ID",required = true) @PathVariable(value = "id") Integer id) {
		return companyInfoService.deleteById(id);
	}

	@PostMapping("batchDel")
	@ApiOperation("批量删除")
	public  CommonResult batchDel(@ApiParam(name = "ids",value = "主键ID集合",required = true) @RequestParam(value = "ids") List<Integer> ids) {
		return companyInfoService.batchDel(ids);
	}

	@GetMapping("selectPage")
	@ApiOperation("分页查询")
	public  CommonResult<CommonPage<CompanyInfoDetail>> selectPage(CompanyInfoQueryParam query) {
		return CommonResult.success(companyInfoService.selectPage(query));
	}

	@GetMapping("selectById/{id}")
	@ApiOperation("查询详情")
	public CommonResult<CompanyInfoDetail> selectById(@ApiParam(name = "id",value = "主键ID",required = true) @PathVariable(value = "id") Integer id) {
		return companyInfoService.selectById(id);
	}

	@PostMapping("addUser")
	@ApiOperation("企业添加用户")
	public CommonResult addUser(@Validated @RequestBody UserCompanyAddParam param) {
		return companyInfoService.addUser(param);
	}

	@PostMapping("updateState/{userId}")
	@ApiOperation("修改是否官方授权")
	public CommonResult updateState(@PathVariable(value = "userId")Long userId) {
		return companyInfoService.updateState(userId);
	}

	@GetMapping("selectCompUser")
	@ApiOperation("查询企业下用户信息")
	public CommonResult<CommonPage<UserCompanyResult>> selectCompUser(CompanyIdQueryParam param){
		return companyInfoService.selectComUser(param);
	}

}

