package com.fuxin.alert.repository;

import com.fuxin.alert.domain.PlatformAlert;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the PlatformAlert entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlatformAlertRepository extends JpaRepository<PlatformAlert, Long> {
    @Query(value = "select * from platform_alert where id > ?1 ",nativeQuery = true)
    List<PlatformAlert> getAlert(Long id);
}
