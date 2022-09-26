package br.com.crista.fashion.security;

import br.com.crista.fashion.config.CorsFilter;
import br.com.crista.fashion.security.jwt.JwtAuthTokenFilter;
import br.com.crista.fashion.security.jwt.JwtAuthEntryPoint;
import br.com.crista.fashion.security.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.SessionManagementFilter;

//http://andreybleme.com/2017-04-01/autenticacao-com-jwt-no-spring-boot/

// Olhar esse arquivo para pegar as configurações de acesso e erro no login
//https://github.com/Baeldung/spring-security-registration/blob/master/src/main/java/org/baeldung/spring/SecSecurityConfig.java

/*
* Olá, eu resolvi o problema dos roles da seguinte forma: Eu envio a lista de roles no token,
* depois no filtro de permissão eu recupero as roles que estão no token e passo para a função
* UsernamePasswordAuthenticationToken(usr, null, grants(roles) ) em vez de passar a lista vazia
 */

//Utilizei a implementacao desse site
//https://grokonez.com/spring-framework/spring-boot/spring-security-jwt-authentication-postgresql-restapis-springboot-spring-mvc-spring-jpa
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true
)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    JwtAuthEntryPoint unauthorizedHandler;

    @Bean
    public JwtAuthTokenFilter authenticationJwtTokenFilter() {
        return new JwtAuthTokenFilter();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());

        // cria uma conta default
        authenticationManagerBuilder.inMemoryAuthentication()
                .withUser("user").password("{noop}password").roles("USER")
                .and()
                .withUser("admin").password("{noop}password").roles("ADMIN");
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CorsFilter corsFilter() {
        CorsFilter filter = new CorsFilter();
        return filter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(corsFilter(), SessionManagementFilter.class)
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/h2/**").permitAll()
                .antMatchers("/v1/auth/login").permitAll()
                .antMatchers("/v1/audit/").permitAll()
                .antMatchers("/v1/relatorio/**").permitAll()
                .antMatchers("/v1/notificacao/**").permitAll()
                .antMatchers("/v1/notificacao/stream/**").permitAll()
                .antMatchers("/v1/dimp/**").permitAll()
                .antMatchers("/v1/extract/**").permitAll()
                .antMatchers("/actuator/**").permitAll()
                .antMatchers("/v2/api-docs/**").permitAll()
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler);

        http.csrf().disable();
        http.headers().frameOptions().disable();
    }
}