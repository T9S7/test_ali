package com.armsmart.jupiter.bs.api.controller;


import com.armsmart.common.api.CommonPage;
import com.armsmart.common.api.CommonResult;
import com.armsmart.jupiter.bs.api.dto.request.UserAddressAddParam;
import com.armsmart.jupiter.bs.api.dto.request.UserAddressMyQueryParam;
import com.armsmart.jupiter.bs.api.dto.request.UserAddressQueryParam;
import com.armsmart.jupiter.bs.api.dto.request.UserAddressUpdateParam;
import com.armsmart.jupiter.bs.api.dto.response.UserAddressDetail;
import com.armsmart.jupiter.bs.api.service.UserAddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * UserAddress 接口
 *
 * @author wei.lin
 **/
@RestController
@Api(tags = "用户地址接口（储栋）")
@RequestMapping("/userAddress/")
public class UserAddressController {

    @Autowired
    private UserAddressService userAddressService;

    @PostMapping("add")
    @ApiOperation("新增地址")
    public CommonResult add(@Validated @RequestBody UserAddressAddParam userAddressAddParam) {
        return CommonResult.success(userAddressService.insert(userAddressAddParam));
    }

    @PostMapping("update")
    @ApiOperation("修改地址")
    public CommonResult update(@Validated @RequestBody UserAddressUpdateParam userAddressUpdateParam) {
        userAddressService.update(userAddressUpdateParam);
        return CommonResult.success();
    }

    @PostMapping("del/{id}")
    @ApiOperation("删除指定地址")
    public CommonResult delete(@ApiParam(name = "id", value = "主键ID", required = true) @PathVariable(value = "id") Integer id) {
        userAddressService.deleteById(id);
        return CommonResult.success();
    }

    @PostMapping("batchDel")
    @ApiOperation("批量删除")
    public CommonResult batchDel(@ApiParam(name = "ids", value = "主键ID集合", required = true) @RequestParam(value = "ids") List<Integer> ids) {
        userAddressService.batchDel(ids);
        return CommonResult.success();
    }

    @GetMapping("selectPage")
    @ApiOperation("分页查询")
    public CommonResult<CommonPage<UserAddressDetail>> selectPage(UserAddressQueryParam query) {
        return CommonResult.success(userAddressService.selectPage(query));
    }

    @GetMapping("myAddress")
    @ApiOperation("我的地址")
    public CommonResult<List<UserAddressDetail>> myAddress(@Validated UserAddressMyQueryParam query) {
        List<UserAddressDetail> userAddressList = userAddressService.myAddress(query);
        return CommonResult.success(userAddressList);
    }

    @GetMapping("myDefaultAddress/{userId}")
    @ApiOperation("我的默认地址")
    public CommonResult<UserAddressDetail> myDefaultAddress(@PathVariable Integer userId) {
        return userAddressService.myDefaultAddress(userId);
    }

}

