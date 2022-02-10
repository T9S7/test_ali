package com.armsmart.jupiter.bs.api.controller;

import com.armsmart.common.api.CommonResult;
import com.armsmart.jupiter.bs.api.dto.request.*;
import com.armsmart.jupiter.bs.api.service.AppNftEthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * CoinInputInfo 接口
 *
 * @author wei.lin
 **/
@RestController
@Api(tags = "App访问NFT-ETH接口")
@RequestMapping("/appEth/nft")
public class AppNftEthController {

    @Autowired
    private AppNftEthService appNftEthService;

    @PostMapping("applyCheck")
    @ApiOperation("申请鉴权")
    public DeferredResult<CommonResult> applyCheck(@Validated @RequestBody ApplyCheckParam applyCheckParam) {
        return appNftEthService.applyCheck(applyCheckParam);
    }

    @GetMapping("rand/{contractAddr}")
    @ApiOperation("获取随机数")
    public DeferredResult<CommonResult> rand(@ApiParam(required = true, value = "合约地址") @PathVariable String contractAddr,@ApiParam(name = "nfcType", value = "鉴权类型", required = true) @RequestParam(value = "nfcType") Integer nfcType) {
        return appNftEthService.rand(contractAddr,nfcType);
    }

    @PostMapping("setArtPubKey")
    @ApiOperation("艺术品公钥录入")
    public DeferredResult<CommonResult> setArtPubKey(@Validated @RequestBody ArtKeyParam artKeyParam) {
        return appNftEthService.setArtPubKey(artKeyParam);
    }

    @PostMapping("setUserKey")
    @ApiOperation("艺术家公钥录入")
    public DeferredResult<CommonResult> setUserKey(@Validated @RequestBody ArtistKeyParam artistKeyParam) {
        return appNftEthService.setUserKey(artistKeyParam);
    }

    @PostMapping("resetContract/{contractAddr}")
    @ApiOperation(value = "重置艺术品合约", hidden = false)
    public DeferredResult<CommonResult> resetContract(@ApiParam(required = true, value = "合约地址") @PathVariable String contractAddr) {
        return appNftEthService.resetContract(contractAddr);
    }

    @GetMapping("getArtInfo/{contractAddr}")
    @ApiOperation(value = "艺术品信息查询", hidden = false)
    public DeferredResult<CommonResult> getArtInfo(@ApiParam(required = true, value = "合约地址") @PathVariable String contractAddr) {
        return appNftEthService.getArtInfo(contractAddr);
    }


    @PatchMapping("modifyOwner")
    @ApiOperation(value = "变更物品所有人信息" ,hidden = false)
    public DeferredResult<CommonResult> modifyOwner(ModifyOwnerAddInfoParam param){
        return appNftEthService.modifyOwner(param);
    }


    @PatchMapping("instanceDepoly")
    @ApiOperation(value = "Jupiter发布合约" ,hidden = false)
    public DeferredResult<CommonResult> instanceDepoly(InstanceDepolyParam param){
        return appNftEthService.instanceDepoly(param);
    }

}
