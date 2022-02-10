package com.armsmart.jupiter.bs.api.dto.request;

import com.google.gson.JsonArray;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(description = "_request")
public class AgentCollectApplyParam {
    private String bizOrderNo;
    private String  payerId;
//    private String
    private List<RecieverListParam> recieverList;
    private Long validateType;
    private Integer payMethod;
    private TlWxPayParam tlWxPayParam;
    private Long amount;
    private String bankCardNo;
    private Long fee;
    private String orderExpireDatetime;
    private String orderIdAgain;
}
