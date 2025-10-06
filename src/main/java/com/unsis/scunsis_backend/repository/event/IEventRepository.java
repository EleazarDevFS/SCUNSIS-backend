package com.unsis.scunsis_backend.repository.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unsis.scunsis_backend.model.event.Event;
@Repository
public interface IEventRepository extends JpaRepository<Event, Long> {}
