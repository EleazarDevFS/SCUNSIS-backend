package com.unsis.scunsis_backend.repository.proof;

import com.unsis.scunsis_backend.model.proof.ProofFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IProofFileRepository extends JpaRepository<ProofFile, Long> {
    Optional<ProofFile> findByFolio(String folio);
    void deleteByFolio(String folio);
}
