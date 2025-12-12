package com.kpl.registration.configJWT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    @Autowired
    JwtService jwtService;
    @Autowired
    UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        var whiteListedApi = List.of("/kpl/jwt/api/validId.*", "/v3/api-docs.*", "/swagger-ui.*", "/swagger-ui.html");
        var authHeader = request.getHeader("Authorization");
        String token;
        String username;
        String uri = request.getRequestURI();
        boolean uriMatchs = false;

        for (String api : whiteListedApi) {
            uriMatchs = uri.matches(api);
            if (uriMatchs)
                break;
        }
//        if (authHeader != null && authHeader.startsWith("Bearer ")) {
//            token = authHeader.substring(7);
//            username = jwtService.extractUserName(token);
//        }
        if (authHeader != null && SecurityContextHolder.getContext().getAuthentication() == null ||
                uriMatchs) {
            if (authHeader != null) {
                token = authHeader.substring(7);
                username = jwtService.extractUserName(token);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (jwtService.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
            filterChain.doFilter(request, response);
        } else {
            logger.info("Not allowed without token");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
