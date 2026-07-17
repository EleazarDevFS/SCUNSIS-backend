package com.unsis.scunsis_backend.repository.receiver;

import com.unsis.scunsis_backend.model.receiver.Receiver;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IReceiverRepository extends JpaRepository<Receiver, Long> {
    boolean existsByEmail(String email);
    Optional<Receiver> findByEmail(String email);
}
