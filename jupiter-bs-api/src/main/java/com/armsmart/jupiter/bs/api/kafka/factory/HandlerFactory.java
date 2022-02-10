package com.armsmart.jupiter.bs.api.kafka.factory;

import com.armsmart.jupiter.bs.api.kafka.handler.Messagehandler;

public interface HandlerFactory {
    Messagehandler instance(String topicFunc);
}
