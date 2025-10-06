package com.unsis.scunsis_backend.repository.activity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unsis.scunsis_backend.model.activity.Activity;
@Repository
public interface IActivityRepository extends JpaRepository<Activity, Long>{}