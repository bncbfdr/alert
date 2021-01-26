package com.fuxin.alert.repository;

import com.fuxin.alert.domain.AlarmLevel;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AlarmLevel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AlarmLevelRepository extends JpaRepository<AlarmLevel, Long> {
}
