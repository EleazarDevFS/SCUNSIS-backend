package com.unsis.scunsis_backend.repository.proof;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.unsis.scunsis_backend.model.enums.EProofType;
import com.unsis.scunsis_backend.model.proof.Proof;
@Repository
public interface IProofRepository extends JpaRepository<Proof, String>{
    @Query("SELECT COUNT(p) FROM Proof p")
    long countAllProofs();
    
    @Query("SELECT COUNT(p) FROM Proof p WHERE p.type = :type")
    long countByType(@Param("type") EProofType type);

}
