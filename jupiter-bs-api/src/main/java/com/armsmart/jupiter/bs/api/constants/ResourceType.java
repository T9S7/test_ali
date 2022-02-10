package com.armsmart.jupiter.bs.api.constants;

import lombok.Getter;

@Getter
public enum ResourceType {
    MENU("菜单", 1),
    BUTTON("按钮", 2),
    SEE("查看", 3);

    String name;

    int code;

    ResourceType(String name, int code) {
        this.name = name;
        this.code = code;
    }
}
