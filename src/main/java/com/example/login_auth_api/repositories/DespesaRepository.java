package com.example.login_auth_api.repositories;

import com.example.login_auth_api.domain.despesa.Despesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DespesaRepository extends JpaRepository<Despesa, Long> {

    // Buscar despesas por ID do usuário
    @Query("SELECT d FROM Despesa d WHERE d.user.id = :userId")
    List<Despesa> findByUserId(@Param("userId") String userId);
}