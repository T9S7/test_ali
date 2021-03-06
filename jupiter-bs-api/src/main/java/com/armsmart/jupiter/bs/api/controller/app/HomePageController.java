package com.armsmart.jupiter.bs.api.controller.app;

import com.armsmart.common.api.CommonPage;
import com.armsmart.common.api.CommonResult;
import com.armsmart.jupiter.bs.api.constants.ThingState;
import com.armsmart.jupiter.bs.api.controller.BaseController;
import com.armsmart.jupiter.bs.api.dto.request.GoodsQueryParam;
import com.armsmart.jupiter.bs.api.dto.request.OrderInfoBuyerParam;
import com.armsmart.jupiter.bs.api.dto.request.OrderInfoQueryParam;
import com.armsmart.jupiter.bs.api.dto.request.OrderInfoSellerParam;
import com.armsmart.jupiter.bs.api.dto.request.PageQueryParam;
import com.armsmart.jupiter.bs.api.dto.request.UserIdQueryParam;
import com.armsmart.jupiter.bs.api.dto.request.UserThingQueryParam;
import com.armsmart.jupiter.bs.api.dto.response.CheckListDetail;
import com.armsmart.jupiter.bs.api.dto.response.CollectSelectListResult;
import com.armsmart.jupiter.bs.api.dto.response.OrderInfoDetail;
import com.armsmart.jupiter.bs.api.dto.response.ThingInfoBidDetail;
import com.armsmart.jupiter.bs.api.dto.response.ThingInfoDetail;
import com.armsmart.jupiter.bs.api.dto.response.ThingShortInfo;
import com.armsmart.jupiter.bs.api.dto.response.UserCountInfoDetail;
import com.armsmart.jupiter.bs.api.dto.response.UserFansListDetail;
import com.armsmart.jupiter.bs.api.service.CheckInfoService;
import com.armsmart.jupiter.bs.api.service.OrderInfoService;
import com.armsmart.jupiter.bs.api.service.ThingInfoService;
import com.armsmart.jupiter.bs.api.service.UserCollectService;
import com.armsmart.jupiter.bs.api.service.UserFansService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author wei.lin
 * @date 2021/12/14
 */
@Api(tags = "APP-??????????????????")
@RestController
@RequestMapping("/homepage/")
public class HomePageController extends BaseController {

    @Autowired
    private OrderInfoService orderInfoService;
    @Autowired
    private CheckInfoService checkInfoService;
    @Autowired
    private ThingInfoService thingInfoService;
    @Autowired
    private UserCollectService userCollectService;
    @Autowired
    private UserFansService userFansService;

    @GetMapping("countInfo")
    @ApiOperation("??????-??????????????????????????????????????????????????????")
    public CommonResult<UserCountInfoDetail> selectCountInfo(@RequestParam(required = false) Long userId) {
        if (null == userId) {
            userId = getCurrentUserId().longValue();
        }
        CommonResult<UserCountInfoDetail> result = thingInfoService.selectCountInfo(userId);
        return result;
    }

    @GetMapping("thingList")
    @ApiOperation("??????|????????????|??????????????????")
    public CommonResult<CommonPage<ThingShortInfo>> thingList(UserThingQueryParam param) {
        UserIdQueryParam userIdQueryParam = new UserIdQueryParam();
        if (null == param.getUserId()) {
            userIdQueryParam.setUserId(getCurrentUserId().longValue());
        } else {
            userIdQueryParam.setUserId(param.getUserId().longValue());
        }
        ThingState thingState = ThingState.getByCode(param.getCurrentState());
        if (null == thingState) {
            return CommonResult.failed("??????????????????????????????");
        }
        switch (thingState) {
            case ON_SALE:
            case NOT_SALE:
                break;
            case PRIVATE:
                if (param.getUserId() != null && !Objects.equals(getCurrentUserId(), param.getUserId())) {
                    return CommonResult.failed("????????????????????????");
                }
                break;
            default:
                return CommonResult.failed("??????????????????????????????");
        }
        return CommonResult.success(thingInfoService.thingList(userIdQueryParam, param.getCurrentState()));
    }

    @GetMapping("myWarrant")
    @ApiOperation("????????????")
    public CommonResult<CommonPage<ThingShortInfo>> myWarrant(PageQueryParam pageQueryParam) {
        UserIdQueryParam userIdQueryParam = new UserIdQueryParam();
        pageQueryParam.setPageNum(pageQueryParam.getPageNum());
        pageQueryParam.setPageSize(pageQueryParam.getPageSize());
        userIdQueryParam.setUserId(getCurrentUserId().longValue());
        return CommonResult.success(thingInfoService.myWarrant(userIdQueryParam));
    }

    /**
     * ????????????
     */
    @GetMapping("bidList")
    @ApiOperation("????????????")
    public CommonResult<CommonPage<ThingInfoBidDetail>> selectBidList(UserIdQueryParam userIdQueryParam) {
        if (userIdQueryParam.getUserId() == null) {
            userIdQueryParam.setUserId(getCurrentUserId().longValue());
        }
        return CommonResult.success(thingInfoService.selectBidList(userIdQueryParam));
    }

    @GetMapping("collectList")
    @ApiOperation("????????????")
    public CommonResult<CommonPage<CollectSelectListResult>> selectCollectList(PageQueryParam pageQueryParam) {
        UserIdQueryParam userIdQueryParam = new UserIdQueryParam();
        userIdQueryParam.setUserId(getCurrentUserId().longValue());
        userIdQueryParam.setPageNum(pageQueryParam.getPageNum());
        userIdQueryParam.setPageSize(pageQueryParam.getPageSize());
        return CommonResult.success(userCollectService.selectCollectList(userIdQueryParam));
    }

