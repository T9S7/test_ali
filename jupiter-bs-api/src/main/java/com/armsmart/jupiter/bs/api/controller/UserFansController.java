package com.armsmart.jupiter.bs.api.controller;


import com.armsmart.jupiter.bs.api.dto.request.*;
import com.armsmart.jupiter.bs.api.dto.response.ThingUserIdStateDetail;
import com.armsmart.jupiter.bs.api.dto.response.UserFansCountDetail;
import com.armsmart.jupiter.bs.api.dto.response.UserFansListDetail;
import com.armsmart.jupiter.bs.api.service.UserFansService;
import com.armsmart.jupiter.bs.api.dto.response.UserFansDetail;
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
 *  UserFans 接口
 * @author 苏礼刚
 **/
@RestController
@Api(tags = "关注粉丝接口（储栋）")
@RequestMapping("/userFans/")
public class UserFansController {

	@Autowired
	private UserFansService userFansService;

	@PostMapping("add/{userId}")
	@ApiOperation("APP-添加关注")
	public CommonResult add(@ApiParam(name = "userId",value = "被关注用户Id",required = true) @PathVariable(value = "userId") Long userId) {
		return userFansService.insert(userId);
	}

	@PostMapping("cancel/{userId}")
	@ApiOperation("APP-取消关注")
	public CommonResult cancel(@ApiParam(name = "userId",value = "被关注用户Id",required = true) @PathVariable(value = "userId") Long userId) {
		return userFansService.cancel(userId);
	}


	@PostMapping("update")
	@ApiOperation("修改")
	public CommonResult update(@Validated @RequestBody UserFansUpdateParam userFansUpdateParam) {
		return userFansService.update(userFansUpdateParam);
	}

	@PostMapping("del/{id}")
	@ApiOperation("删除")
	public  CommonResult delete(@ApiParam(name = "id",value = "主键ID",required = true) @PathVariable(value = "id") Long id) {
		return userFansService.deleteById(id);
	}

	@PostMapping("batchDel")
	@ApiOperation("批量删除")
	public  CommonResult batchDel(@ApiParam(name = "ids",value = "主键ID集合",required = true) @RequestParam(value = "ids") List<Long> ids) {
		return userFansService.batchDel(ids);
	}

	@GetMapping("selectPage")
	@ApiOperation("分页查询")
	public  CommonResult<CommonPage<UserFansDetail>> selectPage(UserFansQueryParam query) {
		return CommonResult.success(userFansService.selectPage(query));
	}

	@GetMapping("selectById/{id}")
	@ApiOperation("查询详情")
	public CommonResult<UserFansDetail> selectById(@ApiParam(name = "id",value = "主键ID",required = true) @PathVariable(value = "id") Long id) {
		return userFansService.selectById(id);
	}

	@GetMapping("selectCount/{userId}")
	@ApiOperation("用户关注信息统计")
	public CommonResult<UserFansCountDetail> selectCount(@ApiParam(name = "userId",value = "用户ID",required = true) @PathVariable(value = "userId") Long userId){
		return userFansService.selectCount(userId);
	}


	@GetMapping("getFocusState")
	@ApiOperation("查询对当前用户的关注状态")
	public CommonResult<ThingUserIdStateDetail> getFocusState(ThingUserIdStateParam param){
		return userFansService.getFocusState(param);
	}
}

