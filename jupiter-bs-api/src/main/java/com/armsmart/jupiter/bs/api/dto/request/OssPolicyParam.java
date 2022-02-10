package com.armsmart.jupiter.bs.api.dto.request;

import com.armsmart.common.utils.JsonUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
public class OssPolicyParam {

    public static final String PUT = "oss:PutObject";
    public static final String PUT_TAGGING = "oss:PutObjectTagging";

    @JsonProperty("Version")
    private String version;
    @JsonProperty("Statement")
    private List<Statement> statement;

    @Data
    public class Statement {
        @JsonProperty("Effect")
        private String effect;
        @JsonProperty("Action")
        private List<String> action;
        @JsonProperty("Resource")
        private List<String> resource;
    }

    public String put(String bucketName) {
        OssPolicyParam policy = new OssPolicyParam();
        policy.setVersion("1");
        Statement statement = new Statement();
        statement.setEffect("Allow");
        statement.setResource(Arrays.asList("acs:oss:*:*:" + bucketName + "/*"));
        statement.setAction(Arrays.asList(PUT, PUT_TAGGING));
        policy.setStatement(Arrays.asList(statement));
        return JsonUtil.bean2Json(policy);
    }

    public static void main(String[] args) {
        OssPolicyParam policyParam = new OssPolicyParam();
        System.out.println(policyParam.put("aa"));
    }
}
