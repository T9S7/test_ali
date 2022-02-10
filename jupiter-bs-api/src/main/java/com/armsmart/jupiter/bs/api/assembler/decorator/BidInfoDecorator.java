package com.armsmart.jupiter.bs.api.assembler.decorator;

import com.armsmart.jupiter.bs.api.assembler.BidInfoAssembler;
import com.armsmart.jupiter.bs.api.constants.BidStatesType;
import com.armsmart.jupiter.bs.api.dto.request.BidInfoAddParam;
import com.armsmart.jupiter.bs.api.entity.BidInfo;

/**
 * BidInfo 对象修饰器
 *
 * @author wei.lin
 **/
public abstract class BidInfoDecorator implements BidInfoAssembler {

    private BidInfoAssembler delegate;

    public BidInfoDecorator(BidInfoAssembler delegate) {
        this.delegate = delegate;
    }


    @Override
    public BidInfo getBidInfo(BidInfoAddParam param) {
        BidInfo bidInfo = delegate.getBidInfo(param);
        bidInfo.setIsDel(false);
        bidInfo.setBidTime(System.currentTimeMillis());
        if (null == bidInfo.getIsAuto()) {
            bidInfo.setIsAuto(false);
        }
        bidInfo.setBidStates(BidStatesType.LEAD.getCode());
        return bidInfo;
    }
}



