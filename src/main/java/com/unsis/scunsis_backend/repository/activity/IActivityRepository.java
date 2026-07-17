package com.unsis.scunsis_backend.repository.activity;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unsis.scunsis_backend.model.activity.Activity;
public interface IActivityRepository extends JpaRepository<Activity, Long>{}