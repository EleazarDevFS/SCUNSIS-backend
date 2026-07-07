package com.unsis.scunsis_backend.repository.receiver;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unsis.scunsis_backend.model.receiver.Receiver;
@Repository
public interface IReceiverRepository extends JpaRepository<Receiver, Long>{
    Boolean existsByEmail(String email);
}
