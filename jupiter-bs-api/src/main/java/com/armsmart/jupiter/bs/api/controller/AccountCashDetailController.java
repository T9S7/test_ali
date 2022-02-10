package com.armsmart.jupiter.bs.api.controller;


import com.armsmart.jupiter.bs.api.entity.AccountCashDetail;
import com.armsmart.jupiter.bs.api.service.AccountCashDetailService;
import com.armsmart.jupiter.bs.api.dto.request.AccountCashDetailAddParam;
import com.armsmart.jupiter.bs.api.dto.request.AccountCashDetailUpdateParam;
import com.armsmart.jupiter.bs.api.dto.request.AccountCashDetailQueryParam;
import com.armsmart.jupiter.bs.api.dto.response.AccountCashDetailDetail;
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
 *  AccountCashDetail 接口
 * @author 苏礼刚
 **/
//@RestController
//@Api(tags = "账户保证金查询接口")
//@RequestMapping("/accountCashDetail/")
public class AccountCashDetailController {

	@Autowired
	private AccountCashDetailService accountCashDetailService;

//
//	@PostMapping("add")
//	@ApiOperation("添加")
//	public CommonResult add(@Validated @RequestBody AccountCashDetailAddParam accountCashDetailAddParam) {
//		return accountCashDetailService.insert(accountCashDetailAddParam);
//	}
//
//	@PostMapping("update")
//	@ApiOperation("修改")
//	public CommonResult update(@Validated @RequestBody AccountCashDetailUpdateParam accountCashDetailUpdateParam) {
//		return accountCashDetailService.update(accountCashDetailUpdateParam);
//	}

//	@PostMapping("del/{id}")
//	@ApiOperation("删除")
//	public  CommonResult delete(@ApiParam(name = "id",value = "主键ID",required = true) @PathVariable(value = "id") Integer id) {
//		return accountCashDetailService.deleteById(id);
//	}
//
//	@PostMapping("batchDel")
//	@ApiOperation("批量删除")
//	public  CommonResult batchDel(@ApiParam(name = "ids",value = "主键ID集合",required = true) @RequestParam(value = "ids") List<Integer> ids) {
//		return accountCashDetailService.batchDel(ids);
//	}
//
	@GetMapping("selectPage")
	@ApiOperation("分页查询")
	public  CommonResult<CommonPage<AccountCashDetailDetail>> selectPage(AccountCashDetailQueryParam query) {
		return CommonResult.success(accountCashDetailService.selectPage(query));
	}

	@GetMapping("selectById/{id}")
	@ApiOperation("查询详情")
	public CommonResult<AccountCashDetailDetail> selectById(@ApiParam(name = "id",value = "主键ID",required = true) @PathVariable(value = "id") Integer id) {
		return accountCashDetailService.selectById(id);
	}
}

