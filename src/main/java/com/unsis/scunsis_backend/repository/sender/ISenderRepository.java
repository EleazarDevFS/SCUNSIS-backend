package com.unsis.scunsis_backend.repository.sender;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unsis.scunsis_backend.model.sender.Sender;

import java.util.List;
import java.util.Optional;

@Repository
public interface ISenderRepository extends JpaRepository<Sender, Long> {
    Optional<Sender> findById(Long senderId);
    List<Sender> getSenderByName(String senderName);
}
