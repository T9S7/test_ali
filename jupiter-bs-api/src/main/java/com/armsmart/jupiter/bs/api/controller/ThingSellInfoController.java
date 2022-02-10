package com.armsmart.jupiter.bs.api.controller;


import com.armsmart.jupiter.bs.api.dto.request.UserIdQueryParam;
import com.armsmart.jupiter.bs.api.entity.ThingInfo;
import com.armsmart.jupiter.bs.api.entity.ThingSellInfo;
import com.armsmart.jupiter.bs.api.service.ThingSellInfoService;
import com.armsmart.jupiter.bs.api.dto.request.ThingSellInfoAddParam;
import com.armsmart.jupiter.bs.api.dto.request.ThingSellInfoUpdateParam;
import com.armsmart.jupiter.bs.api.dto.request.ThingSellInfoQueryParam;
import com.armsmart.jupiter.bs.api.dto.response.ThingSellInfoDetail;
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
 *  ThingSellInfo 接口
 * @author 苏礼刚
 **/
@RestController
@Api(tags = "物品发布接口（储栋）")
@RequestMapping("/thingSellInfo/")
public class ThingSellInfoController {

	@Autowired
	private ThingSellInfoService thingSellInfoService;

	@PostMapping("add")
	@ApiOperation("添加发布")
	public CommonResult add(@Validated @RequestBody ThingSellInfoAddParam thingSellInfoAddParam) {
		return thingSellInfoService.insert(thingSellInfoAddParam);
	}

	@PostMapping("update")
	@ApiOperation("修改")
	public CommonResult update(@Validated @RequestBody ThingSellInfoUpdateParam thingSellInfoUpdateParam) {
		return thingSellInfoService.update(thingSellInfoUpdateParam);
	}

	@PostMapping("del/{id}")
	@ApiOperation("删除")
	public  CommonResult delete(@ApiParam(name = "id",value = "主键ID",required = true) @PathVariable(value = "id") Long id) {
		return thingSellInfoService.deleteById(id);
	}

	@PostMapping("batchDel")
	@ApiOperation("批量删除")
	public  CommonResult batchDel(@ApiParam(name = "ids",value = "主键ID集合",required = true) @RequestParam(value = "ids") List<Long> ids) {
		return thingSellInfoService.batchDel(ids);
	}

	@GetMapping("selectPage")
	@ApiOperation("分页查询")
	public  CommonResult<CommonPage<ThingSellInfoDetail>> selectPage(ThingSellInfoQueryParam query) {
		return CommonResult.success(thingSellInfoService.selectPage(query));
	}

	@GetMapping("selectById/{id}")
	@ApiOperation("查询详情")
	public CommonResult<ThingSellInfoDetail> selectById(@ApiParam(name = "id",value = "主键ID",required = true) @PathVariable(value = "id") Long id) {
		return thingSellInfoService.selectById(id);
	}

	@GetMapping("thingPutOff/{thingId}")
	@ApiOperation("物品下架")
	public CommonResult thingPutOff(@ApiParam(name = "thingId",value = "物品ID",required = true) @PathVariable(value = "thingId") Long thingId) {
		return thingSellInfoService.thingPutOff(thingId);
	}

	@GetMapping("thingPutOn/{thingId}")
	@ApiOperation("物品上架")
	public CommonResult thingPutOn(@ApiParam(name = "thingId",value = "物品ID",required = true) @PathVariable(value = "thingId") Long thingId) {
		return thingSellInfoService.thingPutOn(thingId);
	}

	/**
	 * 上架物品数
	 */
	@GetMapping("putOnCount")
	@ApiOperation("上架物品数")
	public CommonResult putOnCount(Long userId){
		return CommonResult.success(thingSellInfoService.putOnCount(userId));
	}

}
