package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PageQueryParam {

    @ApiModelProperty("页码")
    protected int pageNum = 1;
    @ApiModelProperty("页大小")
    protected int pageSize = 20;
    @ApiModelProperty(value = "排序字段", hidden = true)
    protected String orderBy;
    @ApiModelProperty(value = "排序方式", hidden = true)
    protected String orderSort;

    public int getPageNum() {
        if (pageNum <= 0) {
            pageNum = 1;
        }
        return pageNum;
    }

    public int getPageSize() {
        if (pageSize > 200) {
            pageSize = 200;
        }
        return pageSize;
    }
}
