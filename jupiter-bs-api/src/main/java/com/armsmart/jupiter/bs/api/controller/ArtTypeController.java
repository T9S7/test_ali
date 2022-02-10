package com.armsmart.jupiter.bs.api.controller;


import com.armsmart.jupiter.bs.api.dto.response.SysResourceDetail;
import com.armsmart.jupiter.bs.api.entity.ArtType;
import com.armsmart.jupiter.bs.api.service.ArtTypeService;
import com.armsmart.jupiter.bs.api.dto.request.ArtTypeAddParam;
import com.armsmart.jupiter.bs.api.dto.request.ArtTypeUpdateParam;
import com.armsmart.jupiter.bs.api.dto.request.ArtTypeQueryParam;
import com.armsmart.jupiter.bs.api.dto.response.ArtTypeDetail;
import com.armsmart.common.api.CommonPage;
import com.armsmart.common.api.CommonResult;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 *  ArtType 接口
 * @author 苏礼刚
 **/
@RestController
@Api(tags = "类型管理接口（储栋）")
@RequestMapping("/artType/")
public class ArtTypeController {

	@Autowired
	private ArtTypeService artTypeService;

	@PostMapping("add")
	@ApiOperation("添加")
	public CommonResult add(@Validated @RequestBody ArtTypeAddParam artTypeAddParam) {
		return artTypeService.insert(artTypeAddParam);
	}

	@PostMapping("update")
	@ApiOperation("修改")
	public CommonResult update(@Validated @RequestBody ArtTypeUpdateParam artTypeUpdateParam) {
		return artTypeService.update(artTypeUpdateParam);
	}

	@PostMapping("del/{id}")
	@ApiOperation("删除")
	public  CommonResult delete(@ApiParam(name = "id",value = "主键ID",required = true) @PathVariable(value = "id") Integer id) {
		return artTypeService.deleteById(id);
	}

	@PostMapping("batchDel")
	@ApiOperation("批量删除")
	public  CommonResult batchDel(@ApiParam(name = "ids",value = "主键ID集合",required = true) @RequestParam(value = "ids") List<Integer> ids) {
		return artTypeService.batchDel(ids);
	}

	@GetMapping("selectPage")
	@ApiOperation("分页查询")
	public  CommonResult<CommonPage<ArtTypeDetail>> selectPage(ArtTypeQueryParam query) {
		return CommonResult.success(artTypeService.selectPage(query));
	}

	@GetMapping("selectById/{id}")
	@ApiOperation("查询详情")
	public CommonResult<ArtTypeDetail> selectById(@ApiParam(name = "id",value = "主键ID",required = true) @PathVariable(value = "id") Integer id) {
		return artTypeService.selectById(id);
	}

	@GetMapping("selectListById/{id}")
	@ApiOperation("查询当前类型下所有数据")
	public CommonPage<ArtTypeDetail> selectListById(@ApiParam(name = "id",value = "主键ID",required = true) @PathVariable(value = "id") Integer id) {
		return artTypeService.selectListById(id);
	}

	@GetMapping("selectTree")
	@ApiOperation(value = "查询所有类型-树结构返回", position = 10)
	public CommonResult<List<ArtTypeDetail>> selectTree() {
		List<ArtTypeDetail> list = artTypeService.selectTree();
		return CommonResult.success(list);
	}


	@GetMapping("selectTreePage")
	@ApiOperation(value = "查询所有类型-树结构分页返回")//, position = 10
	public CommonResult<CommonPage<ArtTypeDetail>> selectTreePage() {
		List<ArtTypeDetail> list = artTypeService.selectTree();
		PageInfo<ArtTypeDetail> pageInfo = new PageInfo<>(list);
		return  CommonResult.success(CommonPage.restPage(pageInfo, list));
	}
;
}

