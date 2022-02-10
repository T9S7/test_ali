package com.armsmart.jupiter.bs.api.controller;


import com.armsmart.jupiter.bs.api.dto.request.AccountCashDetailQueryParam;
import com.armsmart.jupiter.bs.api.dto.response.AccountCashDetailDetail;
import com.armsmart.jupiter.bs.api.entity.AccountInfo;
import com.armsmart.jupiter.bs.api.service.AccountCashDetailService;
import com.armsmart.jupiter.bs.api.service.AccountInfoService;
import com.armsmart.jupiter.bs.api.dto.request.AccountInfoAddParam;
import com.armsmart.jupiter.bs.api.dto.request.AccountInfoUpdateParam;
import com.armsmart.jupiter.bs.api.dto.request.AccountInfoQueryParam;
import com.armsmart.jupiter.bs.api.dto.response.AccountInfoDetail;
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
 *  AccountInfo 接口
 * @author 苏礼刚
 **/
@RestController
@Api(tags = "账户查询接口")
@RequestMapping("/accountInfo/")
public class AccountInfoController {

	@Autowired(required = false)
	private AccountInfoService accountInfoService;

	@Autowired(required = false)
	private AccountCashDetailService accountCashDetailService;
//	@PostMapping("add")
//	@ApiOperation("添加")
//	public CommonResult add(@Validated @RequestBody AccountInfoAddParam accountInfoAddParam) {
//		return accountInfoService.insert(accountInfoAddParam);
//	}
//
//	@PostMapping("update")
//	@ApiOperation("修改")
//	public CommonResult update(@Validated @RequestBody AccountInfoUpdateParam accountInfoUpdateParam) {
//		return accountInfoService.update(accountInfoUpdateParam);
//	}

//	@PostMapping("del/{accountId}")
//	@ApiOperation("删除")
//	public  CommonResult delete(@ApiParam(name = "accountId",value = "主键ID",required = true) @PathVariable(value = "accountId") Integer accountId) {
//		return accountInfoService.deleteById(accountId);
//	}
//
//	@PostMapping("batchDel")
//	@ApiOperation("批量删除")
//	public  CommonResult batchDel(@ApiParam(name = "accountIds",value = "主键ID集合",required = true) @RequestParam(value = "accountIds") List<Integer> accountIds) {
//		return accountInfoService.batchDel(accountIds);
//	}
//
//	@GetMapping("selectPage")
//	@ApiOperation("分页查询")
//	public  CommonResult<CommonPage<AccountInfoDetail>> selectPage(AccountInfoQueryParam query) {
//		return CommonResult.success(accountInfoService.selectPage(query));
//	}
//
//	@GetMapping("selectById/{accountId}")
//	@ApiOperation("查询账户信息")
//	public CommonResult<AccountInfoDetail> selectById(@ApiParam(name = "accountId",value = "主键ID",required = true) @PathVariable(value = "accountId") Integer accountId) {
//		return accountInfoService.selectById(accountId);
//	}


	@GetMapping("selectById/{userId}")
	@ApiOperation("查询账户信息")
	public CommonResult<AccountInfoDetail> selectById(@ApiParam(name = "userId",value = "用户ID",required = true) @PathVariable(value = "userId") Integer userId) {
		return accountInfoService.selectById(userId);
	}

	@GetMapping("selectCashPage")
	@ApiOperation("查询保证金明细")
	public  CommonResult<CommonPage<AccountCashDetailDetail>> selectCashPage(AccountCashDetailQueryParam query) {
		return CommonResult.success(accountCashDetailService.selectPage(query));
	}

//	@GetMapping("selectCashById/{id}")
//	@ApiOperation("查询保证金明细")
//	public CommonResult<AccountCashDetailDetail> selectCashById(@ApiParam(name = "id",value = "主键ID",required = true) @PathVariable(value = "id") Integer id) {
//		return accountCashDetailService.selectById(id);
//	}
}

