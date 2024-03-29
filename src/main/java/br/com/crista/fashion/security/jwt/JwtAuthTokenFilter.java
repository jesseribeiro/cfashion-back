package br.com.crista.fashion.security.jwt;

import br.com.crista.fashion.security.service.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.Objects.nonNull;

public class JwtAuthTokenFilter extends OncePerRequestFilter {

    private static final String TOKEN_PREFIX = "Bearer";
    private static final String HEADER_STRING = "Authorization";

    @Autowired
    private JwtProvider tokenProvider;
 
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
 
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthTokenFilter.class);
 
    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                    HttpServletResponse response, 
                    FilterChain filterChain) 
                        throws ServletException, IOException {

        try {
          
            String jwt = getJwt(request);

            if (nonNull(jwt) && tokenProvider.validateJwtToken(jwt)) {

                String username = tokenProvider.getUserNameFromJwtToken(jwt);
 
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication
                    = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
 
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        } catch (Exception e) {

            logger.error("Não conseguiu add o usuário em Authorization -> Message: {}", e);
        }
 
        filterChain.doFilter(request, response);
    }
 
    private String getJwt(HttpServletRequest request) {

        String authHeader = request.getHeader("Authorization");
          
        if (nonNull(authHeader) && authHeader.startsWith("Bearer ")) {

          return authHeader.replace("Bearer ","");
        }
 
        return null;
    }
}