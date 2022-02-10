package com.armsmart.jupiter.bs.api.entity;

import lombok.Data;

@Data
public class RandSeedInfo {
    /**
     *合约地址 主键
     */
    private String contractAddr;

    /**
     *seed
     */
    private Long seed;
}
