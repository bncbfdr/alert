package com.fuxin.alert.repository;

import com.fuxin.alert.domain.AlarmInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the AlarmInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AlarmInfoRepository extends JpaRepository<AlarmInfo, Long> {

    @Query(value = "SELECT * FROM alarm_info a where a.checked = 2 and a.id > ?1",nativeQuery=true)
    List<AlarmInfo> getAlert(Long id);

}
