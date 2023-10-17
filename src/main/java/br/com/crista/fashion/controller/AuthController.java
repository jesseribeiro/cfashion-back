package br.com.crista.fashion.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.crista.fashion.dto.AuthDTO;
import br.com.crista.fashion.dto.UsuarioDTO;
import br.com.crista.fashion.message.JwtResponse;
import br.com.crista.fashion.security.jwt.JwtProvider;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthController extends GenericController {

    private final @NonNull
    AuthenticationManager authenticationManager;

    private final @NonNull
    JwtProvider jwtProvider;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthDTO auth) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        auth.getUsername(),
                        auth.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateJwtToken(authentication);

        return ResponseEntity.ok(new JwtResponse(jwt));
    }

    @PostMapping("/login-admin")
    public ResponseEntity<?> loginAdmin(@Valid @RequestBody AuthDTO auth) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        auth.getUsername(),
                        auth.getPassword()
                )
        );

        String a = authentication.getAuthorities().toString();

        if (a.contains("ADMIN")) {

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtProvider.generateJwtToken(authentication);

            return ResponseEntity.ok(new JwtResponse(jwt));
        } else {

            return ResponseEntity.badRequest().body("Esse usuário não pode autorizar a compra");
        }
    }

    @GetMapping("/dadosusuario/{username}")
    public ResponseEntity getDadosUsuario(@PathVariable("username") String username) {
        try {

            UsuarioDTO usuario = new UsuarioDTO(getCurrentUser());
            usuario.setSomenteBoleto(true);

            return ResponseEntity.ok(usuario);
        } catch (Exception e) {

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}