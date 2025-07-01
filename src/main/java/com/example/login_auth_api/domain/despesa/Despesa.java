package com.example.login_auth_api.domain.despesa;

import com.example.login_auth_api.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "despesas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Despesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;

    @Enumerated(EnumType.STRING)
    private FormaPagamento formaPagamento;

    private Double valor;

    private LocalDate data;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public enum FormaPagamento {
        CARTAO,
        PIX,
        DINHEIRO
    }
}