    @GetMapping("focusList")
    @ApiOperation("????????????")
    public CommonResult<CommonPage<UserFansListDetail>> selectFocusList(PageQueryParam pageQueryParam) {
        UserIdQueryParam userIdQueryParam = new UserIdQueryParam();
        userIdQueryParam.setUserId(getCurrentUserId().longValue());
        userIdQueryParam.setPageNum(pageQueryParam.getPageNum());
        userIdQueryParam.setPageSize(pageQueryParam.getPageSize());
        return CommonResult.success(userFansService.selectFocusList(userIdQueryParam));
    }

    @GetMapping("fansList")
    @ApiOperation("????????????")
    public CommonResult<CommonPage<UserFansListDetail>> selectFansList(PageQueryParam pageQueryParam) {
        UserIdQueryParam userIdQueryParam = new UserIdQueryParam();
        userIdQueryParam.setUserId(getCurrentUserId().longValue());
        userIdQueryParam.setPageNum(pageQueryParam.getPageNum());
        userIdQueryParam.setPageSize(pageQueryParam.getPageSize());
        return CommonResult.success(userFansService.selectFansList(userIdQueryParam));
    }


    @GetMapping("buyerOrderList")
    @ApiOperation("????????????-??????")
    public CommonResult<CommonPage<OrderInfoDetail>> selectBuyerOrder(@Validated OrderInfoBuyerParam param) {
        OrderInfoQueryParam queryParam = new OrderInfoQueryParam();
        List<Integer> orderCategory = new ArrayList<>();
        orderCategory.add(1);
        orderCategory.add(2);
        queryParam.setOrderCategory(orderCategory);
        queryParam.setBuyerId(getCurrentUserId());
        queryParam.setOrderStatus(param.getOrderStatus());
        queryParam.setPageNum(param.getPageNum());
        queryParam.setPageSize(param.getPageSize());
        queryParam.setBuyerDel(false);
        return CommonResult.success(orderInfoService.selectPage(queryParam));
    }

    @GetMapping("sellerOrderList")
    @ApiOperation("????????????-??????")
    public CommonResult<CommonPage<OrderInfoDetail>> selectSellerOrder(@Validated OrderInfoSellerParam param) {
        OrderInfoQueryParam queryParam = new OrderInfoQueryParam();
        List<Integer> orderCategory = new ArrayList<>();
        orderCategory.add(1);
        orderCategory.add(2);
        queryParam.setOrderCategory(orderCategory);
        queryParam.setOrderStatus(param.getOrderStatus());
        queryParam.setPageNum(param.getPageNum());
        queryParam.setPageSize(param.getPageSize());
        queryParam.setSellerId(getCurrentUserId());
        queryParam.setSellerDel(false);
        return CommonResult.success(orderInfoService.selectPage(queryParam));
    }


    @GetMapping("sellList")
    @ApiOperation("????????????--??????| ??????")
    public CommonResult<CommonPage<ThingShortInfo>> sellingList(GoodsQueryParam param) {
        UserIdQueryParam userIdQueryParam = new UserIdQueryParam();
        userIdQueryParam.setUserId(getCurrentUserId().longValue());
        ThingState thingState = ThingState.getByCode(param.getCurrentState());
        if (null == thingState) {
            return CommonResult.failed("??????????????????????????????");
        }
        switch (thingState) {
            case ON_SALE:
            case PUT_OFF:
                break;
            default:
                return CommonResult.failed("??????????????????????????????");
        }
        return CommonResult.success(thingInfoService.thingList(userIdQueryParam, param.getCurrentState()));
    }

    @GetMapping("soldList")
    @ApiOperation("????????????-??????????????????????????????????????????????????????????????????")
    public CommonResult<CommonPage<ThingShortInfo>> soldList(PageQueryParam param) {
        UserIdQueryParam userIdQueryParam = new UserIdQueryParam();
        userIdQueryParam.setUserId(getCurrentUserId().longValue());
        userIdQueryParam.setPageNum(param.getPageNum());
        userIdQueryParam.setPageSize(param.getPageSize());
        return CommonResult.success(thingInfoService.soldList(userIdQueryParam));
    }

    @GetMapping("checkList")
    @ApiOperation("????????????-????????????????????????")
    public CommonResult<CommonPage<CheckListDetail>> selectPage(PageQueryParam pageQueryParam) {
        UserIdQueryParam userIdQueryParam = new UserIdQueryParam();
        userIdQueryParam.setUserId(getCurrentUserId().longValue());
        userIdQueryParam.setPageNum(pageQueryParam.getPageNum());
        userIdQueryParam.setPageSize(pageQueryParam.getPageSize());
        return CommonResult.success(checkInfoService.selectPage(userIdQueryParam));
    }

    @GetMapping("uploadList")
    @ApiOperation("????????????-??????????????????")
    public CommonResult<CommonPage<ThingInfoDetail>> selectUploadList(PageQueryParam pageQueryParam) {
        UserIdQueryParam userIdQueryParam = new UserIdQueryParam();
        userIdQueryParam.setUserId(getCurrentUserId().longValue());
        userIdQueryParam.setPageNum(pageQueryParam.getPageNum());
        userIdQueryParam.setPageSize(pageQueryParam.getPageSize());
        return CommonResult.success(thingInfoService.selectUploadList(userIdQueryParam));
    }


}
