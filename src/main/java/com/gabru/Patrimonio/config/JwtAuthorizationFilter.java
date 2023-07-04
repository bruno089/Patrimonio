package com.gabru.Patrimonio.config;

import com.gabru.Patrimonio.business_services.JwtService;
import com.gabru.Patrimonio.entities.Role;
import com.gabru.Patrimonio.exceptions.JwtException;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class JwtAuthorizationFilter  extends BasicAuthenticationFilter {
    private static final String AUTHORIZATION = "Authorization";

    @Autowired
    JwtService jwtService;

    public JwtAuthorizationFilter( AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }


    @Override
    protected void doFilterInternal( HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String authHeader = req.getHeader(AUTHORIZATION);
        if (jwtService.isBearer(authHeader)) {
            List<GrantedAuthority> authorities;
            try {
                authorities = jwtService.roles(authHeader).stream().map(role -> new SimpleGrantedAuthority(Role.valueOf(role).roleName())).collect(Collectors.toList());
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                jwtService.user(authHeader),
                                null,
                                authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (JwtException e) { //Exception not authorized , for JWTService
                LogManager.getLogger(this.getClass().getName()).debug(">>> FILTER JWT UNAUTHORIZED ..."
                        + req.getHeader(AUTHORIZATION) + e.getMessage());
                res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }
        chain.doFilter(req, res);
    }
}