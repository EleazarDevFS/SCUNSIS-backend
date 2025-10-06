package com.unsis.scunsis_backend.repository.signature;

import com.unsis.scunsis_backend.model.signature.Signature;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ISignatureRepository extends JpaRepository<Signature, Long>{}
