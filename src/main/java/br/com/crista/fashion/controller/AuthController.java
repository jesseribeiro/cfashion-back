package br.com.crista.fashion.controller;

import br.com.crista.fashion.dto.AuthDTO;
import br.com.crista.fashion.enumeration.TipoFormaPagamento;
import br.com.crista.fashion.message.JwtResponse;
import br.com.crista.fashion.security.jwt.JwtProvider;
import br.com.crista.fashion.service.LojaService;
import br.com.crista.fashion.dto.UsuarioDTO;
import br.com.crista.fashion.enumeration.EnumRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1/auth")
public class AuthController extends GenericController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    LojaService lojaService;

    @Autowired
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
            if (usuario.getRoleAtiva().equalsIgnoreCase(EnumRole.CREDIARISTA.name()) ||
                    usuario.getRoleAtiva().equalsIgnoreCase(EnumRole.PROPRIETARIO.name())){
                try {
                    List<String> listaFormasPagamento = lojaService.getListaFormasPagamento(usuario.getLojaId());

                    if (listaFormasPagamento.size() == 1 && listaFormasPagamento.get(0).equals(TipoFormaPagamento.BOLETO.name())) {
                        usuario.setSomenteBoleto(true);
                    }
                }
                catch (Exception e){
                    log.error("Usuário sem loja", e);
                }
            }
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}