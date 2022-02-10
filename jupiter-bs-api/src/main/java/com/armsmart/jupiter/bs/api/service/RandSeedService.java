package com.armsmart.jupiter.bs.api.service;

import com.armsmart.jupiter.bs.api.dao.RandSeedMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RandSeedService {
    @Autowired(required = false)
    private RandSeedMapper randSeedMapper;

    /**
     * 查询合约下是否有seed，没有则添加一条，有则返回seed
     */
    public Long getSeed(String tokenId){
        log.info("能执行了");
        Long seed;
        if(randSeedMapper.ifExeist(tokenId) > 0){
            log.info("进来了");
            seed = randSeedMapper.getSeed(tokenId) + 1;
            randSeedMapper.update(tokenId);
        }else{
            log.info("到了else");
            randSeedMapper.insert(tokenId);
            seed = Long.valueOf(1);

        }
        log.info("结束了");
        return seed;
    }

}
