package com.armsmart.jupiter.bs.api.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RandSeedMapper {
    int ifExeist(String contractAddr);
    int insert(String contractAddr);
    int update(String contractAddr);
    long getSeed(String contractAddr);
}
