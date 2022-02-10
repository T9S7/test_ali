package com.armsmart.jupiter.bs.api.dto.request;

import lombok.Data;

@Data
public class ModifyOwnerAddParam {
    private String authTokenId;

    private String nextDealTokenId;

    private Integer modifyType;

    private String userPubkeyM;

    private String userPubkeyE;

    private String messageIn;

    private String artSign;

    private String webStart;

    private String artistSign;

    private String termInfo;
}
