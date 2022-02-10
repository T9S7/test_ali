package com.armsmart.jupiter.bs.api.kafka.handler;

public interface Messagehandler {

    /**
     *
     * @param obj 要发送的消息
     */
    void sendMessageHandler(Object obj);

    /**
     *
     * @param obj 消费的消息
     */
    void  consumerMessageHandler(Object obj);

}
