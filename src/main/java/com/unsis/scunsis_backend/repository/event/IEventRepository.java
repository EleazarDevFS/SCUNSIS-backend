package com.unsis.scunsis_backend.repository.event;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unsis.scunsis_backend.model.event.Event;
public interface IEventRepository extends JpaRepository<Event, Long> {}
