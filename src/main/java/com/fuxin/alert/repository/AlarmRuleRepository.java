package com.fuxin.alert.repository;

import com.fuxin.alert.domain.AlarmRule;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AlarmRule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AlarmRuleRepository extends JpaRepository<AlarmRule, Long> {
}
