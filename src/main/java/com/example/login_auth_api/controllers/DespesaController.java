package com.example.login_auth_api.controllers;

import com.example.login_auth_api.domain.despesa.Despesa;
import com.example.login_auth_api.domain.user.User;
import com.example.login_auth_api.repositories.DespesaRepository;
import com.example.login_auth_api.repositories.UserRepository;
import com.example.login_auth_api.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/despesas")
@CrossOrigin(origins = "http://localhost:5173")
public class DespesaController {

    @Autowired
    private DespesaRepository despesaRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/cadastrar")
    public ResponseEntity<?> criar(@RequestBody Despesa despesa, HttpServletRequest request) {
        try {
            String token = extractTokenFromRequest(request);
            if (token == null) {
                return ResponseEntity.badRequest().body("Token não encontrado");
            }

            String email = tokenService.validateToken(token);
            if (email == null) {
                return ResponseEntity.status(401).body("Token inválido");
            }

            Optional<User> userOptional = userRepository.findByEmail(email);
            if (userOptional.isEmpty()) {
                return ResponseEntity.badRequest().body("Usuário não encontrado");
            }

            despesa.setUser(userOptional.get());
            Despesa salva = despesaRepository.save(despesa);
            return ResponseEntity.ok(salva);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao cadastrar despesa: " + e.getMessage());
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<?> listar(HttpServletRequest request) {
        try {
            String token = extractTokenFromRequest(request);
            if (token == null) {
                return ResponseEntity.badRequest().body("Token não encontrado");
            }

            String email = tokenService.validateToken(token);
            if (email == null) {
                return ResponseEntity.status(401).body("Token inválido");
            }

            Optional<User> userOptional = userRepository.findByEmail(email);
            if (userOptional.isEmpty()) {
                return ResponseEntity.badRequest().body("Usuário não encontrado");
            }

            List<Despesa> despesas = despesaRepository.findByUserId(userOptional.get().getId());
            return ResponseEntity.ok(despesas);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao listar despesas: " + e.getMessage());
        }
    }

    @GetMapping("pesquisar/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id, HttpServletRequest request) {
        try {
            String token = extractTokenFromRequest(request);
            if (token == null) {
                return ResponseEntity.badRequest().body("Token não encontrado");
            }

            String email = tokenService.validateToken(token);
            if (email == null) {
                return ResponseEntity.status(401).body("Token inválido");
            }

            Optional<User> userOptional = userRepository.findByEmail(email);
            if (userOptional.isEmpty()) {
                return ResponseEntity.badRequest().body("Usuário não encontrado");
            }

            Optional<Despesa> despesaOptional = despesaRepository.findById(id);
            if (despesaOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            Despesa despesa = despesaOptional.get();
            if (!despesa.getUser().getId().equals(userOptional.get().getId())) {
                return ResponseEntity.status(403).body("Acesso negado");
            }

            return ResponseEntity.ok(despesa);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao buscar despesa: " + e.getMessage());
        }
    }

    @PutMapping("alterar/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Despesa novaDespesa, HttpServletRequest request) {
        try {
            String token = extractTokenFromRequest(request);
            if (token == null) {
                return ResponseEntity.badRequest().body("Token não encontrado");
            }

            String email = tokenService.validateToken(token);
            if (email == null) {
                return ResponseEntity.status(401).body("Token inválido");
            }

            Optional<User> userOptional = userRepository.findByEmail(email);
            if (userOptional.isEmpty()) {
                return ResponseEntity.badRequest().body("Usuário não encontrado");
            }

            Optional<Despesa> despesaOptional = despesaRepository.findById(id);
            if (despesaOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            Despesa despesa = despesaOptional.get();
            if (!despesa.getUser().getId().equals(userOptional.get().getId())) {
                return ResponseEntity.status(403).body("Acesso negado");
            }

            despesa.setDescricao(novaDespesa.getDescricao());
            despesa.setFormaPagamento(novaDespesa.getFormaPagamento());
            despesa.setValor(novaDespesa.getValor());
            despesa.setData(novaDespesa.getData());

            return ResponseEntity.ok(despesaRepository.save(despesa));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao atualizar despesa: " + e.getMessage());
        }
    }

    @DeleteMapping("deletar/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id, HttpServletRequest request) {
        try {
            String token = extractTokenFromRequest(request);
            if (token == null) {
                return ResponseEntity.badRequest().body("Token não encontrado");
            }

            String email = tokenService.validateToken(token);
            if (email == null) {
                return ResponseEntity.status(401).body("Token inválido");
            }

            Optional<User> userOptional = userRepository.findByEmail(email);
            if (userOptional.isEmpty()) {
                return ResponseEntity.badRequest().body("Usuário não encontrado");
            }

            Optional<Despesa> despesaOptional = despesaRepository.findById(id);
            if (despesaOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            Despesa despesa = despesaOptional.get();
            if (!despesa.getUser().getId().equals(userOptional.get().getId())) {
                return ResponseEntity.status(403).body("Acesso negado");
            }

            despesaRepository.delete(despesa);
            return ResponseEntity.noContent().build();

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao deletar despesa: " + e.getMessage());
        }
    }

    private String extractTokenFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }
}
