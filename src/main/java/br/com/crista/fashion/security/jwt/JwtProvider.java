package br.com.crista.fashion.security.jwt;

import br.com.crista.fashion.security.service.UserDetailsServiceImpl;
import br.com.crista.fashion.security.service.UsuarioPrincipal;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Component
public class JwtProvider {
 
    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);
 
    @Value("${trulogic.app.jwtSecret}")
    private String jwtSecret;
 
    @Value("${trulogic.app.jwtExpiration}")
    private int jwtExpiration;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;
 
    public String generateJwtToken(Authentication authentication) {
 
        UsuarioPrincipal userPrincipal = (UsuarioPrincipal) authentication.getPrincipal();

        return Jwts.builder()
                    .setSubject(userPrincipal.getUsername())
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                    .signWith(SignatureAlgorithm.HS512, jwtSecret)
                    .compact();
    }
 
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()

                      .setSigningKey(jwtSecret)
                      .parseClaimsJws(token)
                      .getBody().getSubject();
    }
 
    public boolean validateJwtToken(String authToken) {

        try {

            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);

            return true;

        } catch (SignatureException e) {

            logger.error("Invalid JWT signature -> Message: {} ", e);

        } catch (MalformedJwtException e) {

            logger.error("Invalid JWT token -> Message: {}", e);

        } catch (ExpiredJwtException e) {

            logger.error("Expired JWT token -> Message: {}", e);

        } catch (UnsupportedJwtException e) {

            logger.error("Unsupported JWT token -> Message: {}", e);

        } catch (IllegalArgumentException e) {

            logger.error("JWT claims string is empty -> Message: {}", e);
        }
        
        return false;
    }

    public List<String> getRolesUsuarioByToken(String token) {

        try {

            if (nonNull(token)) {

                String username = getUserNameFromJwtToken(token);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                return userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
            }

        } catch (Exception e) {

            logger.error("Não conseguiu add o usuário em Authorization -> Message: {}", e);
        }

        return null;
    }
}