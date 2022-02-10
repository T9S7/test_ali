package com.armsmart.jupiter.bs.api.controller;



import com.armsmart.common.api.CommonPage;
import com.armsmart.common.api.CommonResult;
import com.armsmart.jupiter.bs.api.dto.request.SysRegionAddParam;
import com.armsmart.jupiter.bs.api.dto.request.SysRegionQueryParam;
import com.armsmart.jupiter.bs.api.dto.request.SysRegionUpdateParam;
import com.armsmart.jupiter.bs.api.dto.response.SysRegionDetail;
import com.armsmart.jupiter.bs.api.service.SysRegionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *  SysRegion 接口
 * @author 苏礼刚
 **/
@RestController
@Api(tags = "区域信息接口")
@RequestMapping("/sysRegion/")
public class SysRegionController {

	@Autowired
	private SysRegionService sysRegionService;

	@PostMapping("add")
	@ApiOperation("添加")
	public CommonResult add(@Validated @RequestBody SysRegionAddParam sysRegionAddParam) {
		return CommonResult.success(sysRegionService.insert(sysRegionAddParam));
	}

	@PostMapping("update")
	@ApiOperation("修改")
	public CommonResult update(@Validated @RequestBody SysRegionUpdateParam sysRegionUpdateParam) {
		sysRegionService.update(sysRegionUpdateParam);
		return CommonResult.success();
	}

	@PostMapping("del/{id}")
	@ApiOperation("删除")
	public  CommonResult delete(@ApiParam(name = "id",value = "主键ID",required = true) @PathVariable(value = "id") Integer id) {
		sysRegionService.deleteById(id);
		return CommonResult.success();
	}

	@PostMapping("batchDel")
	@ApiOperation("批量删除")
	public  CommonResult batchDel(@ApiParam(name = "ids",value = "主键ID集合",required = true) @RequestParam(value = "ids") List<Integer> ids) {
		sysRegionService.batchDel(ids);
		return CommonResult.success();
	}

	@GetMapping("selectPage")
	@ApiOperation("分页查询")
	public  CommonResult<CommonPage<SysRegionDetail>> selectPage(SysRegionQueryParam query) {
		return CommonResult.success(sysRegionService.selectPage(query));
	}

	/**
	 * 根据父节点查询子节点
	 * @param regionParent 父节点ID
	 * @date 2020/03/04
	 * @return com.armsmart.abas.app.dto.response.SysRegionDetail
	 */
	@GetMapping("selectByParent")
	@ApiOperation("根据父节点查询子节点")
	public CommonResult<List<SysRegionDetail>>  selectByParent(Integer regionParent) {
		List<SysRegionDetail> list=sysRegionService.selectByParent(regionParent);
		return CommonResult.success(list);
	}
}

