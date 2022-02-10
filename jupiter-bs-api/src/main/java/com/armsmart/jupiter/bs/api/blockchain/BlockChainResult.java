package com.armsmart.jupiter.bs.api.blockchain;

import com.armsmart.common.api.CommonResult;
import com.armsmart.jupiter.bs.api.error.BlockChainError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 区块链服务返回结果处理
 *
 * @author wei.lin
 **/
@Slf4j
@Component
public class BlockChainResult {

    public static ConcurrentHashMap<String, DeferredResult> deferredResultMap = new ConcurrentHashMap<>();

    @Autowired
    private BlockChainProperties blockChainProperties;

    @Scheduled(fixedRate = 5000)
    public void timeoutProcessing() {
        if (deferredResultMap.isEmpty()) {
            return;
        }
        for (Map.Entry<String, DeferredResult> deferredResultEntry : deferredResultMap.entrySet()) {
            String key = deferredResultEntry.getKey();
            Long dateTime = Long.parseLong(key.substring(key.length() - 13, key.length()));
            Long newDateTime = System.currentTimeMillis();
            if (newDateTime - dateTime > blockChainProperties.getTimeout()) {
                DeferredResult value = deferredResultEntry.getValue();
                log.info("定时任务删除数据:{}----》》》》》》》》key is {}", LocalDateTime.now(), key);
                deferredResultMap.remove(key);
                value.setResult(CommonResult.failed(BlockChainError.BLOCK_CHAIN_TIMEOUT));
            }
        }
    }

}
