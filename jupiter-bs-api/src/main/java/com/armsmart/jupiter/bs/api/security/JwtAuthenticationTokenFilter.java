package com.armsmart.jupiter.bs.api.security;

import com.armsmart.jupiter.bs.api.service.SysUserService;
import com.armsmart.jupiter.bs.api.service.UserAuthService;
import com.armsmart.common.config.IgnoreUrlsConfig;
import com.armsmart.common.config.JwtConfig;
import com.armsmart.common.utils.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * JWT登录授权过滤器
 *
 * @author wei.lin
 */
@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private UserAuthService userAuthService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private JwtConfig jwtConfig;
    @Autowired
    private IgnoreUrlsConfig ignoreUrlsConfig;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        //OPTIONS请求直接放行
        if (request.getMethod().equals(HttpMethod.OPTIONS.toString())) {
            chain.doFilter(request, response);
            return;
        }
        //白名单请求直接放行
        PathMatcher pathMatcher = new AntPathMatcher();
        for (String path : ignoreUrlsConfig.getUrls()) {
            if (pathMatcher.match(path, request.getRequestURI())) {
                chain.doFilter(request, response);
                return;
            }
        }

        String authHeader = request.getHeader(jwtConfig.getTokenHeader());
        if (authHeader != null && authHeader.startsWith(jwtConfig.getTokenHead())) {
            String authToken = authHeader.substring(jwtConfig.getTokenHead().length());
            String username = jwtTokenUtil.getUserNameFromToken(authToken);
            log.info("checking username:{}", username);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                boolean isAppUser = jwtTokenUtil.getIsAppUser(authToken);
                UserDetails userDetails = null;
                boolean auth = true;
                if (!isAppUser) {
                    userDetails = this.sysUserService.loadUserByUsername(username);
                } else {
                    userDetails = this.userAuthService.loadUserByUsername(username);
                    AppUserDetails appUserDetails = (AppUserDetails) userDetails;
                    String authCode = appUserDetails.getUserInfo().getAuthCode();
                    String receivedCode = jwtTokenUtil.getAuthCode(authToken);
                    auth = Objects.equals(authCode, receivedCode);
                }
                if (auth && jwtTokenUtil.validateToken(authToken, userDetails)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    log.info("authenticated user:{}", username);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        chain.doFilter(request, response);
    }
}
