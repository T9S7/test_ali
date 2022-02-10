package com.armsmart.jupiter.bs.api.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.allinpay.sdk.bean.BizParameter;
import com.allinpay.sdk.bean.OpenResponse;
import com.armsmart.common.api.CommonResult;
import com.armsmart.jupiter.bs.api.dto.request.QueryBankBalanceParam;
import com.armsmart.jupiter.bs.api.dto.request.QueryVSPFundParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MerchantSerService extends CertBeforeService {

    /**
     * 查询可退金额
     */
    public CommonResult queryVSPFund(QueryVSPFundParam queryVSPFundParam){
        final BizParameter param = new BizParameter();
        param.put("vspOrgid", queryVSPFundParam.getVspOrgid());
        param.put("vspCusid", queryVSPFundParam.getVspCusid());
        CommonResult commonResult = null;
        try {
            final OpenResponse response = client.execute("allinpay.yunst.merchantService.queryVSPFund", param);
            if ("OK".equals(response.getSubCode())) {
                JSONObject data = JSON.parseObject(response.getData());
                System.out.println(response.getData());
                commonResult = CommonResult.success(data.getString("balance"));
            }else{
                commonResult = CommonResult.failed(response.getSubMsg());
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return commonResult;
    }


    /**
     * 查询可提现金额
     */
    public CommonResult queryReserveFundBalance(){
        final BizParameter param = new BizParameter();
        CommonResult commonResult = null;
        try {
            final OpenResponse response = client.execute("allinpay.yunst.merchantService.queryReserveFundBalance", param);
            if ("OK".equals(response.getSubCode())) {
                JSONObject data = JSON.parseObject(response.getData());
                System.out.println(response.getData());
                commonResult = CommonResult.success(data.getString("balance"));
            }else{
                commonResult = CommonResult.failed(response.getSubMsg());
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return commonResult;
    }


    /**
     * 平台银行存管账户余额查询
     */
    public CommonResult queryBankBalance(QueryBankBalanceParam queryBankBalanceParam){
        final BizParameter param = new BizParameter();
        param.put("acctOrgType",queryBankBalanceParam.getAcctOrgType());
        param.put("acctNo",queryBankBalanceParam.getAcctNo());
        param.put("acctName",queryBankBalanceParam.getAcctName());
        param.put("date",queryBankBalanceParam.getDate());
        CommonResult commonResult = null;
        try {
            final OpenResponse response = client.execute("allinpay.yunst.merchantService.queryBankBalance", param);
            if ("OK".equals(response.getSubCode())) {
                JSONObject data = JSON.parseObject(response.getData());
                System.out.println(response.getData());
                commonResult = CommonResult.success(data.getString("balance"));
            }else{
                commonResult = CommonResult.failed(response.getSubMsg());
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return commonResult;
    }

    /**
     *
     * @param accountSetNo
     * @return
     */
    public CommonResult queryMerchantBalance(String accountSetNo){
        final BizParameter param = new BizParameter();
        param.put("accountSetNo",accountSetNo);
        CommonResult commonResult = null;
        try {
            final OpenResponse response = client.execute("allinpay.yunst.merchantService.queryMerchantBalance", param);
            if ("OK".equals(response.getSubCode())) {
                JSONObject data = JSON.parseObject(response.getData());
                System.out.println(response.getData());
                commonResult = CommonResult.success(data.getString("balance"));
            }else{
                commonResult = CommonResult.failed(response.getSubMsg());
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return commonResult;
    }

    /**
     * 电子回单下载
     */
    public CommonResult eleReceiptDownload(String orderId){
        final BizParameter param = new BizParameter();
        param.put("bizOrderNo",orderId);
        CommonResult commonResult = null;
        try {
            final OpenResponse response = client.execute("allinpay.yunst.merchantService.eleReceiptDownload", param);
            if ("OK".equals(response.getSubCode())) {
                JSONObject data = JSON.parseObject(response.getData());
                System.out.println(response.getData());
                commonResult = CommonResult.success(data.getString("url"));
            }else{
                commonResult = CommonResult.failed(response.getSubMsg());
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return commonResult;
    }
}
