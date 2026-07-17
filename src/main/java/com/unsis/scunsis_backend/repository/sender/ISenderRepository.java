package com.unsis.scunsis_backend.repository.sender;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unsis.scunsis_backend.model.sender.Sender;

import java.util.List;

public interface ISenderRepository extends JpaRepository<Sender, Long> {
    List<Sender> getSenderByName(String senderName);
}
