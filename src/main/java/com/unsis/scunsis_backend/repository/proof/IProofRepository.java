package com.unsis.scunsis_backend.repository.proof;

import com.unsis.scunsis_backend.model.enums.EParticipationRole;
import com.unsis.scunsis_backend.model.proof.Proof;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProofRepository extends JpaRepository<Proof, String> {

    @Query("SELECT COUNT(p) FROM Proof p WHERE p.role = :role AND YEAR(p.date) = :year")
    long countByRoleAndYear(@Param("role") EParticipationRole role, @Param("year") int year);

    boolean existsByFolio(String folio);

    List<Proof> findByActivityActivityId(Long activityId);
}
