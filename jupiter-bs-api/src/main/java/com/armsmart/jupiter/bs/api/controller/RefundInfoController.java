package com.armsmart.jupiter.bs.api.controller;


import com.armsmart.jupiter.bs.api.dao.RefundInfoMapper;
import com.armsmart.jupiter.bs.api.dto.request.*;
import com.armsmart.jupiter.bs.api.dto.response.RefundFeeFlowDetail;
import com.armsmart.jupiter.bs.api.entity.RefundInfo;
import com.armsmart.jupiter.bs.api.service.RefundInfoService;
import com.armsmart.jupiter.bs.api.dto.response.RefundInfoDetail;
import com.armsmart.common.api.CommonPage;
import com.armsmart.common.api.CommonResult;
import com.armsmart.jupiter.bs.api.service.TlOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 *  RefundInfo 接口
 * @author 苏礼刚
 **/
@RestController
@Api(tags = "退款接口")
@RequestMapping("/refundInfo/")
public class RefundInfoController {

	@Autowired
	private RefundInfoService refundInfoService;

	@Autowired
	private TlOrderService tlOrderService;

	@Autowired(required = false)
    private RefundInfoMapper refundInfoMapper;

//	@PostMapping("add")
//	@ApiOperation("添加")
//	public CommonResult add(@Validated @RequestBody RefundInfoAddParam refundInfoAddParam) {
//		return refundInfoService.insert(refundInfoAddParam);
//	}
//
//	@PostMapping("update")
//	@ApiOperation("修改")
//	public CommonResult update(@Validated @RequestBody RefundInfoUpdateParam refundInfoUpdateParam) {
//		return refundInfoService.update(refundInfoUpdateParam);
//	}
//
//	@PostMapping("del/{id}")
//	@ApiOperation("删除")
//	public  CommonResult delete(@ApiParam(name = "id",value = "主键ID",required = true) @PathVariable(value = "id") Integer id) {
//		return refundInfoService.deleteById(id);
//	}
//
//	@PostMapping("batchDel")
//	@ApiOperation("批量删除")
//	public  CommonResult batchDel(@ApiParam(name = "ids",value = "主键ID集合",required = true) @RequestParam(value = "ids") List<Integer> ids) {
//		return refundInfoService.batchDel(ids);
//	}
//
//	@GetMapping("selectPage")
//	@ApiOperation("分页查询")
//	public  CommonResult<CommonPage<RefundInfoDetail>> selectPage(RefundInfoQueryParam query) {
//		return CommonResult.success(refundInfoService.selectPage(query));
//	}


//	@GetMapping("selectById/{id}")
//	@ApiOperation("查询详情")
//	public CommonResult<RefundInfoDetail> selectById(@ApiParam(name = "id",value = "主键ID",required = true) @PathVariable(value = "id") Integer id) {
//		return refundInfoService.selectById(id);
//	}

    @GetMapping("feeFlowOneDay")
    @ApiOperation("查询当日流水")
    public CommonResult<RefundFeeFlowDetail> feeFlowOneDay() {
        return refundInfoService.feeFlowOneDay();
    }

	@GetMapping("refundList")
	@ApiOperation("退款列表")
	public  CommonResult<CommonPage<RefundInfoDetail>> refundList(PageQueryParam param) {
		RefundInfoQueryParam query = new RefundInfoQueryParam();
		query.setPageNum(param.getPageNum());
		query.setPageSize(param.getPageSize());
		query.setIsDel(false);
		query.setOrderStatus(0);
		return CommonResult.success(refundInfoService.selectPage(query));
	}

	@PostMapping("refund/{id}")
	@ApiOperation("退款申请")
	public CommonResult refund(@ApiParam(name = "id",value = "主键ID",required = true) @PathVariable(value = "id") Integer id){
		RefundParam param = new RefundParam();
		RefundInfo refundInfo = refundInfoMapper.selectById(id);
		param.setAmont(refundInfo.getAmount());
		param.setOrg_order_id(refundInfo.getOrderId().toString());
		param.setUser_id(refundInfo.getUserId().toString());
		return tlOrderService.refund(param);
	}
}

