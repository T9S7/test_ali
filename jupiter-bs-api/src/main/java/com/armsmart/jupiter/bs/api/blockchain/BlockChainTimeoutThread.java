package com.armsmart.jupiter.bs.api.blockchain;

import com.armsmart.common.api.CommonResult;
import org.springframework.web.context.request.async.DeferredResult;

import static com.armsmart.jupiter.bs.api.error.BlockChainError.BLOCK_CHAIN_TIMEOUT;

/**
 * 区块链超时处理线程
 *
 * @author wei.lin
 **/
public class BlockChainTimeoutThread implements Runnable {

    private DeferredResult deferredResult;

    public BlockChainTimeoutThread(DeferredResult deferredResult) {
        this.deferredResult = deferredResult;
    }

    @Override
    public void run() {
        deferredResult.setResult(CommonResult.failed(BLOCK_CHAIN_TIMEOUT));
    }
}
