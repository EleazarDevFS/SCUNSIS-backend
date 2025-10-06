package com.unsis.scunsis_backend.repository.sender;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unsis.scunsis_backend.model.sender.Sender;
@Repository
public interface ISenderRepository extends JpaRepository<Sender, Long> {}
