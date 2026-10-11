package com.example.productservice.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.web.server.Http2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
private final JwtService jwtService;


public JwtAuthenticationFilter(JwtService jwtService){
    this.jwtService=jwtService;
}
    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
       String authHeader=request.getHeader("Authorization");
       if(authHeader==null || !authHeader.startsWith("Bearer ")){
           filterChain.doFilter(request,response);
           return;
       }
       String jwt=authHeader.substring(7);
       String email=jwtService.extractEmail(jwt);
       if(email!=null && SecurityContextHolder.getContext().getAuthentication()==null){
           if(jwtService.isValid(jwt)){
               UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(email,null,new ArrayList<>());
               SecurityContextHolder.getContext().setAuthentication(authentication);
           }
       }
           filterChain.doFilter(request,response);
    }


}
