package com.armsmart.jupiter.gateway.bside.security;

import cn.hutool.core.util.URLUtil;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 动态权限数据源
 *
 * @author wei.lin
 */
@Component
public class DynamicSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private static Map<String, ConfigAttribute> configAttributeMap = null;


    @PostConstruct
    public void loadDataSource() {
        configAttributeMap = new ConcurrentHashMap<>();
        configAttributeMap.put("/auth/token", new SecurityConfig("auth"));
        //微服务改变前，因web和app两套账户体系同时存在，暂时关闭api权限校验
       /* List<SysResource> resourceList = sysResourceService.selectAll();
        if (!CollectionUtils.isEmpty(resourceList)) {
            for (SysResource resource : resourceList) {
                if (StringUtils.isNotBlank(resource.getResourcePath())) {
                    configAttributeMap.put(resource.getResourcePath(), new SecurityConfig(resource.getResourceId() + ":" + resource.getResourceKey()));
                }
            }
        }*/
    }

    public void clearDataSource() {
        configAttributeMap.clear();
        configAttributeMap = null;
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        if (configAttributeMap == null) {
            this.loadDataSource();
        }
        List<ConfigAttribute> configAttributes = new ArrayList<>();
        String url = ((FilterInvocation) o).getRequestUrl();
        String path = URLUtil.getPath(url);
        PathMatcher pathMatcher = new AntPathMatcher();
        Iterator<String> iterator = configAttributeMap.keySet().iterator();
        while (iterator.hasNext()) {
            String pattern = iterator.next();
            if (pathMatcher.match(pattern, path)) {
                configAttributes.add(configAttributeMap.get(pattern));
            }
        }
        return configAttributes;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

}
